package com.project.son.app.helper.classes

import android.content.Context
import android.text.Editable
import com.project.son.app.helper.interfaces.IMQTTListener
import com.project.son.app.helper.model.FailureModel
import com.project.son.app.helper.model.MessageModel
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber

class MQTTHandler {
    companion object {
        private var connected = false

        private var client: MqttAndroidClient? = null
        private var _mIMQTTListener: IMQTTListener? = null
        private var _mPublisherIMQTTListener: IMQTTListener? = null

        private var publishTopic = "nonstop7111"

        fun setup(
            mIMQTTListener: IMQTTListener?,
            mContext: Context?
        ) {
            if (!connected) {
                _mIMQTTListener = mIMQTTListener
                client = MqttAndroidClient(
                    mContext,
                    "tcp://broker.hivemq.com", //"tcp://185.8.174.194",
                    MqttClient.generateClientId()
                )

                client?.setCallback(object : MqttCallback {
                    override fun connectionLost(cause: Throwable) {
                        _mPublisherIMQTTListener?.onConnectionToBrokerLost(
                            FailureModel(
                                cause.message ?: ""
                            )
                        )
                        _mIMQTTListener?.onConnectionToBrokerLost(
                            FailureModel(
                                cause.message ?: ""
                            )
                        )
                        Timber.w("Connection to broker Lost.")
                        connected = false
                    }

                    override fun messageArrived(topic: String?, message: MqttMessage?) {
                        try {
                            _mPublisherIMQTTListener?.onMessageArrived(
                                MessageModel(
                                    topic ?: "", message!!
                                )
                            )
                            Timber.i("Message Arrived.")
                        } catch (e: Exception) {
                            Timber.e(e)
                        }
                    }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) {
                        _mPublisherIMQTTListener?.onDeliveryMessageComplete(token!!)
                        Timber.i("Receiving Message Completed.")
                    }
                })
                connect()
            }
        }

        private fun connect() {
            try {
                val mMqttOptions = MqttConnectOptions()
                mMqttOptions.isAutomaticReconnect = true
                mMqttOptions.isCleanSession = false
                mMqttOptions.keepAliveInterval = 10
                client?.connect(mMqttOptions)?.actionCallback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        _mIMQTTListener?.onConnectionSuccessful(asyncActionToken!!)
                        Timber.i("Connection to broker Success.")
                        connected = true
                    }

                    override fun onFailure(
                        asyncActionToken: IMqttToken?,
                        exception: Throwable
                    ) {
                        // Something went wrong e.g. connection timeout or firewall problems
                        _mIMQTTListener?.onConnectionFailure(
                            FailureModel(exception.message!!)
                        )
                        Timber.e("Connection to broker Failed")
                        connected = false

                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        fun publish(
            mIMQTTListener: IMQTTListener?,
            topic: String,
            command: ByteArray
        ) {
            _mPublisherIMQTTListener = mIMQTTListener
            publishTopic = "nonstop/$topic"

            var mPublishToken: IMqttDeliveryToken? = null
            try {
                mPublishToken = client?.publish(
                    publishTopic,
                    MqttMessage(command)
                )
            } catch (e: Exception) {
                Timber.e(e)
            }

            if (mPublishToken == null)
                _mPublisherIMQTTListener?.onPublishFailure(
                    FailureModel("Publish token is null.")
                )
            else
                mPublishToken.actionCallback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        try {
                            _mPublisherIMQTTListener?.onPublishSuccessful(asyncActionToken!!)
                        } catch (e: Exception) {
                        }
                        Timber.i("Publishing Done.")
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable) {
                        try {
                            _mPublisherIMQTTListener?.onPublishFailure(
                                FailureModel(
                                    exception.message!!
                                )
                            )
                        } catch (e: Exception) {
                        }
                        Timber.e("Publishing Failed.")
                    }
                }
        }

        private fun subscribe() {
            try {
                if (client != null) {
                    val subscribeToken: IMqttToken = client?.subscribe(
                        String.format(
                            "rsp/%s/#",
                            publishTopic
                        ), 0
                    )!!
                    subscribeToken.actionCallback = object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {
                            _mIMQTTListener?.onSubscribeSuccessful(asyncActionToken!!)
                            Timber.i("Subscribing Done.")
                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken?,
                            exception: Throwable
                        ) {
                            _mIMQTTListener?.onSubscribeFailure(
                                FailureModel(
                                    exception.message!!
                                )
                            )
                            Timber.e("Subscribe Failed.")
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        fun disconnect() {
            connected = false
            client?.let {
                try {
                    it.disconnectForcibly()
                    it.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            client = null
        }
    }
}
