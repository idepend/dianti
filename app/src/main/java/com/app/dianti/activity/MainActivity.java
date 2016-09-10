package com.app.dianti.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.app.dianti.R;
import com.app.dianti.activity.base.InspectionActivity;
import com.app.dianti.activity.base.RescueListActivity;
import com.app.dianti.activity.map.MapActivity;
import com.app.dianti.activity.myhistory.MyHistroyActivity;
import com.app.dianti.activity.notice.NoticeActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.event.RefreshEvent;
import com.app.dianti.third.BadgeView;
import com.app.dianti.util.Logs;
import com.app.dianti.util.UserPreference;
import com.app.dianti.vo.ResponseData;
import com.app.dianti.vo.UserInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

public class MainActivity extends BaseActivity implements OnClickListener {

    private ImageButton btn_xucha;
    private ImageButton btn_weibao;
    private TextView titleTv;
    private SharedPreferences sharedPreferences;
    private UserInfo userInfo;
    private boolean isWeibao;
    private boolean isXuncha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        EventBus.getDefault().register(this);
        sharedPreferences=getSharedPreferences(LoginActivity.SHARAEPREFEREN_INFO, Activity.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName","");
        String name = sharedPreferences.getString("name","");
        String roleName = sharedPreferences.getString("roleName","");
        String role = sharedPreferences.getString("role","");
        String token = sharedPreferences.getString("token","");
        userInfo=new UserInfo();
        userInfo.setUsername(userName);
        userInfo.setRole(role);
        userInfo.setName(name);
        userInfo.setToken(token);
        userInfo.setRoleName(roleName);
        if (userInfo != null ){
            AppContext.userInfo=userInfo;
        }

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
        init();

//        Log.e("wj", "onCreate: "+ getVersion()+" versionCode="+getVersionCode());
    }

