package com.juaracoding.iot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.gson.Gson;
import com.juaracoding.iot.MqttService.MqttDatabaseHelper;
import com.juaracoding.iot.MqttService.PahoMqttClient;
import com.juaracoding.iot.Utils.ChartHelper;
import com.juaracoding.iot.Utils.Constants;
import com.juaracoding.iot.Utils.SharePreferencesHelper;
import com.juaracoding.iot.model.IReceivedMessageListener;
import com.juaracoding.iot.model.ReceivedMessage;
import com.juaracoding.iot.model.Subscription;
import com.juaracoding.iot.ui.dialog_fragment.AddNewDialogFragment;
import com.juaracoding.iot.ui.dashboard.DashboardFragment;
import com.juaracoding.iot.ui.dialog_fragment.OptionsDialogFragment;
import com.juaracoding.iot.ui.history.HistoryFragment;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SharePreferencesHelper sp;
    private MqttAndroidClient client;
    private PahoMqttClient pahoMqttClient;
    private MqttDatabaseHelper db;
    private FrameLayout frameLayout;
    private BottomAppBar bottomAppBar;
    private ArrayList<IReceivedMessageListener> receivedMessageListeners = new ArrayList<>();
    private ArrayList<ReceivedMessage> receivedMessageArrayList = new ArrayList<>();
    private ArrayList<Subscription> subscriptionArrayList = new ArrayList<>();
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    //private FragmentBackStack fragmentBackStackListener;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = new SharePreferencesHelper(this);
        pahoMqttClient = new PahoMqttClient();
        db = new MqttDatabaseHelper(this);

        client = pahoMqttClient.getMqttClient(getApplicationContext(), sp.getSPServer(), Constants.CLIENT_ID);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        frameLayout = findViewById(R.id.frame_main);

        loadFragment(new DashboardFragment(), null);
        initComponent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(connect()){
            handler = new Handler();
            final Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    try{
                        if(!subcribe()){
                            subcribe();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            };
            handler.postDelayed(runnable, 2000);
        }

    }

    private void initComponent(){
        bottomAppBar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_list));
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsDialogFragment dialogFragment = new OptionsDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "optionsDialog");
            }
        });
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_history:
                        loadFragment(new HistoryFragment(), "History");
                        break;
                }
                return true;
            }
        });

    }

    public boolean loadFragment(Fragment fragment, String fragmentName) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_main, fragment, fragmentName)
                    .addToBackStack(fragmentName)
                    .commit();
            return true;
        }
        return false;
    }

    public void addView(GridLayout view) {
        Context context;
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.item_component, null);

        view.addView(myView);
    }

    public void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddNewDialogFragment editNameDialogFragment = AddNewDialogFragment.newInstance("Add New Component");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    public boolean connect() {
        if (sp.getSPServer() != null) {
            client = pahoMqttClient.getMqttClient(getApplicationContext(), sp.getSPServer(), MqttClient.generateClientId());
            return true;
        }else {
            showInfo("Server is Null");
            return false;
        }
    }

    public boolean disconnet() {
        if (pahoMqttClient.mqttAndroidClient.isConnected()) {
            try {
                pahoMqttClient.disconnect(client);
            } catch (MqttException e) {
                e.printStackTrace();
            }
            return true;
        }else {
            showInfo("Not Connected");
            return false;
        }
    }

    public void mqttCallback() {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                ReceivedMessage msg = new ReceivedMessage(topic, message);
                Subscription sub = new Subscription(msg.getTimestamp().toString(),topic, message.toString());
                receivedMessageArrayList.add(msg);
                for(IReceivedMessageListener listener : receivedMessageListeners){
                    listener.onMessageReceived(msg);
                    if(msg.getTopic().equalsIgnoreCase(Constants.TEMP)){
                        String temp = msg.getMessage().toString() + "°";
                        sp.saveSPString("temp", temp);
                    }else if(msg.getTopic().equalsIgnoreCase(Constants.HUMID)){
                        String humid = msg.getMessage().toString() + "%";
                        sp.saveSPString("humid", humid);
                    }else if(msg.getTopic().equalsIgnoreCase(Constants.LDR)){
                        String ldr = msg.getMessage().toString() + "%";
                        sp.saveSPString("ldr", ldr);
                    }
                    //db.insertData(sub);
                    //db.insertData(msg);
                    db.insertData(msg.getTimestamp().toString(), msg.getTopic(), message.toString());
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token){

            }
        });

    }

    public boolean subcribe() {
        if (pahoMqttClient.mqttAndroidClient.isConnected()) {
            try {
                pahoMqttClient.subscribe(client, Constants.SP_TOPIC, 0);
                mqttCallback();
            } catch (MqttException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            showInfo("Tidak Terhubung");
            return false;
        }
    }

    public void showInfo(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showInfoDialog(Context context, String messages){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(messages);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void addReceivedMessageListner(IReceivedMessageListener listener){
        receivedMessageListeners.add(listener);
    }

    public ArrayList<String> showHistoryFromDb(){
        Cursor res = db.retrieveData();
        if(res.getCount() == 0){
            showInfo("No Data in Database");
            return null;
        }

        ArrayList<String> stringArrayList = new ArrayList<>();
        while (res.moveToNext()) {
            String timeStamp = res.getString(1);
            String topic = res.getString(2);
            String message = res.getString(3);
            stringArrayList.add(timeStamp);
            stringArrayList.add(topic);
            stringArrayList.add(message);
        }

        return stringArrayList;
    }


    public ArrayList<ReceivedMessage> getMessages(){
        return receivedMessageArrayList;
    }

    public void publishMessage(String pubMessage){
        if(pahoMqttClient.mqttAndroidClient.isConnected()){
            try {
                pahoMqttClient.publishMessage(client, pubMessage,0, Constants.PUBLISH_TOPIC);
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            showInfo("Are you complete your Connection Settings ?");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mqttCallback();
    }
}
