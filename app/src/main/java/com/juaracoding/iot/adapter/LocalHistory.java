package com.juaracoding.iot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.juaracoding.iot.MqttService.MqttDatabaseHelper;
import com.juaracoding.iot.R;
import com.juaracoding.iot.model.Subscription;

import java.util.ArrayList;

public class LocalHistory extends ArrayAdapter<Subscription> {

    private Context context;
    private ArrayList<Subscription> stringArrayList;
    private MqttDatabaseHelper db;

    public LocalHistory(Context context, ArrayList<Subscription> stringArrayList) {
        super(context, R.layout.item_history);
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_history, parent, false);

        final TextView topicTextView = (TextView) rowView.findViewById(R.id.tv_topic);
        final TextView messageTextView = (TextView) rowView.findViewById(R.id.tv_message);
        final TextView dateTextView = (TextView) rowView.findViewById(R.id.tv_date);


        dateTextView.setText(stringArrayList.get(position).getTimeStamp());
        topicTextView.setText(stringArrayList.get(position).getTopic());
        messageTextView.setText(stringArrayList.get(position).getLastMessage());

        //Log.d("Cursor Throw Exception", sub.toString());

        return rowView;
    }
}
