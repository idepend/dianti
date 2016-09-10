package com.app.dianti.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/3
 * @time 15:29
 */
public class InspectionAddData {

    public String token;
    public String pic;
    public String desc;
    public String checkList;
    public String eleCode;

    public InspectionAddData() {
    }

    public InspectionAddData(String token, String pic, String desc, String checkList, String eleCode) {
        this.token = token;
        this.pic = pic;
        this.desc = desc;
        this.checkList = checkList;
        this.eleCode = eleCode;
    }
}
