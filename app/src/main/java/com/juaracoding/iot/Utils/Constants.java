package com.juaracoding.iot.Utils;


import android.provider.BaseColumns;

import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * Created by brijesh on 20/4/17.
 */

public class Constants implements BaseColumns {

    //connection constants
    //public static final String MQTT_BROKER_URL = "tcp://192.168.43.6:1883";
    public static final String CLIENT_ID = MqttClient.generateClientId();
    public static final String PUBLISH_TOPIC = "topic/esp";
    public static final String SUBCRIBE_TOPIC = "topic/+";
    //subcribe constants
    public static final String TEMP = "topic/temp";
    public static final String HUMID = "topic/humid";
    public static final String LDR = "topic/ldr";
    //message constants publish
    public static final String LAMPU_NYALA = "LampuNyala";
    public static final String LAMPU_MATI = "LampuMati";
    public static final String POMPA_NYALA = "PompaNyala";
    public static final String POMPA_MATI = "PompaMati";
    //local databases info constants
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "iot.db";
    public static final String TABLE_RESULT = "result";
    public static final String TABLE_RESULT_SUBCRIPTIONS = "subcriptions";

    public static final String COLUMN_MESSAGE = " COLUMN_MESSAGE";
    public static final String COLUMN_TIMESTAMP = " COLUMN_TIME_STAMP";
    public static final String COLUMN_TOPIC = " COLUMN_TOPIC";

    public static final String COMMA_SEP = ",";
    private static final String TEXT_TYPE = " TEXT";
    public static final String CREATE_ENTITIES = "CREATE TABLE " +
            TABLE_RESULT + " (" + _ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
            COLUMN_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
            COLUMN_TOPIC + TEXT_TYPE + COMMA_SEP +
            COLUMN_MESSAGE + " TEXT);";

    public static final String CREATE_ENTITIES_SUBCRIPTIONS = "CREATE TABLE " +
            TABLE_RESULT_SUBCRIPTIONS + " (" + _ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
            COLUMN_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
            COLUMN_TOPIC + TEXT_TYPE + COMMA_SEP +
            COLUMN_MESSAGE + " TEXT);";

    public static final String DELETE_ALL_HISTORY =" DROP TABLE IF EXISTS " + TABLE_RESULT;
    public static final String DELETE_ALL_HISTORY_SUBCRIPTIONS =" DROP TABLE IF EXISTS " + TABLE_RESULT_SUBCRIPTIONS;
    public static final String  COLUMN_SERVER = "server";

    //viewpager string
    public static final String INFO_TEMP = "Temperature";
    public static final String INFO_HUMID = "Humidity";
    public static final String INFO_LDR = "Light Intensity";

    //sp
    public static final String SP_USER = "spUser";
    public static final String SP_SERVER = "tcp://";
    public static final String SP_TOPIC = "topic/+";

}

