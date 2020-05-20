package com.project.son.app.helper.model

import org.eclipse.paho.client.mqttv3.MqttMessage

class MessageModel(val topic: String, val message: MqttMessage)
