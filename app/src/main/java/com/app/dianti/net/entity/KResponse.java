package com.app.dianti.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 12:17
 */
public class KResponse<T> {

    private int code;

    private String msg;

    private T data;

    public KResponse() {
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
