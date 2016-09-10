package com.app.dianti.util;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangji on 2016/8/17.
 */
public class ExitActivityUtil extends Application {

    private List<Activity> activityList = new LinkedList<Activity>();

    private static ExitActivityUtil instance;

    private ExitActivityUtil() {
    }

    /**
     * 单例模式中获取唯一的activity
     *
     * @return
     */
    public static ExitActivityUtil getInstance() {
        if (instance == null) {
            instance = new ExitActivityUtil();
        }
        return instance;
    }

    /**
     * 添加activity到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有activity并退出
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    /**
     * 遍历所有activity并退出
     */
    public void delete() {
        for (Activity activity : activityList) {
            if (activityList.size() == 1) {
                return;
            } else {
                activity.finish();
            }
        }
    }
}
