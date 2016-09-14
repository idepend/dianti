package com.app.dianti.common;

import com.app.dianti.vo.UserInfo;

public class AppContext {

    /**
     * 增阔平台地址1
     */
    public static String PLATFORM_ADDRESS_ONE="http://121.40.89.242:80";

    /**
     * 增阔平台地址2
     */
    public static String PLATFORM_ADDRESS_TWO="http://121.40.89.242:10000";

    /**
     * 类型--档案历史(状态为已完成的数据)
     */
    public static final int history_data_type = 1;
    /**
     * 类型--个人历史(状态为已完成的数据)
     */
    public static final int history_my_data_type = 3;
    /**
     * 类型--实时未结单数据
     */
    public static final int real_data_type = 2;

    /**
     * 版本
     */
    public static int VERSION = 1;
    /**
     * 当前登录用户信息
     */
    public static UserInfo userInfo = new UserInfo();
    /**
     * 服务器地址
     */
    public static String SERVER_URL = "http://192.168.1.6";
    public static String SERVER_URL_2 = "http://192.168.1.6";
//    public static String SERVER_URL = "http://121.40.89.242:10001";
//    public static String SERVER_URL_2 = "http://218.75.75.34:80";

    /**
     * 登录接口
     */
    public static String API_LOGIN_URL;

    /**
     * 消息通知查询接口
     */
    public static String API_MSG_NOTIFI;

    /**
     * 各项数据总数查询接口
     */
    public static String API_ALL_COUNT;

    /**
     * 维保数据接口
     */
    public static String API_LOGIN_MAINTENANCE;
    /**
     * 维保项目查询接口
     */
    public static String API_MAINTENANCE_ITEM;

    /**
     * 巡查项目查询接口
     */
    public static String API_ELEVATOR_DETAIL;

    /**
     * 维保签到接口
     */
    public static String API_MAINTENANCE_SIGN;
    /**
     * 维保人员提交维保接口
     */
    public static String API_MAINTENANCE_COMMIT;

    public static String API_MAINTENANCE_ADD;

    /**
     * 年检记录接口
     */
    public static String API_LOGIN_ANNUAL;
    /**
     * 年检记录接口
     */
    public static String API_LOGIN_DANGAN;

    /**
     * 年检详情
     */
    public static String API_ANNUAL_DETAIL;

    /**
     * 应急救援记录接口
     */
    public static String API_RESKJU;
    /**
     * 获取电梯详情--基本信息接口
     */
    public static String API_GET_ELE_BASE_INFO;
    /**
     * 电梯实时监测--信息接口
     */
    public static String API_GET_ELE_REALTIME_INFO;

    /**
     * 根据坐标获取周围的电梯--信息接口
     */
    public static String API_GET_ELE_NEAR;

    /**
     * 年检-继续整改--信息接口
     */
    public static String API_ELE_YEAR_REFORM;

    /**
     * 上传年检报告--信息接口
     */
    public static String API_ELE_YEAR_REPORT;

    /**
     * 完成应急救援---信息接口
     */
    public static String API_ELE_RESKJU_SAVE;

    /**
     * 电梯巡查列表---信息接口
     */
    public static String API_ELE_INSPECTION;

    /**
     * 电梯巡查,添加或提交巡查单---信息接口
     */
    public static String API_ELE_INSPECTION_SAVE;

    /**
     * 电梯巡查--电梯巡查--更新巡查单子---信息接口
     * 6-3-2. 电梯巡查--物业确认完成巡查单子
     * token
     * id //巡查单子 id
     * type //0 为完成，1转为故障(app 界面的保修)
     * reason // 如果为 1 的话为必填 转为故障的原因
     */
    public static String API_ELE_INSPECTION_UPDATE_STATUS;

    /**
     * 电梯巡查-更新巡查单子---信息接口
     */
    public static String API_ELE_INSPECTION_WAIT_CONFIRM;

    /**
     * 维保物业确认处理接口
     */
    public static String API_MAINTENANCE_CONFIRM;


    /**
     * 巡查记录查看接口
     */
    public static String API_ELE_INSPECTION_VIEW;

    /**
     * 故障救援--完成故障单子，待物业确认
     */
    public static String API_ELE_RESCUE_WAIT_CONFIRM;

    /**
     * 故障救援--物业完成故障单子(物业确认)
     */
    public static String API_ELE_RESCUE_CONFIRM;

    /**
     * 维保单子详情查看接口
     */
    public static String API_MAINTENANCE_VIEW;

    /**
     * 救援单子详情查看接口
     */
    public static String API_RESCUE_VIEW;

    /**
     * 通知公告接口
     */
    public static String API_NOTICE_LIST;

    /**
     * 公告详情
     */
    public static String API_NOTICE_DETAIL;

