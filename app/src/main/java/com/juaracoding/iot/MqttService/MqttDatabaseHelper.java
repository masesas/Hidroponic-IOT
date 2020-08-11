package com.juaracoding.iot.MqttService;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.juaracoding.iot.Utils.Constants;
import com.juaracoding.iot.model.ReceivedMessage;
import com.juaracoding.iot.model.Subscription;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Date;


public class MqttDatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String TAG = "MqttDatabaseHelper";

    public MqttDatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_ENTITIES);
        db.execSQL(Constants.CREATE_ENTITIES_SUBCRIPTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DELETE_ALL_HISTORY);
        db.execSQL(Constants.DELETE_ALL_HISTORY_SUBCRIPTIONS);
//        if(newVersion > oldVersion){
//
//        }
        onCreate(db);
    }

    public boolean insertData(String timeStamp, String topic, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            values.put(Constants.COLUMN_TIMESTAMP,timeStamp);
            values.put(Constants.COLUMN_TOPIC, topic);
            values.put(Constants.COLUMN_MESSAGE, message);
            Log.d(TAG, "inserting" + values);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        long result = db.insert(Constants.TABLE_RESULT,null, values);
        if(result == -1)
            return false;

        return true;
    }

    public boolean insertData(ReceivedMessage receivedMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            values.put(Constants.COLUMN_TIMESTAMP, receivedMessage.getTimestamp().toString());
            values.put(Constants.COLUMN_TOPIC, receivedMessage.getTopic());
            values.put(Constants.COLUMN_MESSAGE, receivedMessage.getMessage().toString());
            Log.d(TAG, "inserting" + values);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        long result = db.insert(Constants.TABLE_RESULT_SUBCRIPTIONS,null, values);
        if(result == -1)
            return false;

        return true;
    }

    public boolean insertData(Subscription subscription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            values.put(Constants.COLUMN_TIMESTAMP, subscription.getTimeStamp());
            values.put(Constants.COLUMN_TOPIC, subscription.getTopic());
            values.put(Constants.COLUMN_MESSAGE, subscription.getLastMessage());
            Log.d(TAG, "inserting" + values);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        long result = db.insert(Constants.TABLE_RESULT_SUBCRIPTIONS,null, values);
        if(result == -1)
            return false;

        return true;
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("DELETE FROM " + Constants.COLUMN_MESSAGE, null, null);
        db.close();
    }

    public void deleteAllData(Subscription subscription){
        Log.d(TAG, "Deleting Subscription: " + subscription.toString());
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_RESULT_SUBCRIPTIONS, _ID + "=?", new String[]{String.valueOf(subscription.getTimeStamp())});
        db.close();
    }

    public ArrayList<ReceivedMessage> getAllMessages(ReceivedMessage receivedMessage){
        ArrayList<ReceivedMessage> messageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_RESULT, null);
        for (int i = 0; i < c.getCount(); i++) {
            if(!c.moveToNext()){
                Log.d(TAG, "retrieveData: error whiling " + c.getCount());
            }
            String topic = c.getString(c.getColumnIndex(Constants.COLUMN_TOPIC));
            String message = c.getString(c.getColumnIndex(Constants.COLUMN_MESSAGE));
            String timeStamp = c.getString(c.getColumnIndex(Constants.COLUMN_TIMESTAMP));

        }
        return messageArrayList;
    }

    public Cursor retrieveData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_RESULT, null);
        return  c;
    }


    public ArrayList<String> getAllMessages(){
        ArrayList<String> receivedMessageList = new ArrayList<>();
        String query = "SELECT * FROM " + Constants.TABLE_RESULT;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()){
            receivedMessageList.add(cursor.getString(cursor.getColumnIndex(_ID)));
            receivedMessageList.add(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TIMESTAMP)));
            receivedMessageList.add(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TOPIC)));
            receivedMessageList.add(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MESSAGE)));
            cursor.moveToNext();
        }

        return receivedMessageList;
    }
}



