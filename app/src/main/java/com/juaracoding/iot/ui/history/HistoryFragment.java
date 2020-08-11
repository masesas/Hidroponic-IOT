package com.juaracoding.iot.ui.history;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.juaracoding.iot.MainActivity;
import com.juaracoding.iot.MqttService.MqttDatabaseHelper;
import com.juaracoding.iot.R;
import com.juaracoding.iot.Utils.Constants;
import com.juaracoding.iot.adapter.HistoryAdapter;
import com.juaracoding.iot.adapter.LocalHistory;
import com.juaracoding.iot.model.IReceivedMessageListener;
import com.juaracoding.iot.model.ReceivedMessage;
import com.juaracoding.iot.model.Subscription;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private HistoryAdapter adapter;
    private LocalHistory localHistoryAdapter;
    private ArrayList<ReceivedMessage> receivedMessageArrayList;
    private ListView listView;
    private FloatingActionButton fabClear;
    private ArrayList<Subscription> stringArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receivedMessageArrayList = ((MainActivity) getActivity()).getMessages();
        ((MainActivity) getActivity()).addReceivedMessageListner(new IReceivedMessageListener() {
            @Override
            public void onMessageReceived(ReceivedMessage message) {
                Log.d("History Fragment : ", "onMessageReceived: " + message.getMessage());
                //localHistoryAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        //stringArrayList = ((MainActivity) getActivity()).showHistoryFromDb();
        Log.d("Database", "data" + stringArrayList);

        adapter = new HistoryAdapter(getActivity(), receivedMessageArrayList);
        //localHistoryAdapter = new LocalHistory(getActivity(), stringArrayList);

        listView = root.findViewById(R.id.lv_history);
        listView.setAdapter(adapter);

        fabClear = getActivity().findViewById(R.id.fab);
        fabClear.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel));
        fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivedMessageArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        return root;
    }
}