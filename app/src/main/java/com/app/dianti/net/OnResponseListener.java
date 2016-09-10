package com.app.dianti.net;

/**
 * @user MycroftWong
 * @date 16/4/12
 * @time 21:30
 */
public interface OnResponseListener<T> {

    void onSuccess(T data);

    void onFailure(String info);

}
