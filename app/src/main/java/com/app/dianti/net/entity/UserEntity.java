package com.app.dianti.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 12:19
 */
public class UserEntity {

    /**
     * name : test
     * role : admin
     * roleName : 超级管理员
     * token : adminn_1467433123
     */

    private String name;
    private String role;
    private String roleName;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
