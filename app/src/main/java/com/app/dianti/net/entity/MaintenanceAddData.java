package com.app.dianti.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/3
 * @time 15:00
 */
public class MaintenanceAddData {

    public String token;
    public int type;
    public long plan_date;
    public String ele_code;

    public MaintenanceAddData() {
    }

    public MaintenanceAddData(String token, int type, long plan_date, String ele_code) {
        this.token = token;
        this.type = type;
        this.plan_date = plan_date;
        this.ele_code = ele_code;
    }
}