    public static void apiUrlBuild() {
        /**
         * 登录接口
         */
        API_LOGIN_URL = SERVER_URL + "/app/user/auth.htm";

        API_MSG_NOTIFI = SERVER_URL + "/app/notification/get";

        /**
         * 各项数据总数查询接口
         */
        API_ALL_COUNT = SERVER_URL + "/app/record/depId";

        /**
         * 维保数据接口
         */
        API_LOGIN_MAINTENANCE = SERVER_URL + "/app/maintain/list.htm";
        /**
         * 维保项目查询接口
         */
        API_MAINTENANCE_ITEM = SERVER_URL + "/app/maintain/list/detail.htm";

        API_ELEVATOR_DETAIL = SERVER_URL + "/app/patrol/detail.htm";

        /**
         * 维保签到接口
         */
        API_MAINTENANCE_SIGN = SERVER_URL + "/app/maintain/sign.htm";

        /**
         * 维保人员提交维保接口
         */
        API_MAINTENANCE_COMMIT = SERVER_URL + "/app/maintain/wait_confirm.htm";

        /**
         * 维保人员提交维保接口
         */
        API_MAINTENANCE_ADD = SERVER_URL + "/app/maintain/add.htm";

        /**
         * 年检记录接口
         */
        API_LOGIN_ANNUAL = SERVER_URL_2 +"/Home/MEle/Annuallist";
        /**
         * 年检记录接口
         */
        API_LOGIN_DANGAN = SERVER_URL_2 +"/Home/MEle/eleFile";

        API_ANNUAL_DETAIL= SERVER_URL_2 +"/Home/MEle/annualInfo";

        /**
         * 应急救援记录接口
         */
        API_RESKJU = SERVER_URL_2 + "/Home/MEle/rescueList";
        /**
         * 获取电梯详情--基本信息接口
         */
        API_GET_ELE_BASE_INFO = SERVER_URL_2 + "/Home/MEle/getEleInfo";
        /**
         * 电梯实时监测--信息接口
         */
        API_GET_ELE_REALTIME_INFO = SERVER_URL + "/app/elevator/runtime.htm";

        /**
         * 根据坐标获取周围的电梯--信息接口
         */
        API_GET_ELE_NEAR = SERVER_URL_2 + "/Home/MEle/getelebylocation";

        /**
         * 通知公告列表
         */
        API_NOTICE_LIST = SERVER_URL_2 + "/Home/MNotice/notice_list";

        /**
         * 公告详情
         */
        API_NOTICE_DETAIL=SERVER_URL_2 + "/Home/MNotice/getnotice";

        /**
         * 年检-继续整改--信息接口
         */
        API_ELE_YEAR_REFORM = SERVER_URL + "/app/annual/correct.htm";

        /**
         * 上传年检报告--信息接口
         */
        API_ELE_YEAR_REPORT = SERVER_URL + "/app/annual/done.htm";

        /**
         * 完成应急救援---信息接口
         */
        API_ELE_RESKJU_SAVE = SERVER_URL + "/app/rescue/inorup.htm";

        /**
         * 电梯巡查列表---信息接口
         */
        API_ELE_INSPECTION = SERVER_URL_2 + "/Home/MEle/patrollist";

        /**
         * 电梯巡查,添加或提交巡查单---信息接口
         */
        API_ELE_INSPECTION_SAVE = SERVER_URL + "/app/patrol/add.htm";

        /**
         * 电梯巡查--电梯巡查--更新巡查单子---信息接口
         * 6-3-2. 电梯巡查--物业确认完成巡查单子
         * token
         * id //巡查单子 id
         * type //0 为完成，1转为故障(app 界面的保修)
         * reason // 如果为 1 的话为必填 转为故障的原因
         */
        API_ELE_INSPECTION_UPDATE_STATUS = SERVER_URL + "/app/patrol/done.htm";

        /**
         * 电梯巡查-更新巡查单子---信息接口
         */
        API_ELE_INSPECTION_WAIT_CONFIRM = SERVER_URL + "/app/patrol/wait_confirm.htm";

        /**
         * 维保物业确认处理接口
         */
        API_MAINTENANCE_CONFIRM = SERVER_URL + "/app/maintain/done.htm";

        /**
         * 巡查记录查看接口
         */
        API_ELE_INSPECTION_VIEW = SERVER_URL_2 + "/Home/MEle/getPatrolById";

        /**
         * 故障救援--完成故障单子，待物业确认
         */
        API_ELE_RESCUE_WAIT_CONFIRM = SERVER_URL + "/app/rescue/wait_confirm.htm";

        /**
         * 故障救援--物业完成故障单子(物业确认)
         */
        API_ELE_RESCUE_CONFIRM = SERVER_URL + "/app/rescue/done.htm";

        API_MAINTENANCE_VIEW = SERVER_URL_2 + "/Home/Maintain/mGetInfoById";

        API_RESCUE_VIEW = SERVER_URL_2 + "/Home/FaultAlarm/mGetInfoById";

    }



}
