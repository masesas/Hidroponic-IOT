package com.juaracoding.iot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juaracoding.iot.R;
import com.juaracoding.iot.model.MqttModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.HorizontalViewHolder> {

    private Context context;
    List<MqttModel> mqttModels = new ArrayList<>();

    public ComponentAdapter(Context context){
        this.context = context;
    }

    public void setData(List<MqttModel> mqttModels){
        this.mqttModels = mqttModels;
        notifyDataSetChanged();
    }

    public MqttModel getData(int position){
        return mqttModels.get(position);
    }


    @NonNull
    @Override
    public ComponentAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_component, parent, false);

        return new HorizontalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentAdapter.HorizontalViewHolder holder, int position) {

        MqttModel mqttModel = mqttModels.get(position);
        holder.tvName.setText(mqttModel.getComponentName());
        holder.imgComponent.setImageResource(mqttModel.getComponentImage());
        holder.tvValue.setText(mqttModel.getMqttMessage().toString());
        holder.tvNormalValue.setText(mqttModel.getNormalValue());
    }

    @Override
    public int getItemCount() {
        return mqttModels.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvValue, tvNormalValue;
        private CircleImageView imgComponent;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_component_name);
            tvValue = itemView.findViewById(R.id.tv_component_value);
            tvNormalValue  = itemView.findViewById(R.id.tv_component_normal_value);
            imgComponent = itemView.findViewById(R.id.img_component);
        }
    }
}
