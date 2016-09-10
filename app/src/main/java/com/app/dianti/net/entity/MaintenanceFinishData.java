package com.app.dianti.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/5
 * @time 20:28
 */
public final class MaintenanceFinishData {

    public MaintenanceFinishData(String token, long id, String desc, String prove, String checkList) {
        this.token = token;
        this.id = id;
        this.desc = desc;
        this.prove = prove;
        this.checkList = checkList;
    }

    public String token;
    public long id;
    public String desc;
    public String prove;
    public String checkList;

}
