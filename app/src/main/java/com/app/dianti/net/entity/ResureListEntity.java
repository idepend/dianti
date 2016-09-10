package com.app.dianti.net.entity;

import java.util.List;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 12:22
 */
public final class ResureListEntity {

    /**
     * list : [{"device_id":"0","rescue_person":"0","id":"81","ele_addr":"绍兴路538号三立开元大厦","elevator_id":"283","property_dep_id":"0","use_dep_contact_name":null,"log":[],"update_user":"0","use_dep_mobile":"17767115528","is_del":"0","ele_name":"永和坊3-1","type":"2","maintain_dep_id":"182","rescue_dep_id":"0","create_date":"146289159","end_date":"0","tir_person":"0","maintain_dep_name":"三菱电梯杭州分公司","area":null,"rescue_type_id":"0","from":"0","use_id":"181","use_dep_name":"三立时代广场","code":null,"community":"0","maintain_type":"1","status":"4","city":null,"lng":"0.00000000","msg_status":"0","start_date":"0","ele_status":"1","province":null,"ele_create_at":"1465696633","injuries":"0","ele_code":"31103301252016421002","is_success":"1","lat":"0.00000000","reason":"","brief":null},{"device_id":"2","rescue_person":"0","id":"135","ele_addr":"杭州绍兴路538号","elevator_id":"1","property_dep_id":"1","use_dep_contact_name":"","log":[],"update_user":"0","use_dep_mobile":"17767115528","is_del":"0","ele_name":"我的电梯","type":"2","maintain_dep_id":"3","rescue_dep_id":"9","create_date":"1463197635","end_date":"0","tir_person":"0","maintain_dep_name":"杭州华达电梯有限公司","area":"下城区","rescue_type_id":"0","from":"0","use_id":"1","use_dep_name":"三立时代广场物业","code":null,"community":"东新街道","maintain_type":"1","status":"4","city":"杭州市","lng":"120.24146700","msg_status":"0","start_date":"0","ele_status":"6","province":"浙江省","ele_create_at":"1467001404","injuries":"0","ele_code":"3333","is_success":"1","lat":"30.22814900","reason":"","brief":null},{"device_id":"2","rescue_person":"0","id":"134","ele_addr":"杭州绍兴路538号","elevator_id":"1","property_dep_id":"1","use_dep_contact_name":"","log":[],"update_user":"0","use_dep_mobile":"17767115528","is_del":"0","ele_name":"我的电梯","type":"2","maintain_dep_id":"3","rescue_dep_id":"9","create_date":"1463197635","end_date":"0","tir_person":"0","maintain_dep_name":"杭州华达电梯有限公司","area":"下城区","rescue_type_id":"0","from":"0","use_id":"1","use_dep_name":"三立时代广场物业","code":null,"community":"东新街道","maintain_type":"1","status":"4","city":"杭州市","lng":"120.24146700","msg_status":"0","start_date":"0","ele_status":"6","province":"浙江省","ele_create_at":"1467001404","injuries":"0","ele_code":"3333","is_success":"1","lat":"30.22814900","reason":"","brief":null}]
     * currentPage : 1
     * totalPage : 1
     * totalRecord : 3
     */

    private int currentPage;
    private int totalPage;
    private String totalRecord;
    /**
     * device_id : 0
     * rescue_person : 0
     * id : 81
     * ele_addr : 绍兴路538号三立开元大厦
     * elevator_id : 283
     * property_dep_id : 0
     * use_dep_contact_name : null
     * log : []
     * update_user : 0
     * use_dep_mobile : 17767115528
     * is_del : 0
     * ele_name : 永和坊3-1
     * type : 2
     * maintain_dep_id : 182
     * rescue_dep_id : 0
     * create_date : 146289159
     * end_date : 0
     * tir_person : 0
     * maintain_dep_name : 三菱电梯杭州分公司
     * area : null
     * rescue_type_id : 0
     * from : 0
     * use_id : 181
     * use_dep_name : 三立时代广场
     * code : null
     * community : 0
     * maintain_type : 1
     * status : 4
     * city : null
     * lng : 0.00000000
     * msg_status : 0
     * start_date : 0
     * ele_status : 1
     * province : null
     * ele_create_at : 1465696633
     * injuries : 0
     * ele_code : 31103301252016421002
     * is_success : 1
     * lat : 0.00000000
     * reason :
     * brief : null
     */

