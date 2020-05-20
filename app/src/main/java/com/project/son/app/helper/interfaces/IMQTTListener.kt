package com.project.son.app.helper.interfaces

import com.project.son.app.helper.model.FailureModel
import com.project.son.app.helper.model.MessageModel
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken

interface IMQTTListener {
    fun onConnectionToBrokerLost(response: FailureModel) {}
    fun onMessageArrived(response: MessageModel) {}
    fun onDeliveryMessageComplete(response: IMqttDeliveryToken) {}
    fun onConnectionSuccessful(response: IMqttToken) {}
    fun onConnectionFailure(response: FailureModel) {}
    fun onSubscribeSuccessful(response: IMqttToken) {}
    fun onSubscribeFailure(response: FailureModel) {}
    fun onPublishSuccessful(response: IMqttToken) {}
    fun onPublishFailure(response: FailureModel) {}
}
