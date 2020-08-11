package com.juaracoding.iot.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttModel {

    private int componentImage;
    private int serverPort;
    private MqttMessage mqttMessage;
    private String topic;
    private String pubMessages;
    private String server;
    private String normalValue;
    private String componentName;
    private String subMessages;

    public MqttModel() {
    }

    public MqttModel(String pubMessages) {
        this.pubMessages = pubMessages;
    }

    public MqttModel(String pubMessages, String subMessages) {
        this.pubMessages = pubMessages;
        this.subMessages = subMessages;
    }

    public int getComponentImage() {
        return componentImage;
    }

    public void setComponentImage(int componentImage) {
        this.componentImage = componentImage;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public MqttMessage getMqttMessage() {
        return mqttMessage;
    }

    public void setMqttMessage(MqttMessage mqttMessage) {
        this.mqttMessage = mqttMessage;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPubMessages() {
        return pubMessages;
    }

    public void setPubMessages(String pubMessages) {
        this.pubMessages = pubMessages;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(String normalValue) {
        this.normalValue = normalValue;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getSubMessages() {
        return subMessages;
    }

    public void setSubMessages(String subMessages) {
        this.subMessages = subMessages;
    }
}
