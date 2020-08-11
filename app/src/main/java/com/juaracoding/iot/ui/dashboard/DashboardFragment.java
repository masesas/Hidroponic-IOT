package com.juaracoding.iot.ui.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.TypedArrayUtils;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.juaracoding.iot.MainActivity;
import com.juaracoding.iot.MqttService.MqttDatabaseHelper;
import com.juaracoding.iot.R;
import com.juaracoding.iot.Utils.ChartHelper;
import com.juaracoding.iot.Utils.Constants;
import com.juaracoding.iot.Utils.SharePreferencesHelper;
import com.juaracoding.iot.adapter.ComponentAdapter;
import com.juaracoding.iot.model.IReceivedMessageListener;
import com.juaracoding.iot.model.MqttModel;
import com.juaracoding.iot.model.ReceivedMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DashboardFragment extends Fragment {

    private ChartHelper sensorChart;
    private SharePreferencesHelper sp;
    private LineChart chart;
    private TextView tvDay, tvTempValue, tvHumidValue, tvLdrValue, tvName;
    private Button btnConnect;
    private TextClock tvDate;
    private ImageView imgIcon;
    private ComponentAdapter adapter;
    private MqttModel model;
    private int click = 0;
    private Switch swLamp, swPump;
    private FloatingActionButton fab;
    private static final String TAG = "Dashboard Fragment Result";
    private MqttDatabaseHelper db;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).connect();
        setRetainInstance(true);
        ((MainActivity) getActivity()).addReceivedMessageListner(new IReceivedMessageListener() {
            @Override
            public void onMessageReceived(ReceivedMessage message) {
                sp.getSpMessagesTemp();
                sp.getSpMessagesHumid();
                sp.getSpMessagesLdr();
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        chart = root.findViewById(R.id.chart);
        tvDate = root.findViewById(R.id.tv_date);
        tvDay = root.findViewById(R.id.tv_day);
        btnConnect = root.findViewById(R.id.btn_connect);
        imgIcon = root.findViewById(R.id.img_icon);
        tvTempValue = root.findViewById(R.id.tv_temp);
        tvHumidValue = root.findViewById(R.id.tv_humid);
        tvLdrValue = root.findViewById(R.id.tv_ldr);
        tvName = root.findViewById(R.id.tv_component_name);
        swLamp = root.findViewById(R.id.switch_lamp);
        swPump = root.findViewById(R.id.switch_pump);
        fab = getActivity().findViewById(R.id.fab);

        db = new MqttDatabaseHelper(getActivity());
        sensorChart = new ChartHelper(chart);
        sp = new SharePreferencesHelper(getContext());
        Log.d("data cuy","data"+((MainActivity) getActivity()).showHistoryFromDb());
//        List<Integer> inttt = new ArrayList<>();
//        List<Integer> intt = new ArrayList<>();
//        List<Integer> res = new ArrayList<>();
//
//        for (int i = 0; i < intt.size() && i < inttt.size(); i++) {
//
//        }
        initComponent();
        return root;
    }


    private void initComponent() {
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        tvDay.setText(currentDate);
        tvName.setText(sp.getSpName());
        if(!sp.getSpMessagesLdr().isEmpty() && !sp.getSpMessagesHumid().isEmpty() && !sp.getSpMessagesTemp().isEmpty()){
            tvTempValue.setText(sp.getSpMessagesTemp());
            tvLdrValue.setText(sp.getSpMessagesLdr());
            tvHumidValue.setText(sp.getSpMessagesHumid());

            ArrayList<String> data = ((MainActivity) getActivity()).showHistoryFromDb();
            for (int i = 0; i < data.size(); i++) {
                if(data.contains("topic/humid")){

                }
                if(data.contains("topic/ldr")){

                }
                if(data.contains("topic/temp")){

                }
            }

            float[] value = {
                    Float.parseFloat(sp.getSpMessagesTemp().replace("°", "")),
                    Float.parseFloat(sp.getSpMessagesLdr().replace("%","")),
                    Float.parseFloat(sp.getSpMessagesHumid().replace("%", ""))
            };
            float[] value2 = {
                    Float.parseFloat(sp.getSpMessagesTemp().replace("°", ""))+ 2,
                    Float.parseFloat(sp.getSpMessagesLdr().replace("%",""))+ 3,
                    Float.parseFloat(sp.getSpMessagesHumid().replace("%", "")) + 5
            };

            float[] value3 = {
                    Float.parseFloat(sp.getSpMessagesTemp().replace("°", "")) + 3,
                    Float.parseFloat(sp.getSpMessagesLdr().replace("%","")) + 4,
                    Float.parseFloat(sp.getSpMessagesHumid().replace("%", "")) + 6
            };

            sensorChart.addEntry(value, value2, value3);
        }

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnConnect.getText().toString().equalsIgnoreCase("Disconnect")) {
                    ((MainActivity) getActivity()).disconnet();
                    btnConnect.setText("Connect");
                    ((MainActivity) getActivity()).showInfo("Successfully Disconnected");
                } else {
                    try {
                        ((MainActivity) getActivity()).connect();
//                            ((MainActivity) getActivity()).subcribe();
                        ((MainActivity) getActivity()).showInfo("Successfully Connected");
                        btnConnect.setText("Disconnect");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        swLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((MainActivity) getActivity()).publishMessage(Constants.LAMPU_NYALA);
                } else {
                    ((MainActivity) getActivity()).publishMessage(Constants.LAMPU_MATI);
                }
            }
        });

        swPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((MainActivity) getActivity()).publishMessage(Constants.POMPA_NYALA);
                } else {
                    ((MainActivity) getActivity()).publishMessage(Constants.POMPA_MATI);
                }
            }
        });

        Log.d("data db", "initComponent: " + db.getAllMessages());
    }

}