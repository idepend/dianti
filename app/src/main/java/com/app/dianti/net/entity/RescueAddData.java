package com.app.dianti.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/3
 * @time 16:03
 */
public class RescueAddData {

    public RescueAddData() {
    }

    public RescueAddData(String id, String token, String eleCode, int tirPeople, int rescuedPeople, int injuries, int isSuccess, long startTime, long endTime, String reason, String pic) {
        this.id = id;
        this.token = token;
        this.eleCode = eleCode;
        this.tirPeople = tirPeople;
        this.rescuedPeople = rescuedPeople;
        this.injuries = injuries;
        this.isSuccess = isSuccess;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.pic = pic;
    }

    public String id;
    public String token;
    public String eleCode;
    public int tirPeople;
    public int rescuedPeople;
    public int injuries;
    public int isSuccess;
    public long startTime;
    public long endTime;
    public String reason;
    public String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEleCode() {
        return eleCode;
    }

    public void setEleCode(String eleCode) {
        this.eleCode = eleCode;
    }

    public int getTirPeople() {
        return tirPeople;
    }

    public void setTirPeople(int tirPeople) {
        this.tirPeople = tirPeople;
    }

    public int getRescuedPeople() {
        return rescuedPeople;
    }

    public void setRescuedPeople(int rescuedPeople) {
        this.rescuedPeople = rescuedPeople;
    }

    public int getInjuries() {
        return injuries;
    }

    public void setInjuries(int injuries) {
        this.injuries = injuries;
    }

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
