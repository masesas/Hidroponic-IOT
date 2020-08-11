package com.juaracoding.iot.model;


public class Subscription {

    private String topic;
    private int qos;
    private String lastMessage;
    private String clientHandle;
    private String timeStamp;
    private boolean enableNotifications;

    public Subscription(String topic, String lastMessage) {
        this.topic = topic;
        this.lastMessage = lastMessage;
    }

    public Subscription(String topic, String lastMessage, String timeStamp) {
        this.topic = topic;
        this.lastMessage = lastMessage;
        this.timeStamp = timeStamp;
    }

    public Subscription(String topic, int qos, String clientHandle, boolean enableNotifications){
        this.topic = topic;
        this.qos = qos;
        this.clientHandle = clientHandle;
        this.enableNotifications = enableNotifications;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getClientHandle() {
        return clientHandle;
    }

    public void setClientHandle(String clientHandle) {
        this.clientHandle = clientHandle;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isEnableNotifications() {
        return enableNotifications;
    }

    public void setEnableNotifications(boolean enableNotifications) {
        this.enableNotifications = enableNotifications;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "topic='" + topic + '\'' +
                ", qos=" + qos +
                ", clientHandle='" + clientHandle + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", enableNotifications='" + enableNotifications + '\''+
                '}';
    }
}