    private List<ListEntity> list;

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

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity {

        private String device_id;
        private String rescue_person;
        private String id;
        private String ele_addr;
        private String elevator_id;
        private String property_dep_id;
        private Object use_dep_contact_name;
        private String update_user;
        private String use_dep_mobile;
        private String is_del;
        private String ele_name;
        private String type;
        private String maintain_dep_id;
        private String rescue_dep_id;
        private String create_date;
        private String end_date;
        private String tir_person;
        private String maintain_dep_name;
        private Object area;
        private String rescue_type_id;
        private String from;
        private String use_id;
        private String use_dep_name;
        private Object code;
        private String community;
        private String maintain_type;
        private String status;
        private Object city;
        private String lng;
        private String msg_status;
        private String start_date;
        private String ele_status;
        private Object province;
        private String ele_create_at;
        private String injuries;
        private String ele_code;
        private String is_success;
        private String lat;
        private String reason;
        private Object brief;
        private List<?> log;

        private Long last_recv_date;

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getRescue_person() {
            return rescue_person;
        }

        public void setRescue_person(String rescue_person) {
            this.rescue_person = rescue_person;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEle_addr() {
            return ele_addr;
        }

        public void setEle_addr(String ele_addr) {
            this.ele_addr = ele_addr;
        }

        public String getElevator_id() {
            return elevator_id;
        }

        public void setElevator_id(String elevator_id) {
            this.elevator_id = elevator_id;
        }

        public String getProperty_dep_id() {
            return property_dep_id;
        }

        public void setProperty_dep_id(String property_dep_id) {
            this.property_dep_id = property_dep_id;
        }

        public Object getUse_dep_contact_name() {
            return use_dep_contact_name;
        }

        public void setUse_dep_contact_name(Object use_dep_contact_name) {
            this.use_dep_contact_name = use_dep_contact_name;
        }

        public String getUpdate_user() {
            return update_user;
        }

        public void setUpdate_user(String update_user) {
            this.update_user = update_user;
        }

        public String getUse_dep_mobile() {
            return use_dep_mobile;
        }

        public void setUse_dep_mobile(String use_dep_mobile) {
            this.use_dep_mobile = use_dep_mobile;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getEle_name() {
            return ele_name;
        }

        public void setEle_name(String ele_name) {
            this.ele_name = ele_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMaintain_dep_id() {
            return maintain_dep_id;
        }

        public void setMaintain_dep_id(String maintain_dep_id) {
            this.maintain_dep_id = maintain_dep_id;
        }

        public String getRescue_dep_id() {
            return rescue_dep_id;
        }

        public void setRescue_dep_id(String rescue_dep_id) {
            this.rescue_dep_id = rescue_dep_id;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getTir_person() {
            return tir_person;
        }

        public void setTir_person(String tir_person) {
            this.tir_person = tir_person;
        }

        public String getMaintain_dep_name() {
            return maintain_dep_name;
        }

        public void setMaintain_dep_name(String maintain_dep_name) {
            this.maintain_dep_name = maintain_dep_name;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public String getRescue_type_id() {
            return rescue_type_id;
        }

        public void setRescue_type_id(String rescue_type_id) {
            this.rescue_type_id = rescue_type_id;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getUse_id() {
            return use_id;
        }

        public void setUse_id(String use_id) {
            this.use_id = use_id;
        }

        public String getUse_dep_name() {
            return use_dep_name;
        }

        public void setUse_dep_name(String use_dep_name) {
            this.use_dep_name = use_dep_name;
        }

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getMaintain_type() {
            return maintain_type;
        }

        public void setMaintain_type(String maintain_type) {
            this.maintain_type = maintain_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getMsg_status() {
            return msg_status;
        }

        public void setMsg_status(String msg_status) {
            this.msg_status = msg_status;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEle_status() {
            return ele_status;
        }

        public void setEle_status(String ele_status) {
            this.ele_status = ele_status;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public String getEle_create_at() {
            return ele_create_at;
        }

        public void setEle_create_at(String ele_create_at) {
            this.ele_create_at = ele_create_at;
        }

        public String getInjuries() {
            return injuries;
        }

        public void setInjuries(String injuries) {
            this.injuries = injuries;
        }

        public String getEle_code() {
            return ele_code;
        }

        public void setEle_code(String ele_code) {
            this.ele_code = ele_code;
        }

        public String getIs_success() {
            return is_success;
        }

        public void setIs_success(String is_success) {
            this.is_success = is_success;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Object getBrief() {
            return brief;
        }

        public void setBrief(Object brief) {
            this.brief = brief;
        }

        public List<?> getLog() {
            return log;
        }

        public void setLog(List<?> log) {
            this.log = log;
        }

        public Long getLast_recv_date() {
            return last_recv_date;
        }

        public void setLast_recv_date(Long last_recv_date) {
            this.last_recv_date = last_recv_date;
        }
    }
}
