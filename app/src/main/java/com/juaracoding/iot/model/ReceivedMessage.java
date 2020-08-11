package com.juaracoding.iot.model;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Date;


public class ReceivedMessage {

    public ReceivedMessage(String topic, MqttMessage message) {
        this.topic = topic;
        this.message = message;
        this.timestamp = new Date();
    }

    public ReceivedMessage(String topic, MqttMessage message, Date timestamp) {
        this.topic = topic;
        this.message = message;
        this.timestamp = timestamp;
    }

    private final String topic;
    private final MqttMessage message;
    private final Date timestamp;


    public String getTopic() {
        return topic;
    }

    public MqttMessage getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ReceivedMessage{" +
                "topic='" + topic + '\'' +
                ", message=" + message +
                ", timestamp=" + timestamp +
                '}';
    }
}
