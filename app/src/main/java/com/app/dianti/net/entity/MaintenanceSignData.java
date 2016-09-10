package com.app.dianti.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/5
 * @time 20:18
 */
public class MaintenanceSignData {

    public MaintenanceSignData(String token, long id, String qrCode, String location, String picture) {
        this.token = token;
        this.id = id;
        this.qrCode = qrCode;
        this.location = location;
        this.picture = picture;
    }

    public String token;
    public long id;
    public String qrCode;
    public String location;
    public String picture;

}
