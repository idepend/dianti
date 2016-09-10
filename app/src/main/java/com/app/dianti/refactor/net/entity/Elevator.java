package com.app.dianti.refactor.net.entity;

/**
 * @user MycroftWong
 * @date 16/7/4
 * @time 21:16
 */
public final class Elevator {

    public Elevator() {
    }

    /**
     * device_id : 2
     * device : {"id":"2","lock_ver":"0","create_date":"0","update_date":"0","elevator_id":null,"type":"0","dep_id":"0","code":"1234567890123456","order":"0","lat":"0.00","ip":null,"is_del":"0","manufacturer":"0","lng":"0.00","media_id":"0","name":"x","status":"0"}
     * id : 1
     * ele_addr : 杭州绍兴路538号
     * property_dep_id : 1
     * use_dep_contact_name :
     * camera : {"id":"43","lock_ver":"0","create_date":"1466217129","update_date":"1466933654","elevator_id":null,"type":"3","dep_id":"1","code":"45664556","order":"0","lat":"0.00","ip":null,"is_del":"0","manufacturer":"2","lng":"0.00","media_id":"4","name":"公司监控","status":"0"}
     * reg_id : 1
     * use_dep_mobile : 17767115528
     * ele_name : 我的电梯
     * args_id : 1
     * is_del : 0
     * product : {"postcode":null,"legal_name":"","place":null,"code":"12345","own_id":"0","res_phone":"13845678765","short_name":"三立","fax":"","lng":"0.00","legal":"1","brief":"","create_date":"1463062927","name":"三立时代广场物业","res_name":"陈锋","type":"2","inside_code":null,"id":"1","level":"0","qualification":"","mobile":"17767115528","lat":"0.00","pid":"0","own_name":"","order":"0","res_id":"1","address":"下城区绍兴路538号","update_date":"1467537103"}
     * rescue : {"postcode":null,"legal_name":"","place":null,"code":"88888888kkkkk","own_id":"0","res_phone":"fsdfkjhsdk","short_name":"救援单位","fax":"","lng":"0.00","legal":"1","brief":"","create_date":"1464876165","name":"救援单位","res_name":"救援单位","type":"4","inside_code":null,"id":"9","level":"0","qualification":"TS2310011-2008","mobile":"17767115528","lat":"0.00","pid":"0","own_name":"","order":"0","res_id":"1","address":"","update_date":"1464876165"}
     * design : null
     * camera_id : 43
     * maintain_dep_id : 3
     * rescue_dep_id : 9
     * create_date : 1467001404
     * area : 下城区
     * maintain_dep_name : 杭州华达电梯有限公司
     * info : {"id":"1","dengji_no":"","elevator_id":"1","inspection_date":"1433865600","conclusion_name":"","province":"浙江省","manufact_num":"","area":"下城区","inspection_cate_name":"","repaire_date":"0","manufact_date":"0","into_use_date":"0","city":"杭州市","install_complete_date":"-28800","inside_code":null,"repaire_cycle":"60","maintain_cycle":"6","remarks":""}
     * community : 东新街道
     * use_id : 1
     * design_id : 0
     * use_dep_name : 三立时代广场物业
     * code : 3333
     * property : {"postcode":null,"legal_name":"","place":null,"code":"12345","own_id":"0","res_phone":"13845678765","short_name":"三立","fax":"","lng":"0.00","legal":"1","brief":"","create_date":"1463062927","name":"三立时代广场物业","res_name":"陈锋","type":"2","inside_code":null,"id":"1","level":"0","qualification":"","mobile":"17767115528","lat":"0.00","pid":"0","own_name":"","order":"0","res_id":"1","address":"下城区绍兴路538号","update_date":"1467537103"}
     * maintain_type : 1
     * status : {"failureinfo":"0","elevator_id":"1","id":"16","updatetime":"1293841312","servicemodel":"1","is_voice":"1","haspeople":"1","curfloor":"3","direction":"0","doorstatus":"0"}
     * city : 杭州市
     * lng : 120.24146700
     * product_id : 1
     * install_id : 0
     * province : 浙江省
     * maintain : {"postcode":null,"legal_name":"","place":null,"code":null,"own_id":"0","res_phone":"杭州华达电梯有限公司","short_name":"杭州华达电梯有限公司","fax":"","lng":"0.00","legal":"1","brief":"","create_date":"1463063295","name":"杭州华达电梯有限公司","res_name":"杭州华达电梯有限公司","type":"3","inside_code":null,"id":"3","level":"11","qualification":null,"mobile":"17767115528","lat":"0.00","pid":"0","own_name":"","order":"111","res_id":"1","address":"","update_date":"1463065656"}
     * lat : 30.22814900
     * install : null
     * args : {"id":"1","door":"24","rated_speed":"2","overlay":"移动","elevator_id":"0","sb_no":"","brand":"三菱","controll_type":"曳引式","load_capacity":"1000","type_id":"1","floor":"24","model":"","name":"我的电梯","station":"24"}
     * reg : {"id":"1","create_date":"1464883200","use_place":"办公楼","update_date":"1465488000","inner_code":"02","elevator_id":"0","licence_code":"","inspect_institution":"杭州市特种设备检测研究院","area_id":"东新街道","code":"3333","address":"杭州绍兴路538号","institution":"杭州市质量技术监督局","operation_user":"王谦","lat":"30.22814900","reg_user":"","lng":"120.24146700","rescue_code":"","status":"1"}
     * use : {"postcode":null,"legal_name":"","place":null,"code":"12345","own_id":"0","res_phone":"13845678765","short_name":"三立","fax":"","lng":"0.00","legal":"1","brief":"","create_date":"1463062927","name":"三立时代广场物业","res_name":"陈锋","type":"2","inside_code":null,"id":"1","level":"0","qualification":"","mobile":"17767115528","lat":"0.00","pid":"0","own_name":"","order":"0","res_id":"1","address":"下城区绍兴路538号","update_date":"1467537103"}
     */

