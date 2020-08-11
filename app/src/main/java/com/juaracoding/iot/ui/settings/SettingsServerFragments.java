package com.juaracoding.iot.ui.settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.juaracoding.iot.MainActivity;
import com.juaracoding.iot.R;
import com.juaracoding.iot.Utils.SharePreferencesHelper;
import com.juaracoding.iot.ui.dashboard.DashboardFragment;

public class SettingsServerFragments extends Fragment {

    private static final String TAG = "Settings.....";
    private EditText etName, etServer, etPort;
    private Switch ssl;
    private Button btnSave;
    private SharePreferencesHelper sp;
    private String connectionKey;

    public SettingsServerFragments() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        etName = root.findViewById(R.id.et_connection_name);
        etServer = root.findViewById(R.id.et_server);
        etPort = root.findViewById(R.id.et_port_server);
        btnSave = root.findViewById(R.id.btn_save);
        //ssl = root.findViewById(R.id.switch_ssl);

        sp = new SharePreferencesHelper(getContext());
        initComponent();
        return root;
    }


    private void initComponent(){
//        etServer.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String format = "tcp://";
//                if(!server.equals("")){
//                    etServer.setText(format);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        etPort.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String format = "1880";
//                if(port.equals("")){
//                    etPort.setText(format);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        if(sp.getSPServer() != null && sp.getSpName() != null){
            etServer.setText(sp.getSPServer());
            etName.setText(sp.getSpName());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String port = etPort.getText().toString();
                final String name = etName.getText().toString();
                final String server = etServer.getText().toString();

                sp.saveSPString("name", name);
                sp.saveSPString("server", server + ":" + port);
                //sp.saveSPString("ssl", ssl.isChecked() ? "Ya":"Tidak");

                if(sp.getSpName() != null && sp.getSPServer()!= null){
                    ((MainActivity) getActivity()).showInfo("Succesfully Save Connecyion");
                    Log.d(TAG, "onClick: " + sp.getSpName() + "\n" + sp.getSPServer());
                    ((MainActivity) getActivity()).loadFragment(new DashboardFragment(), "Dashboard");
                }
            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        String spServer = sp.getSPServer();
//        String  spName = sp.getSpName();
//        String spSsl = sp.getSpSsl();
//
//        if(spServer != null){
//            etServer.setText(spServer);
//        }
//
//        if(spName != null){
//            etName.setText(spName);
//        }
//
////        if(spSsl.equalsIgnoreCase("Ya")){
////            ssl.setChecked(true);
////        }else{
////            ssl.setChecked(false);
////        }
//    }
}