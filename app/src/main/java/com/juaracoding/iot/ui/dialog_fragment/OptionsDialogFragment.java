package com.juaracoding.iot.ui.dialog_fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.juaracoding.iot.MainActivity;
import com.juaracoding.iot.R;
import com.juaracoding.iot.Utils.SharePreferencesHelper;
import com.juaracoding.iot.ui.settings.SettingsServerFragments;

public class OptionsDialogFragment extends DialogFragment {

    private Button btnSettings;
    private TextView tvServer;
    private int width = 600;
    private int height = 450;
    private SharePreferencesHelper sp;

    public OptionsDialogFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_options, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        btnSettings = v.findViewById(R.id.btn_settings);
        tvServer = v.findViewById(R.id.tv_server);
        sp = new SharePreferencesHelper(getContext());
        initComponent();
        return v;
    }

    private void initComponent() {
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new SettingsServerFragments(), "Settings");
                dismiss();
            }
        });

        if(sp.getSPServer() != null){
            tvServer.setText(sp.getSPServer());
        }else{
            tvServer.setText("Are you complete your Settings ?");
        }
        //((MainActivity) getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(width, height);
    }
}
