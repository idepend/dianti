package com.app.dianti.net.entity;

import java.util.List;

/**
 * @user MycroftWong
 * @date 16/7/3
 * @time 13:30
 */
public class UploadImageEntity {

    public UploadImageEntity() {
    }

    /**
     * code : 200
     * msg : 上传成功！
     * img_urls : ["upload/imgs/1467494630662.jpg"]
     */

    private int code;
    private String msg;
    private List<String> img_urls;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getImg_urls() {
        return img_urls;
    }

    public void setImg_urls(List<String> img_urls) {
        this.img_urls = img_urls;
    }

    @Override
    public String toString() {
        return "UploadImageEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", img_urls=" + img_urls +
                '}';
    }
}