    private String device_id;
    /**
     * id : 2
     * lock_ver : 0
     * create_date : 0
     * update_date : 0
     * elevator_id : null
     * type : 0
     * dep_id : 0
     * code : 1234567890123456
     * order : 0
     * lat : 0.00
     * ip : null
     * is_del : 0
     * manufacturer : 0
     * lng : 0.00
     * media_id : 0
     * name : x
     * status : 0
     */

    private DeviceEntity device;
    private String id;
    private String ele_addr;
    private String property_dep_id;
    private String use_dep_contact_name;
    /**
     * id : 43
     * lock_ver : 0
     * create_date : 1466217129
     * update_date : 1466933654
     * elevator_id : null
     * type : 3
     * dep_id : 1
     * code : 45664556
     * order : 0
     * lat : 0.00
     * ip : null
     * is_del : 0
     * manufacturer : 2
     * lng : 0.00
     * media_id : 4
     * name : 公司监控
     * status : 0
     */

    private CameraEntity camera;
    private String reg_id;
    private String use_dep_mobile;
    private String ele_name;
    private String args_id;
    private String is_del;
    /**
     * postcode : null
     * legal_name :
     * place : null
     * code : 12345
     * own_id : 0
     * res_phone : 13845678765
     * short_name : 三立
     * fax :
     * lng : 0.00
     * legal : 1
     * brief :
     * create_date : 1463062927
     * name : 三立时代广场物业
     * res_name : 陈锋
     * type : 2
     * inside_code : null
     * id : 1
     * level : 0
     * qualification :
     * mobile : 17767115528
     * lat : 0.00
     * pid : 0
     * own_name :
     * order : 0
     * res_id : 1
     * address : 下城区绍兴路538号
     * update_date : 1467537103
     */

    private ProductEntity product;
    /**
     * postcode : null
     * legal_name :
     * place : null
     * code : 88888888kkkkk
     * own_id : 0
     * res_phone : fsdfkjhsdk
     * short_name : 救援单位
     * fax :
     * lng : 0.00
     * legal : 1
     * brief :
     * create_date : 1464876165
     * name : 救援单位
     * res_name : 救援单位
     * type : 4
     * inside_code : null
     * id : 9
     * level : 0
     * qualification : TS2310011-2008
     * mobile : 17767115528
     * lat : 0.00
     * pid : 0
     * own_name :
     * order : 0
     * res_id : 1
     * address :
     * update_date : 1464876165
     */

