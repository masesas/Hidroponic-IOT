package com.juaracoding.iot.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class SharePreferencesHelper {

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharePreferencesHelper(Context context){
        sp = context.getSharedPreferences(Constants.SP_USER, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.apply();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPServer() {
        return sp.getString("server", "");
    }

    public String getSPTopic(){
        return sp.getString("topic", "");
    }

    public String getSpName(){
        return sp.getString("name", "");
    }

    public String getSpSsl(){
        return sp.getString("ssl", "");
    }

    public String getSpMessagesTemp(){ return sp.getString("temp", "");}

    public String getSpMessagesHumid(){ return sp.getString("humid", "");}

    public String getSpMessagesLdr(){ return sp.getString("ldr", "");}

    public String getSpFragment(){
        return sp.getString("fragment", "");
    }
}
