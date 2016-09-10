package com.app.dianti.refactor.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/5
 * @time 19:55
 */
public final class MaintenanceType {

    public MaintenanceType() {
    }

    /**
     * desc : 清洁，门窗完好，照明正常
     * id : 1
     * name : 机房、滑轮间环境
     */

    private String desc;
    private int id;
    private String name;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
