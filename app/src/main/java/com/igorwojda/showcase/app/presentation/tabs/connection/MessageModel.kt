package com.igorwojda.showcase.app.presentation.tabs.connection

import org.eclipse.paho.client.mqttv3.MqttMessage

class MessageModel(val topic: String, val message: MqttMessage)
