package com.juaracoding.iot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.juaracoding.iot.MainActivity;
import com.juaracoding.iot.R;
import com.juaracoding.iot.Utils.Constants;
import com.juaracoding.iot.model.IReceivedMessageListener;
import com.juaracoding.iot.model.ReceivedMessage;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<ReceivedMessage> {

    private final Context context;
    private final ArrayList<ReceivedMessage> messages;
    private final ReceivedMessage message = null;

    public HistoryAdapter(Context context, ArrayList<ReceivedMessage> messages) {
        super(context, R.layout.item_history, messages);
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_history, parent, false);

        final TextView topicTextView = (TextView) rowView.findViewById(R.id.tv_topic);
        final TextView messageTextView = (TextView) rowView.findViewById(R.id.tv_message);
        final TextView dateTextView = (TextView) rowView.findViewById(R.id.tv_date);

        topicTextView.setText(messages.get(position).getTopic());
        if(messages.get(position).getTopic().equalsIgnoreCase(Constants.TEMP)){
            messageTextView.setText(new String(messages.get(position).getMessage().getPayload()) + "°");
        }else if(messages.get(position).getTopic().equalsIgnoreCase(Constants.HUMID)){
            messageTextView.setText(new String(messages.get(position).getMessage().getPayload()) + "%");
        }else if(messages.get(position).getTopic().equalsIgnoreCase(Constants.LDR)){
            messageTextView.setText(new String(messages.get(position).getMessage().getPayload()) + "%");
        }else {
            messageTextView.setText(new String(messages.get(position).getMessage().getPayload()));
        }
        DateFormat dateTimeFormatter = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        String shortDateStamp = dateTimeFormatter.format(messages.get(position).getTimestamp());
        dateTextView.setText(context.getString(R.string.message_time_fmt, shortDateStamp));

        return rowView;
    }

    @Override
    public int getCount() {
        return messages.size();
    }
}
