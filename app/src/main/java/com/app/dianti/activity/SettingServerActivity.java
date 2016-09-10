package com.app.dianti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.dianti.R;
import com.app.dianti.util.UserPreference;

/**
 * Created by afei on 16/7/14.
 */
public class SettingServerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_server);
        super.initTitleBar("平台设置");
        initView();
    }

    private void initView() {
        final EditText serverAddressEditText = (EditText) findViewById(R.id.serverAddress);
        final EditText serverAddress2EditText = (EditText) findViewById(R.id.serverAddress2);

        //海洋
        UserPreference.ensureIntializePreference(getApplicationContext());
        String localServerAddress = UserPreference.get("serverAddress", "");
        if(!localServerAddress.equals("")){
            serverAddressEditText.setText(localServerAddress);
        }

        //汉川
        String localServerAddress2 = UserPreference.get("serverAddress2", "");
        if(!localServerAddress2.equals("")){
            serverAddress2EditText.setText(localServerAddress2);
        }

        findViewById(R.id.saveServerAddressBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String serverAddress = serverAddressEditText.getText().toString();
                String serverAddress2 = serverAddress2EditText.getText().toString();
                UserPreference.ensureIntializePreference(getApplicationContext());
                UserPreference.save("serverAddress", serverAddress.trim());
                UserPreference.save("serverAddress2", serverAddress2.trim());
                //开启广播更新登录的logo
                Intent intent=new Intent();
                intent.setAction("LoginServerAddress");
                intent.putExtra("serverAddress",serverAddress.trim());
                intent.putExtra("sererAddress2",serverAddress2.trim());
                sendBroadcast(intent);
                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