    private RescueEntity rescue;
    private Object design;
    private String camera_id;
    private String maintain_dep_id;
    private String rescue_dep_id;
    private String create_date;
    private String area;
    private String maintain_dep_name;
    /**
     * id : 1
     * dengji_no :
     * elevator_id : 1
     * inspection_date : 1433865600
     * conclusion_name :
     * province : 浙江省
     * manufact_num :
     * area : 下城区
     * inspection_cate_name :
     * repaire_date : 0
     * manufact_date : 0
     * into_use_date : 0
     * city : 杭州市
     * install_complete_date : -28800
     * inside_code : null
     * repaire_cycle : 60
     * maintain_cycle : 6
     * remarks :
     */

    private InfoEntity info;
    private String community;
    private String use_id;
    private String design_id;
    private String use_dep_name;
    private String code;
    /**
     * postcode : null
     * legal_name :
     * place : null
     * code : 12345
     * own_id : 0
     * res_phone : 13845678765
     * short_name : 三立
     * fax :
     * lng : 0.00
     * legal : 1
     * brief :
     * create_date : 1463062927
     * name : 三立时代广场物业
     * res_name : 陈锋
     * type : 2
     * inside_code : null
     * id : 1
     * level : 0
     * qualification :
     * mobile : 17767115528
     * lat : 0.00
     * pid : 0
     * own_name :
     * order : 0
     * res_id : 1
     * address : 下城区绍兴路538号
     * update_date : 1467537103
     */

    private PropertyEntity property;
    private String maintain_type;
    /**
     * failureinfo : 0
     * elevator_id : 1
     * id : 16
     * updatetime : 1293841312
     * servicemodel : 1
     * is_voice : 1
     * haspeople : 1
     * curfloor : 3
     * direction : 0
     * doorstatus : 0
     */

    private StatusEntity status;
    private String city;
    private String lng;
    private String product_id;
    private String install_id;
    private String province;
    /**
     * postcode : null
     * legal_name :
     * place : null
     * code : null
     * own_id : 0
     * res_phone : 杭州华达电梯有限公司
     * short_name : 杭州华达电梯有限公司
     * fax :
     * lng : 0.00
     * legal : 1
     * brief :
     * create_date : 1463063295
     * name : 杭州华达电梯有限公司
     * res_name : 杭州华达电梯有限公司
     * type : 3
     * inside_code : null
     * id : 3
     * level : 11
     * qualification : null
     * mobile : 17767115528
     * lat : 0.00
     * pid : 0
     * own_name :
     * order : 111
     * res_id : 1
     * address :
     * update_date : 1463065656
     */

    private MaintainEntity maintain;
    private String lat;
    private Object install;
    /**
     * id : 1
     * door : 24
     * rated_speed : 2
     * overlay : 移动
     * elevator_id : 0
     * sb_no :
     * brand : 三菱
     * controll_type : 曳引式
     * load_capacity : 1000
     * type_id : 1
     * floor : 24
     * model :
     * name : 我的电梯
     * station : 24
     */

    private ArgsEntity args;
    /**
     * id : 1
     * create_date : 1464883200
     * use_place : 办公楼
     * update_date : 1465488000
     * inner_code : 02
     * elevator_id : 0
     * licence_code :
     * inspect_institution : 杭州市特种设备检测研究院
     * area_id : 东新街道
     * code : 3333
     * address : 杭州绍兴路538号
     * institution : 杭州市质量技术监督局
     * operation_user : 王谦
     * lat : 30.22814900
     * reg_user :
     * lng : 120.24146700
     * rescue_code :
     * status : 1
     */

