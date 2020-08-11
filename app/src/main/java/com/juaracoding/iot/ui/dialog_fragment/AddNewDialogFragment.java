package com.juaracoding.iot.ui.dialog_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.juaracoding.iot.R;
import com.juaracoding.iot.model.MqttModel;

public class AddNewDialogFragment extends DialogFragment {

    private EditText etTopic, etMessages, etComponentName;
    private Spinner spOptions;
    private Button btnAdd;
    private MqttModel models;

    public AddNewDialogFragment() {

    }

    public static AddNewDialogFragment newInstance(String title) {
        AddNewDialogFragment frag = new AddNewDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_add_new, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTopic = view.findViewById(R.id.et_topic);
        etMessages = view.findViewById(R.id.et_messages);
        etComponentName = view.findViewById(R.id.et_component_name);
        spOptions = view.findViewById(R.id.sp_options);
        initComponent();

    }

    private void initComponent(){
        spOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equalsIgnoreCase("Publish")){
                    etMessages.setVisibility(View.VISIBLE);
                }else{
                    etMessages.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String topic = etTopic.getText().toString();
        models.setTopic(topic);
        final String messages = etMessages.getText().toString();
        final String name = etComponentName.getText().toString();
        models.setComponentName(name);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("topic", models.getTopic());
                bundle.putString("name", models.getComponentName());
            }
        });
    }
}