    @Subscribe(threadMode = MAIN)
    public void onEvent(RefreshEvent event) {
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", AppContext.userInfo.getToken());
        String parmas = JSON.toJSONString(map);
        OkHttpUtils.post().url(AppContext.API_ALL_COUNT).addParams("data", parmas).build().execute(new StringCallback() {

            @Override
            public void onResponse(final String respData, int arg1) {
                try {
                    final ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseData.getCode().equals("200")) {
                                Integer annualNum = responseData.getDataInt("annualNum");
                                if (annualNum != null && annualNum > 0) {
                                    View target = findViewById(R.id.nianjianBtn);
                                    BadgeView badge= (BadgeView) target.getTag();
                                    if (badge==null) {
                                        badge = new BadgeView(getApplicationContext(), target);
                                        target.setTag(badge);
                                    }
                                    badge.setText(annualNum.toString());
                                    badge.show();
                                }
                                Integer maintenanceNum = responseData.getDataInt("maintenanceNum");
                                if (maintenanceNum != null && maintenanceNum > 0) {
                                    View target = findViewById(R.id.weibaoBtn);
                                    BadgeView badge = (BadgeView) target.getTag();
                                    if (badge==null){
                                        badge = new BadgeView(getApplicationContext(), target);
                                        target.setTag(badge);
                                    }

                                    if (!isWeibao && isXuncha){
                                        badge.setVisibility(View.GONE);
                                        badge.setBackgroundColor(Color.parseColor("#00000000"));
                                    }else{
                                        badge.setText(maintenanceNum.toString());
                                    }
                                    badge.show();
                                }
                                Integer rescueNum = responseData.getDataInt("rescueNum");
                                if (rescueNum != null && rescueNum > 0) {
                                    View target = findViewById(R.id.jiuyuanBtn);
                                    BadgeView badge = (BadgeView) target.getTag();
                                    if (badge == null) {
                                        badge = new BadgeView(getApplicationContext(), target);
                                        target .setTag(badge);
                                    }
                                    badge.setText(rescueNum.toString());
                                    badge.show();
                                }
                                Integer patrolNum = responseData.getDataInt("patrolNum");
                                if (patrolNum != null && patrolNum > 0) {
                                    View target = findViewById(R.id.xunchaBtn);
                                    BadgeView badge = (BadgeView) target.getTag();
                                    if (badge==null) {
                                        badge = new BadgeView(getApplicationContext(), target);
                                        target.setTag(badge);
                                    }
                                    if (isWeibao && !isXuncha){
                                        badge.setVisibility(View.GONE);
                                        badge.setBackgroundColor(Color.parseColor("#00000000"));
                                    }else{
                                        badge.setText(patrolNum.toString());
                                    }

                                    badge.show();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    Logs.e(e);
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                //tip("请求保存数据失败!");
            }
        });
    }


    /**
     * 获取当前版本号
     * @return
     */
    public String getVersion() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        return version;
    }

    /**
     * 获取版本号
     * @return
     */
    public int getVersionCode(){
        PackageManager manager=getPackageManager();
        PackageInfo info=null;
        try {
            info=manager.getPackageInfo(getPackageName(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
        int code=info.versionCode;
        return code;
    }

    private void init() {
        findViewById(R.id.danganBtn).setOnClickListener(this);
        findViewById(R.id.weibaoBtn).setOnClickListener(this);
        btn_weibao= (ImageButton) findViewById(R.id.weibaoBtn);
        btn_xucha= (ImageButton) findViewById(R.id.xunchaBtn);
        titleTv= (TextView) findViewById(R.id.main_title);
        findViewById(R.id.xunchaBtn).setOnClickListener(this);
        findViewById(R.id.nianjianBtn).setOnClickListener(this);
        findViewById(R.id.jiuyuanBtn).setOnClickListener(this);
        findViewById(R.id.gonggaoBtn).setOnClickListener(this);
        findViewById(R.id.wodelishiBtn).setOnClickListener(this);
        findViewById(R.id.ele_map).setOnClickListener(this);
        findViewById(R.id.tuichuBtn).setOnClickListener(this);

        TextView usernameText = (TextView) (findViewById(R.id.usernameText));
        usernameText.setText(AppContext.userInfo.getName());


        TextView rolenameText = (TextView) (findViewById(R.id.rolenameText));
        rolenameText.setText(AppContext.userInfo.getRoleName());

        if (AppContext.userInfo.getRoleName().equals("维保管理员")){
            isWeibao=true;
            isXuncha=false;
            btn_xucha.setEnabled(false);
            btn_xucha.setBackgroundResource(R.drawable.diantixuncha_enabled);
        }else if (AppContext.userInfo.getRoleName().equals("巡查管理员")){
            isWeibao=false;
            isXuncha=true;
            btn_weibao.setEnabled(false);
            btn_weibao.setBackgroundResource(R.drawable.diantiweibao_enabled);
        }

    initData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.danganBtn:
//                goActivity(ElevatorListActivity.class);
                goActivity(com.app.dianti.activity.base.ElevatorListActivity.class);
                // goActivity(DossierActivity.class);
                break;

            case R.id.ele_map:
                goActivity(MapActivity.class);
                break;
            case R.id.weibaoBtn:
//			goActivity(MaintenanceActivity.class);
                goActivity(com.app.dianti.activity.base.MaintenanceActivity.class);
                break;

            case R.id.xunchaBtn:
                goActivity(InspectionActivity.class);
                break;

            case R.id.nianjianBtn:
//                goActivity(AnnualActivity.class);
                goActivity(com.app.dianti.activity.base.AnnualActivity.class);
                break;

            case R.id.jiuyuanBtn:
//                goActivity(ReskjuActivity.class);
//                goActivity(RescueListActivity.class);
                startActivityForResult(new Intent(this,RescueListActivity.class),10);
                break;

            case R.id.gonggaoBtn:
                goActivity(NoticeActivity.class);
                break;

            case R.id.wodelishiBtn:
                goActivity(MyHistroyActivity.class);
                break;

            case R.id.tuichuBtn:
                // Intent home = new Intent(Intent.ACTION_MAIN);
                // home.addCategory(Intent.CATEGORY_HOME);
                // startActivity(home);

                // testHttp();
                final AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("是否退出当前账号！");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        sharedPreferences.edit().clear().commit();
                        goActivity(LoginActivity.class);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();

                break;

            default:
                break;
        }
    }
}