    private RegEntity reg;
    /**
     * postcode : null
     * legal_name :
     * place : null
     * code : 12345
     * own_id : 0
     * res_phone : 13845678765
     * short_name : 三立
     * fax :
     * lng : 0.00
     * legal : 1
     * brief :
     * create_date : 1463062927
     * name : 三立时代广场物业
     * res_name : 陈锋
     * type : 2
     * inside_code : null
     * id : 1
     * level : 0
     * qualification :
     * mobile : 17767115528
     * lat : 0.00
     * pid : 0
     * own_name :
     * order : 0
     * res_id : 1
     * address : 下城区绍兴路538号
     * update_date : 1467537103
     */

    private UseEntity use;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
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

    public String getProperty_dep_id() {
        return property_dep_id;
    }

    public void setProperty_dep_id(String property_dep_id) {
        this.property_dep_id = property_dep_id;
    }

    public String getUse_dep_contact_name() {
        return use_dep_contact_name;
    }

    public void setUse_dep_contact_name(String use_dep_contact_name) {
        this.use_dep_contact_name = use_dep_contact_name;
    }

    public CameraEntity getCamera() {
        return camera;
    }

    public void setCamera(CameraEntity camera) {
        this.camera = camera;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getUse_dep_mobile() {
        return use_dep_mobile;
    }

    public void setUse_dep_mobile(String use_dep_mobile) {
        this.use_dep_mobile = use_dep_mobile;
    }

    public String getEle_name() {
        return ele_name;
    }

    public void setEle_name(String ele_name) {
        this.ele_name = ele_name;
    }

    public String getArgs_id() {
        return args_id;
    }

    public void setArgs_id(String args_id) {
        this.args_id = args_id;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public RescueEntity getRescue() {
        return rescue;
    }

    public void setRescue(RescueEntity rescue) {
        this.rescue = rescue;
    }

    public Object getDesign() {
        return design;
    }

    public void setDesign(Object design) {
        this.design = design;
    }

    public String getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(String camera_id) {
        this.camera_id = camera_id;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMaintain_dep_name() {
        return maintain_dep_name;
    }

    public void setMaintain_dep_name(String maintain_dep_name) {
        this.maintain_dep_name = maintain_dep_name;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getUse_id() {
        return use_id;
    }

    public void setUse_id(String use_id) {
        this.use_id = use_id;
    }

    public String getDesign_id() {
        return design_id;
    }

    public void setDesign_id(String design_id) {
        this.design_id = design_id;
    }

    public String getUse_dep_name() {
        return use_dep_name;
    }

    public void setUse_dep_name(String use_dep_name) {
        this.use_dep_name = use_dep_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }

    public String getMaintain_type() {
        return maintain_type;
    }

    public void setMaintain_type(String maintain_type) {
        this.maintain_type = maintain_type;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getInstall_id() {
        return install_id;
    }

    public void setInstall_id(String install_id) {
        this.install_id = install_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public MaintainEntity getMaintain() {
        return maintain;
    }

    public void setMaintain(MaintainEntity maintain) {
        this.maintain = maintain;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Object getInstall() {
        return install;
    }

    public void setInstall(Object install) {
        this.install = install;
    }

    public ArgsEntity getArgs() {
        return args;
    }

    public void setArgs(ArgsEntity args) {
        this.args = args;
    }

    public RegEntity getReg() {
        return reg;
    }

    public void setReg(RegEntity reg) {
        this.reg = reg;
    }

    public UseEntity getUse() {
        return use;
    }

    public void setUse(UseEntity use) {
        this.use = use;
    }

    public static class DeviceEntity {

        private String id;
        private String lock_ver;
        private String create_date;
        private String update_date;
        private Object elevator_id;
        private String type;
        private String dep_id;
        private String code;
        private String order;
        private String lat;
        private Object ip;
        private String is_del;
        private String manufacturer;
        private String lng;
        private String media_id;
        private String name;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLock_ver() {
            return lock_ver;
        }

        public void setLock_ver(String lock_ver) {
            this.lock_ver = lock_ver;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public Object getElevator_id() {
            return elevator_id;
        }

        public void setElevator_id(Object elevator_id) {
            this.elevator_id = elevator_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDep_id() {
            return dep_id;
        }

        public void setDep_id(String dep_id) {
            this.dep_id = dep_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public Object getIp() {
            return ip;
        }

        public void setIp(Object ip) {
            this.ip = ip;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class CameraEntity {

        private String id;
        private String lock_ver;
        private String create_date;
        private String update_date;
        private Object elevator_id;
        private String type;
        private String dep_id;
        private String code;
        private String order;
        private String lat;
        private Object ip;
        private String is_del;
        private String manufacturer;
        private String lng;
        private String media_id;
        private String name;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLock_ver() {
            return lock_ver;
        }

        public void setLock_ver(String lock_ver) {
            this.lock_ver = lock_ver;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public Object getElevator_id() {
            return elevator_id;
        }

        public void setElevator_id(Object elevator_id) {
            this.elevator_id = elevator_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDep_id() {
            return dep_id;
        }

        public void setDep_id(String dep_id) {
            this.dep_id = dep_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public Object getIp() {
            return ip;
        }

        public void setIp(Object ip) {
            this.ip = ip;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ProductEntity {

        private Object postcode;
        private String legal_name;
        private Object place;
        private String code;
        private String own_id;
        private String res_phone;
        private String short_name;
        private String fax;
        private String lng;
        private String legal;
        private String brief;
        private String create_date;
        private String name;
        private String res_name;
        private String type;
        private Object inside_code;
        private String id;
        private String level;
        private String qualification;
        private String mobile;
        private String lat;
        private String pid;
        private String own_name;
        private String order;
        private String res_id;
        private String address;
        private String update_date;

        public Object getPostcode() {
            return postcode;
        }

        public void setPostcode(Object postcode) {
            this.postcode = postcode;
        }

        public String getLegal_name() {
            return legal_name;
        }

        public void setLegal_name(String legal_name) {
            this.legal_name = legal_name;
        }

        public Object getPlace() {
            return place;
        }

        public void setPlace(Object place) {
            this.place = place;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOwn_id() {
            return own_id;
        }

        public void setOwn_id(String own_id) {
            this.own_id = own_id;
        }

        public String getRes_phone() {
            return res_phone;
        }

        public void setRes_phone(String res_phone) {
            this.res_phone = res_phone;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLegal() {
            return legal;
        }

        public void setLegal(String legal) {
            this.legal = legal;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRes_name() {
            return res_name;
        }

        public void setRes_name(String res_name) {
            this.res_name = res_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getInside_code() {
            return inside_code;
        }

        public void setInside_code(Object inside_code) {
            this.inside_code = inside_code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getOwn_name() {
            return own_name;
        }

        public void setOwn_name(String own_name) {
            this.own_name = own_name;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getRes_id() {
            return res_id;
        }

        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }
    }

    public static class RescueEntity {

        private Object postcode;
        private String legal_name;
        private Object place;
        private String code;
        private String own_id;
        private String res_phone;
        private String short_name;
        private String fax;
        private String lng;
        private String legal;
        private String brief;
        private String create_date;
        private String name;
        private String res_name;
        private String type;
        private Object inside_code;
        private String id;
        private String level;
        private String qualification;
        private String mobile;
        private String lat;
        private String pid;
        private String own_name;
        private String order;
        private String res_id;
        private String address;
        private String update_date;

        public Object getPostcode() {
            return postcode;
        }

        public void setPostcode(Object postcode) {
            this.postcode = postcode;
        }

        public String getLegal_name() {
            return legal_name;
        }

        public void setLegal_name(String legal_name) {
            this.legal_name = legal_name;
        }

        public Object getPlace() {
            return place;
        }

        public void setPlace(Object place) {
            this.place = place;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOwn_id() {
            return own_id;
        }

        public void setOwn_id(String own_id) {
            this.own_id = own_id;
        }

        public String getRes_phone() {
            return res_phone;
        }

        public void setRes_phone(String res_phone) {
            this.res_phone = res_phone;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLegal() {
            return legal;
        }

        public void setLegal(String legal) {
            this.legal = legal;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRes_name() {
            return res_name;
        }

        public void setRes_name(String res_name) {
            this.res_name = res_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getInside_code() {
            return inside_code;
        }

        public void setInside_code(Object inside_code) {
            this.inside_code = inside_code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getOwn_name() {
            return own_name;
        }

        public void setOwn_name(String own_name) {
            this.own_name = own_name;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getRes_id() {
            return res_id;
        }

        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }
    }

    public static class InfoEntity {

        private String id;
        private String dengji_no;
        private String elevator_id;
        private String inspection_date;
        private String conclusion_name;
        private String province;
        private String manufact_num;
        private String area;
        private String inspection_cate_name;
        private String repaire_date;
        private String manufact_date;
        private String into_use_date;
        private String city;
        private String install_complete_date;
        private Object inside_code;
        private String repaire_cycle;
        private String maintain_cycle;
        private String remarks;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDengji_no() {
            return dengji_no;
        }

        public void setDengji_no(String dengji_no) {
            this.dengji_no = dengji_no;
        }

        public String getElevator_id() {
            return elevator_id;
        }

        public void setElevator_id(String elevator_id) {
            this.elevator_id = elevator_id;
        }

        public String getInspection_date() {
            return inspection_date;
        }

        public void setInspection_date(String inspection_date) {
            this.inspection_date = inspection_date;
        }

        public String getConclusion_name() {
            return conclusion_name;
        }

        public void setConclusion_name(String conclusion_name) {
            this.conclusion_name = conclusion_name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getManufact_num() {
            return manufact_num;
        }

        public void setManufact_num(String manufact_num) {
            this.manufact_num = manufact_num;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getInspection_cate_name() {
            return inspection_cate_name;
        }

        public void setInspection_cate_name(String inspection_cate_name) {
            this.inspection_cate_name = inspection_cate_name;
        }

        public String getRepaire_date() {
            return repaire_date;
        }

        public void setRepaire_date(String repaire_date) {
            this.repaire_date = repaire_date;
        }

        public String getManufact_date() {
            return manufact_date;
        }

        public void setManufact_date(String manufact_date) {
            this.manufact_date = manufact_date;
        }

        public String getInto_use_date() {
            return into_use_date;
        }

        public void setInto_use_date(String into_use_date) {
            this.into_use_date = into_use_date;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getInstall_complete_date() {
            return install_complete_date;
        }

        public void setInstall_complete_date(String install_complete_date) {
            this.install_complete_date = install_complete_date;
        }

        public Object getInside_code() {
            return inside_code;
        }

        public void setInside_code(Object inside_code) {
            this.inside_code = inside_code;
        }

        public String getRepaire_cycle() {
            return repaire_cycle;
        }

        public void setRepaire_cycle(String repaire_cycle) {
            this.repaire_cycle = repaire_cycle;
        }

        public String getMaintain_cycle() {
            return maintain_cycle;
        }

        public void setMaintain_cycle(String maintain_cycle) {
            this.maintain_cycle = maintain_cycle;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }

    public static class PropertyEntity {

        private Object postcode;
        private String legal_name;
        private Object place;
        private String code;
        private String own_id;
        private String res_phone;
        private String short_name;
        private String fax;
        private String lng;
        private String legal;
        private String brief;
        private String create_date;
        private String name;
        private String res_name;
        private String type;
        private Object inside_code;
        private String id;
        private String level;
        private String qualification;
        private String mobile;
        private String lat;
        private String pid;
        private String own_name;
        private String order;
        private String res_id;
        private String address;
        private String update_date;

        public Object getPostcode() {
            return postcode;
        }

        public void setPostcode(Object postcode) {
            this.postcode = postcode;
        }

        public String getLegal_name() {
            return legal_name;
        }

        public void setLegal_name(String legal_name) {
            this.legal_name = legal_name;
        }

        public Object getPlace() {
            return place;
        }

        public void setPlace(Object place) {
            this.place = place;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOwn_id() {
            return own_id;
        }

        public void setOwn_id(String own_id) {
            this.own_id = own_id;
        }

        public String getRes_phone() {
            return res_phone;
        }

        public void setRes_phone(String res_phone) {
            this.res_phone = res_phone;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLegal() {
            return legal;
        }

        public void setLegal(String legal) {
            this.legal = legal;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRes_name() {
            return res_name;
        }

        public void setRes_name(String res_name) {
            this.res_name = res_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getInside_code() {
            return inside_code;
        }

        public void setInside_code(Object inside_code) {
            this.inside_code = inside_code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getOwn_name() {
            return own_name;
        }

        public void setOwn_name(String own_name) {
            this.own_name = own_name;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getRes_id() {
            return res_id;
        }

        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }
    }

    public static class StatusEntity {

        private String failureinfo;
        private String elevator_id;
        private String id;
        private String updatetime;
        private String servicemodel;
        private String is_voice;
        private String haspeople;
        private String curfloor;
        private String direction;
        private String doorstatus;

        public String getFailureinfo() {
            return failureinfo;
        }

        public void setFailureinfo(String failureinfo) {
            this.failureinfo = failureinfo;
        }

        public String getElevator_id() {
            return elevator_id;
        }

        public void setElevator_id(String elevator_id) {
            this.elevator_id = elevator_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getServicemodel() {
            return servicemodel;
        }

        public void setServicemodel(String servicemodel) {
            this.servicemodel = servicemodel;
        }

        public String getIs_voice() {
            return is_voice;
        }

        public void setIs_voice(String is_voice) {
            this.is_voice = is_voice;
        }

        public String getHaspeople() {
            return haspeople;
        }

        public void setHaspeople(String haspeople) {
            this.haspeople = haspeople;
        }

        public String getCurfloor() {
            return curfloor;
        }

        public void setCurfloor(String curfloor) {
            this.curfloor = curfloor;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getDoorstatus() {
            return doorstatus;
        }

        public void setDoorstatus(String doorstatus) {
            this.doorstatus = doorstatus;
        }
    }

    public static class MaintainEntity {

        private Object postcode;
        private String legal_name;
        private Object place;
        private Object code;
        private String own_id;
        private String res_phone;
        private String short_name;
        private String fax;
        private String lng;
        private String legal;
        private String brief;
        private String create_date;
        private String name;
        private String res_name;
        private String type;
        private Object inside_code;
        private String id;
        private String level;
        private Object qualification;
        private String mobile;
        private String lat;
        private String pid;
        private String own_name;
        private String order;
        private String res_id;
        private String address;
        private String update_date;

        public Object getPostcode() {
            return postcode;
        }

        public void setPostcode(Object postcode) {
            this.postcode = postcode;
        }

        public String getLegal_name() {
            return legal_name;
        }

        public void setLegal_name(String legal_name) {
            this.legal_name = legal_name;
        }

        public Object getPlace() {
            return place;
        }

        public void setPlace(Object place) {
            this.place = place;
        }

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public String getOwn_id() {
            return own_id;
        }

        public void setOwn_id(String own_id) {
            this.own_id = own_id;
        }

        public String getRes_phone() {
            return res_phone;
        }

        public void setRes_phone(String res_phone) {
            this.res_phone = res_phone;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLegal() {
            return legal;
        }

        public void setLegal(String legal) {
            this.legal = legal;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRes_name() {
            return res_name;
        }

        public void setRes_name(String res_name) {
            this.res_name = res_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getInside_code() {
            return inside_code;
        }

        public void setInside_code(Object inside_code) {
            this.inside_code = inside_code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public Object getQualification() {
            return qualification;
        }

        public void setQualification(Object qualification) {
            this.qualification = qualification;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getOwn_name() {
            return own_name;
        }

        public void setOwn_name(String own_name) {
            this.own_name = own_name;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getRes_id() {
            return res_id;
        }

        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }
    }

    public static class ArgsEntity {

        private String id;
        private String door;
        private String rated_speed;
        private String overlay;
        private String elevator_id;
        private String sb_no;
        private String brand;
        private String controll_type;
        private String load_capacity;
        private String type_id;
        private String floor;
        private String model;
        private String name;
        private String station;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDoor() {
            return door;
        }

        public void setDoor(String door) {
            this.door = door;
        }

        public String getRated_speed() {
            return rated_speed;
        }

        public void setRated_speed(String rated_speed) {
            this.rated_speed = rated_speed;
        }

        public String getOverlay() {
            return overlay;
        }

        public void setOverlay(String overlay) {
            this.overlay = overlay;
        }

        public String getElevator_id() {
            return elevator_id;
        }

        public void setElevator_id(String elevator_id) {
            this.elevator_id = elevator_id;
        }

        public String getSb_no() {
            return sb_no;
        }

        public void setSb_no(String sb_no) {
            this.sb_no = sb_no;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getControll_type() {
            return controll_type;
        }

        public void setControll_type(String controll_type) {
            this.controll_type = controll_type;
        }

        public String getLoad_capacity() {
            return load_capacity;
        }

        public void setLoad_capacity(String load_capacity) {
            this.load_capacity = load_capacity;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }
    }

    public static class RegEntity {

        private String id;
        private String create_date;
        private String use_place;
        private String update_date;
        private String inner_code;
        private String elevator_id;
        private String licence_code;
        private String inspect_institution;
        private String area_id;
        private String code;
        private String address;
        private String institution;
        private String operation_user;
        private String lat;
        private String reg_user;
        private String lng;
        private String rescue_code;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getUse_place() {
            return use_place;
        }

        public void setUse_place(String use_place) {
            this.use_place = use_place;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getInner_code() {
            return inner_code;
        }

        public void setInner_code(String inner_code) {
            this.inner_code = inner_code;
        }

        public String getElevator_id() {
            return elevator_id;
        }

        public void setElevator_id(String elevator_id) {
            this.elevator_id = elevator_id;
        }

        public String getLicence_code() {
            return licence_code;
        }

        public void setLicence_code(String licence_code) {
            this.licence_code = licence_code;
        }

        public String getInspect_institution() {
            return inspect_institution;
        }

        public void setInspect_institution(String inspect_institution) {
            this.inspect_institution = inspect_institution;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public String getOperation_user() {
            return operation_user;
        }

        public void setOperation_user(String operation_user) {
            this.operation_user = operation_user;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getReg_user() {
            return reg_user;
        }

        public void setReg_user(String reg_user) {
            this.reg_user = reg_user;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getRescue_code() {
            return rescue_code;
        }

        public void setRescue_code(String rescue_code) {
            this.rescue_code = rescue_code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class UseEntity {

        private Object postcode;
        private String legal_name;
        private Object place;
        private String code;
        private String own_id;
        private String res_phone;
        private String short_name;
        private String fax;
        private String lng;
        private String legal;
        private String brief;
        private String create_date;
        private String name;
        private String res_name;
        private String type;
        private Object inside_code;
        private String id;
        private String level;
        private String qualification;
        private String mobile;
        private String lat;
        private String pid;
        private String own_name;
        private String order;
        private String res_id;
        private String address;
        private String update_date;

        public Object getPostcode() {
            return postcode;
        }

        public void setPostcode(Object postcode) {
            this.postcode = postcode;
        }

        public String getLegal_name() {
            return legal_name;
        }

        public void setLegal_name(String legal_name) {
            this.legal_name = legal_name;
        }

        public Object getPlace() {
            return place;
        }

        public void setPlace(Object place) {
            this.place = place;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOwn_id() {
            return own_id;
        }

        public void setOwn_id(String own_id) {
            this.own_id = own_id;
        }

        public String getRes_phone() {
            return res_phone;
        }

        public void setRes_phone(String res_phone) {
            this.res_phone = res_phone;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLegal() {
            return legal;
        }

        public void setLegal(String legal) {
            this.legal = legal;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRes_name() {
            return res_name;
        }

        public void setRes_name(String res_name) {
            this.res_name = res_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getInside_code() {
            return inside_code;
        }

        public void setInside_code(Object inside_code) {
            this.inside_code = inside_code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getOwn_name() {
            return own_name;
        }

        public void setOwn_name(String own_name) {
            this.own_name = own_name;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getRes_id() {
            return res_id;
        }

        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }
    }
}
