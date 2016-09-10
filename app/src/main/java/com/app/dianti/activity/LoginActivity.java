package com.app.dianti.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dianti.R;
import com.app.dianti.common.AppContext;
import com.app.dianti.util.ExitActivityUtil;
import com.app.dianti.util.MD5Util;
import com.app.dianti.util.StringUtils;
import com.app.dianti.util.UserPreference;
import com.app.dianti.vo.ResponseData;
import com.app.dianti.vo.UserInfo;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class LoginActivity extends BaseActivity implements OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private SharedPreferences sharedPreferences;
    public static final String SHARAEPREFEREN_INFO = "saveInfo";
    private String userName="";
    private ImageView loginLogo;
    private TextView logoNameTv;
    private String serverAddress1="";
    private String serverAddress2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        super.initTitleBar("档案查询");
        sharedPreferences=getSharedPreferences(SHARAEPREFEREN_INFO, Activity.MODE_PRIVATE);
        userName=sharedPreferences.getString("userName","");
        if (!userName.equals("") && userName!=""){
            goActivity(MainActivity.class);
            finish();
        }
        initView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        registerReceiver();
    }

    private void initView() {
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.settingBtn).setOnClickListener(this);
        loginLogo= (ImageView) findViewById(R.id.logo);
        logoNameTv= (TextView) findViewById(R.id.logo_name);
//         Log.i("wj", "login: "+serverAddress2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                // startActivity(new Intent(LoginActivity.this,
                // MainActivity.class));

                login();
                break;
            case R.id.settingBtn:
                goActivity(SettingServerActivity.class);
                break;

            default:
                break;
        }
    }

    private void login() {
        /*
         *加载服务器地址
         */
        //海洋
        UserPreference.ensureIntializePreference(getApplicationContext());
        String localServerAddress = UserPreference.get("serverAddress", "");
        if (localServerAddress.equals("")) {
            //tip("服务器地址配置不正确");
            AppContext.SERVER_URL = "http://218.75.75.34:10000";
        } else {
            AppContext.SERVER_URL = localServerAddress;
        }

        //汉川
        String localServerAddress2 = UserPreference.get("serverAddress2", "");
        if (localServerAddress2.equals("")) {
            //tip("服务器地址配置不正确");
            AppContext.SERVER_URL_2 = "http://218.75.75.34:88";
        } else {
            AppContext.SERVER_URL_2 = localServerAddress2;
        }

        AppContext.apiUrlBuild();

        final EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);

        if (StringUtils.isBlank(usernameText.getText().toString())) {
            tip("请输入用户名");
            return;
        }
        if (StringUtils.isBlank(passwordText.getText().toString())) {
            tip("请输入密码");
            return;
        }

        final String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        password = MD5Util.getMD5Code(password).toUpperCase();

        try {
//            Log.i("wj", "login: "+AppContext.API_LOGIN_URL);
            OkHttpUtils.post().url(AppContext.API_LOGIN_URL).addParams("data", "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}").build().execute(new StringCallback() {
                @Override
                public void onResponse(String respData, int arg1) {
                    ResponseData responseData = new Gson().fromJson(respData, new TypeToken<ResponseData>() {
                    }.getType());
                    if (responseData.getCode().equals("200")) {
                        /**
                         * 角色说明
                         * admin： 超级管理员
                         testing:测试用户组
                         maintain_admin: 维保管理员
                         maintain: 维保人员
                         property: 物业管理员
                         */
                        UserInfo userInfo = new UserInfo();
                        userInfo.setUsername(usernameText.getText().toString());
                        userInfo.setName(responseData.getDataString("name"));
                        userInfo.setToken(responseData.getDataString("token"));
                        userInfo.setRole(responseData.getDataString("role"));
                        userInfo.setRoleName(responseData.getDataString("roleName"));
                        if (StringUtils.isBlank(userInfo.getName())) {
                            userInfo.setName(username);
                        }

                        AppContext.userInfo = userInfo;
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("name",userInfo.getName());
                        editor.putString("roleName",userInfo.getRoleName());
                        editor.putString("role",userInfo.getRole());
                        editor.putString("token",userInfo.getToken());
                        editor.putString("userName",userInfo.getUsername());
                        editor.commit();
                        goActivity(MainActivity.class);
                        finish();
                    } else {
                        tip("登录失败!请检查用户名密码是否正确");
                    }
                }
                @Override
                public void onError(Call arg0, Exception arg1, int arg2) {
                    tip("登录失败!网络请求错误!");
                }
            });
        }catch (Exception e){
            Toast.makeText(this,"登录失败，请检查您当前的平台地址链接！",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    /**
     * 注册广播
     */
    private void registerReceiver(){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("LoginServerAddress");
        registerReceiver(broadcastReceiver,intentFilter);

    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals("LoginServerAddress")){
                serverAddress1=intent.getStringExtra("sererAddress2");
                serverAddress2=intent.getStringExtra("serverAddress");
                Log.i("wj", "onReceive: "+serverAddress1+"  2="+serverAddress2);
                if (serverAddress1.equals("http://121.40.89.242:80") || serverAddress2.equals("http://121.40.89.242:10001")){
                    loginLogo.setImageResource(R.drawable.logo);
                    logoNameTv.setVisibility(View.GONE);
                }else{
                    loginLogo.setImageResource(R.mipmap.login);
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //调用退出函数
            exitBy2Click();
        }
        return false;
    }

    private long exitTime = 0;

    /**
     * 双击退出事件
     */
    private void exitBy2Click() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            ExitActivityUtil.getInstance().delete();
            this.finish();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
