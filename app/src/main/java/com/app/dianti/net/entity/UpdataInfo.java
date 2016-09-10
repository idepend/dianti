package com.app.dianti.net.entity;

/**
 * Created by Lenovo on 2016/8/31.
 */

public class UpdataInfo {

    /**
     * code : 200
     * msg : 查询成功
     * data : {"appName":"AndroidUpadateVersion","apkName":"AndroidUpadateVersion.apk","packageName":"org.android.version","slientInstall":"false","forceInstall":"true","updateInfo":"1、版本更新升级测试；2、修改请求测试数据；3、优化用户体验和程序的bug。","versionNumber":"2"}
     */

    private int code;
    private String msg;
    /**
     * appName : AndroidUpadateVersion
     * apkName : AndroidUpadateVersion.apk
     * packageName : org.android.version
     * slientInstall : false
     * forceInstall : true
     * updateInfo : 1、版本更新升级测试；2、修改请求测试数据；3、优化用户体验和程序的bug。
     * versionNumber : 2
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String appName;
        private String apkName;
        private String packageName;
        private String slientInstall;
        private String forceInstall;
        private String updateInfo;
        private String versionNumber;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getApkName() {
            return apkName;
        }

        public void setApkName(String apkName) {
            this.apkName = apkName;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getSlientInstall() {
            return slientInstall;
        }

        public void setSlientInstall(String slientInstall) {
            this.slientInstall = slientInstall;
        }

        public String getForceInstall() {
            return forceInstall;
        }

        public void setForceInstall(String forceInstall) {
            this.forceInstall = forceInstall;
        }

        public String getUpdateInfo() {
            return updateInfo;
        }

        public void setUpdateInfo(String updateInfo) {
            this.updateInfo = updateInfo;
        }

        public String getVersionNumber() {
            return versionNumber;
        }

        public void setVersionNumber(String versionNumber) {
            this.versionNumber = versionNumber;
        }
    }
}
