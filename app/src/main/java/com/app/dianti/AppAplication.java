package com.app.dianti;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.app.dianti.activity.base.AnnualActivity;
import com.app.dianti.activity.base.InspectionActivity;
import com.app.dianti.activity.base.MaintenanceActivity;
import com.app.dianti.activity.base.RescueListActivity;
import com.app.dianti.common.AppContext;
import com.app.dianti.third.BadgeView;
import com.app.dianti.util.Logs;
import com.app.dianti.util.StringUtils;
import com.app.dianti.vo.ResponseData;
import com.pgyersdk.crash.PgyCrashManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

/**
 * Created by afei on 16/8/6.
 */
public class AppAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 蒲公英
        PgyCrashManager.register(this);

        initNotifi();
    }

    private void initNotifi() {
        // 每隔多长时间执行一次
        int period = 1000 * 10;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    getNotifi();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // 延迟30秒启动
        int delyTime = 1000 * 10;
        new Timer().schedule(task, delyTime, period);
    }

    private void getNotifi() {
        if (StringUtils.isBlank(AppContext.userInfo.getToken())) {
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("token", AppContext.userInfo.getToken());
        String parmas = JSON.toJSONString(map);
        OkHttpUtils.post().url(AppContext.API_MSG_NOTIFI).addParams("data", parmas).build().execute(new StringCallback() {

            @Override
            public void onResponse(String respData, int arg1) {
                try {
                    ResponseData responseData = JSON.parseObject(respData, ResponseData.class);
                    if (responseData.getCode().equals("200")) {
                        parseNotifiData(responseData);
                    } else {
                        Logs.e(respData);
                    }
                } catch (Exception e) {
                    Logs.e(e);
                }
            }

            @Override
            public void onError(Call arg0, Exception arg1, int arg2) {
                //tip("请求保存数据失败!");
                Logs.e("获取通知失败: ", arg1);
            }
        });
    }

    /**
     *
     * @param responseData
     */
    private void parseNotifiData(ResponseData responseData) {
        List<Map<String, Object>> msgList = responseData.getDataMap("msg");
        if (msgList == null || msgList.isEmpty()) {
            return;
        }

        //1,维保，2，年检，3，故障，4，巡查
        int type1 = 0;
        int type2 = 0;
        int type3 = 0;
        int type4 = 0;
        for (Map<String, Object> row : msgList) {
            if(row.get("type").toString().equals("1")){
                type1 = type1 + 1;
            }else if(row.get("type").toString().equals("2")){
                type2 = type2 + 1;
            }else if(row.get("type").toString().equals("3")){
                type3 = type3 + 1;
            }else if(row.get("type").toString().equals("4")){
                type4 = type4 + 1;
            }
        }

//        StringBuffer msg = new StringBuffer();
        if(type1 > 0){
//            msg.append(String.format("维保: %d条. " , type1));
            showNotifi(String.format("维保: %d条. " , type1), MaintenanceActivity.class);
        }
        if(type3 > 0){
//            msg.append(String.format("故障: %d条. " , type3));
            showNotifi(String.format("故障: %d条. " , type3), RescueListActivity.class);
        }
        if(type2 > 01){
//            msg.append(String.format("年检: %d条. " , type2));
            showNotifi(String.format("年检: %d条. " , type2), AnnualActivity.class);
        }
        if(type4 > 0){
//            msg.append(String.format("巡查: %d条. " , type4));
            showNotifi(String.format("巡查: %d条. " , type4), InspectionActivity.class);
        }

//        if(msg.length() == 0){
//            return;
//        }
    }

    public void showNotifi(String msg, Class<?> cls){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        Intent intent = new Intent(getApplicationContext(), cls);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 100, intent, 0);

        mBuilder.setContentTitle("消息提醒")//设置通知栏标题
                .setContentText(msg)
                .setContentIntent(contentIntent) //设置通知栏点击意图
//                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(R.drawable.tip_info);//设置通知小ICON

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        mNotificationManager.notify(100, notification);
    }
}
