package com.app.dianti.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 12:21
 */
public final class LoginUserEntity {

    private String username;
    private String password;

    public LoginUserEntity() {
    }

    public LoginUserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
