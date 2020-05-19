package com.igorwojda.showcase.app.presentation.tabs.connection

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.telephony.SmsManager
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import com.igorwojda.showcase.R
import com.igorwojda.showcase.app.presentation.tabs.connection.enum.ConnectionType
import com.igorwojda.showcase.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.fragment_connection.*
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.kodein.di.generic.instance
import kotlin.text.Charsets.UTF_8

class ConnectionFragment : BaseContainerFragment() {

    private val MY_SEND_SMS_REQUEST_CODE = 1000

    private val viewModel: ConnectionViewModel by instance()

    private lateinit var connectionType: ConnectionType

    override val layoutResourceId = R.layout.fragment_connection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = requireContext()

        setTextFont()
        initNumberPicker()
        initConnectionActions()
        initSendCommand()
    }

    private fun initMqtt() {
        MQTTHandler.setup(object : IMQTTListener {
            override fun onConnectionSuccessful(response: IMqttToken) {
                showSnackbar("اتصال با سرور برقرار شد", true)

                connectionType = ConnectionType.INTERNET
                cpb_sms.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_sms.indeterminateMode = false
                cpb_internet.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_connected)
                cpb_internet.indeterminateMode = false
                cpb_bluetooth.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_bluetooth.indeterminateMode = false
                img_sms.isEnabled = true
                img_internet.isEnabled = false
//                img_bluetooth.isEnabled = true
                txv_choose_floor.visibility = VISIBLE
                npk_floor.visibility = VISIBLE
                btn_send_command.visibility = VISIBLE
            }

            override fun onConnectionFailure(response: FailureModel) {
                showSnackbar("اتصال با سرور برقرار نشد", false)

                connectionType = ConnectionType.NONE
                cpb_sms.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_sms.indeterminateMode = false
                cpb_internet.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_internet.indeterminateMode = false
                cpb_bluetooth.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_bluetooth.indeterminateMode = false
                img_sms.isEnabled = true
                img_internet.isEnabled = true
//                img_bluetooth.isEnabled = true
                txv_choose_floor.visibility = INVISIBLE
                npk_floor.visibility = INVISIBLE
                btn_send_command.visibility = INVISIBLE
            }

            override fun onConnectionToBrokerLost(response: FailureModel) {
                showSnackbar("اتصال با سرور قطع شد", false)

                connectionType = ConnectionType.NONE
                cpb_sms.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_sms.indeterminateMode = false
                cpb_internet.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_internet.indeterminateMode = false
                cpb_bluetooth.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_bluetooth.indeterminateMode = false
                img_sms.isEnabled = true
                img_internet.isEnabled = true
//                img_bluetooth.isEnabled = true
                txv_choose_floor.visibility = INVISIBLE
                npk_floor.visibility = INVISIBLE
                btn_send_command.visibility = INVISIBLE
            }
        }, context)
    }

    private fun initSendCommand() {
        btn_send_command.setOnClickListener {
            when (connectionType) {
                ConnectionType.SMS -> sendSmsCommand()
                ConnectionType.INTERNET -> sendInternetCommand()
                ConnectionType.BLUETOOTH -> sendBluetoothCommand()
                else -> {
                }
            }
        }
    }

    private fun sendSmsCommand() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_DENIED
        )
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.SEND_SMS),
                MY_SEND_SMS_REQUEST_CODE
            )
        else
            try {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage("+989059661346", null, "*@#$7124*${npk_floor.value}*@#$7124*", null, null)
                showSnackbar("فرمان ارسال شد", true)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
    }

    private fun showSnackbar(message: String, positivity: Boolean) {
        try {
            val mSnackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
            mSnackbar.setTextColor(if (positivity) Color.GREEN else Color.RED)
            mSnackbar.setActionTextColor(Color.WHITE)

            val txvSnackbarMessage = mSnackbar.view.findViewById<TextView>(R.id.snackbar_text)
            val txvSnackbarAction = mSnackbar.view.findViewById<TextView>(R.id.snackbar_action)

            ViewCompat.setLayoutDirection(mSnackbar.view, ViewCompat.LAYOUT_DIRECTION_RTL);

            Typeface.createFromAsset(
                context?.assets,
                "iransans_fa.ttf"
            ).let {
                txvSnackbarMessage.typeface = it
                txvSnackbarAction.typeface = it
            }
            mSnackbar.setAction("تأیید") {
                mSnackbar.dismiss()
            }.show()
        } catch (ignore: Exception) {
        }
    }

    private fun sendInternetCommand() {
        MQTTHandler.publish(
            object : IMQTTListener {
                override fun onPublishSuccessful(response: IMqttToken) {
                    showSnackbar("فرمان ارسال شد", true)
                }

                override fun onPublishFailure(response: FailureModel) {
                    showSnackbar("مشکل در ارسال فرمان", false)
                }
            }, npk_floor.value.toString().toByteArray(UTF_8)
        )
    }

    private fun sendBluetoothCommand() {
    }

    private fun initConnectionActions() {
        img_sms.setOnClickListener {
            MQTTHandler.disconnect()
            runFailedHandler()
            connectionType = ConnectionType.SMS
            cpb_sms.backgroundProgressBarColor =
                ContextCompat.getColor(requireContext(), R.color.color_connection_connected)
            cpb_sms.indeterminateMode = false
            cpb_internet.backgroundProgressBarColor =
                ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
            cpb_internet.indeterminateMode = false
            cpb_bluetooth.backgroundProgressBarColor =
                ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
            cpb_bluetooth.indeterminateMode = false
            img_sms.isEnabled = false
            img_internet.isEnabled = true
//            img_bluetooth.isEnabled = true
            txv_choose_floor.visibility = VISIBLE
            npk_floor.visibility = VISIBLE
            btn_send_command.visibility = VISIBLE
        }

        img_internet.setOnClickListener {
            MQTTHandler.disconnect()
            runFailedHandler()
            initMqtt()
            connectionType = ConnectionType.NONE
            cpb_sms.backgroundProgressBarColor =
                ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
            cpb_sms.indeterminateMode = false
            cpb_internet.backgroundProgressBarColor =
                ContextCompat.getColor(requireContext(), R.color.color_connection_connecting)
            cpb_internet.indeterminateMode = true
            cpb_bluetooth.backgroundProgressBarColor =
                ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
            cpb_bluetooth.indeterminateMode = false
            img_sms.isEnabled = true
            img_internet.isEnabled = false
//            img_bluetooth.isEnabled = true
            txv_choose_floor.visibility = INVISIBLE
            npk_floor.visibility = INVISIBLE
            btn_send_command.visibility = INVISIBLE
        }

        img_bluetooth.setOnClickListener {
            MQTTHandler.disconnect()
            runFailedHandler()
            cpb_sms.backgroundProgressBarColor =
                ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
            cpb_sms.indeterminateMode = false
            cpb_internet.backgroundProgressBarColor =
                ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
            cpb_internet.indeterminateMode = false
            cpb_bluetooth.backgroundProgressBarColor =
                ContextCompat.getColor(requireContext(), R.color.color_connection_connecting)
            cpb_bluetooth.indeterminateMode = true
            connectionType = ConnectionType.BLUETOOTH
            img_sms.isEnabled = true
            img_internet.isEnabled = true
            img_bluetooth.isEnabled = false
            txv_choose_floor.visibility = INVISIBLE
            npk_floor.visibility = INVISIBLE
            btn_send_command.visibility = INVISIBLE
        }
    }

    private fun setTextFont() {
        Typeface.createFromAsset(
            context?.assets,
            "iransans_fa.ttf"
        ).let {
            txv_choose_connection.typeface = it
            txv_choose_floor.typeface = it
            btn_send_command.typeface = it
        }
    }

    private fun initNumberPicker() {
        val minValue = -10
        val maxValue = 50

        npk_floor.let {
            it.minValue = 0
            it.maxValue = maxValue - minValue
            it.value = 0 - minValue
            it.setFormatter { value -> (value + minValue).toString() }
            it.setOnValueChangedListener { picker, oldVal, newVal -> run {} }
        }
    }

    private fun runFailedHandler() {
        Handler().postDelayed({
            if (connectionType == ConnectionType.NONE) {
                showSnackbar("اتصال برقرار نشد.", false)
                MQTTHandler.disconnect()

                cpb_sms.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_sms.indeterminateMode = false
                cpb_internet.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_internet.indeterminateMode = false
                cpb_bluetooth.backgroundProgressBarColor =
                    ContextCompat.getColor(requireContext(), R.color.color_connection_disconnected)
                cpb_bluetooth.indeterminateMode = false
                connectionType = ConnectionType.BLUETOOTH
                img_sms.isEnabled = true
                img_internet.isEnabled = true
//                img_bluetooth.isEnabled = true
                txv_choose_floor.visibility = INVISIBLE
                npk_floor.visibility = INVISIBLE
                btn_send_command.visibility = INVISIBLE
            }
        }, 5000)
    }
}
