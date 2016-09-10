package com.app.dianti.refactor.net.entity;

import java.util.List;

/**
 * @user MycroftWong
 * @date 16/7/4
 * @time 21:19
 */
public class ElevatorList {

    /**
     * list : [{"data":"value"}]
     * currentPage : 1
     * totalPage : 346
     * totalRecord : 6913
     */

    private int currentPage;
    private int totalPage;
    private String totalRecord;
    private List<Elevator> list;

    /**
     * data : value
     */

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<Elevator> getList() {
        return list;
    }

    public void setList(List<Elevator> list) {
        this.list = list;
    }

}
