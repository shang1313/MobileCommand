package android.slc.appbase.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckUpdateResp {

    /**
     * id : 3
     * name : 办公
     */

    @SerializedName("appCategory")
    private AppCategoryBean appCategory;
    /**
     * appCategory : {"id":"3","name":"办公"}
     * appChannelNo :
     * appModules : [{"appModularUri":"fco://fire.mobile.work?action=1","appName":"移动办公","icon":"09d55101-1c3d-42b6-9eeb-3a9836ce3dec.png","id":"5","idApp":"9","name":"公文阅办"},{"appModularUri":"fco://fire.mobile.work?action=4","appName":"移动办公","icon":"889bf646-66c6-4a82-9ce7-46b4bd79b1fb.png","id":"6","idApp":"9","name":"写邮件"},{"appModularUri":"fco://fire.mobile.work?action=2","appName":"移动办公","icon":"45b43319-f10f-4901-8ca0-b3fc07f604d2.png","id":"7","idApp":"9","name":"公文检索"},{"appModularUri":"fco://fire.mobile.work?action=3","appName":"移动办公","icon":"1aad97a4-ba68-4587-957c-42523fc9c665.png","id":"8","idApp":"9","name":"公文审批"},{"appModularUri":"fco://fire.mobile.work?action=5","appName":"移动办公","icon":"3436112e-46f2-4b77-ab16-5eab65dd916d.png","id":"9","idApp":"9","name":"收邮箱"},{"appModularUri":"fco://fire.mobile.work?action=7","appName":"移动办公","icon":"5b2566de-def7-4ec8-bb29-8672457b980b.png","id":"10","idApp":"9","name":"法律法规"},{"appModularUri":"fco://fire.mobile.work?action=8","appName":"移动办公","icon":"4b05232b-f5ec-4d24-b860-ac0543daffd6.png","id":"11","idApp":"9","name":"值班查询"},{"appModularUri":"fco://fire.mobile.work?action=6","appName":"移动办公","icon":"b97a0ffb-4627-4d04-b792-3e1f8816ace7.png","id":"12","idApp":"9","name":"发件箱"}]
     * appName : 移动办公
     * appRealName : 005814f5-d744-4ea5-9fc5-8868287fe403.apk
     * appSize : 8476362
     * appVersion : 2
     * appVersionName : 1.0.1
     * createBy : 1
     * createTime : 1611294716000
     * icon : a4d1598e-0bf4-4cf6-b4c4-310d852ed653.png
     * id : 9
     * idCategory : 3
     * isForcedUpdate : 0
     * modifyBy : 1
     * modifyTime : 1613809171000
     * packageName : ap.mobile.officing
     * status : 1
     * updateCount : 161
     * versionDesc : 1、移动端公文阅办
     * 2、移动端邮件收发
     * 3、移动端值班查询
     */

    @SerializedName("appChannelNo")
    private String appChannelNo;
    @SerializedName("appName")
    private String appName;
    @SerializedName("appRealName")
    private String appRealName;
    @SerializedName("appSize")
    private String appSize;
    @SerializedName("appVersion")
    private int appVersion;
    @SerializedName("appVersionName")
    private String appVersionName;
    @SerializedName("createBy")
    private String createBy;
    @SerializedName("createTime")
    private long createTime;
    @SerializedName("icon")
    private String icon;
    @SerializedName("id")
    private String id;
    @SerializedName("idCategory")
    private String idCategory;
    @SerializedName("isForcedUpdate")
    private int isForcedUpdate;
    @SerializedName("modifyBy")
    private String modifyBy;
    @SerializedName("modifyTime")
    private long modifyTime;
    @SerializedName("packageName")
    private String packageName;
    @SerializedName("status")
    private int status;
    @SerializedName("updateCount")
    private String updateCount;
    @SerializedName("versionDesc")
    private String versionDesc;
    @SerializedName("updateUrl")
    private String updateUrl;
    /**
     * appModularUri : fco://fire.mobile.work?action=1
     * appName : 移动办公
     * icon : 09d55101-1c3d-42b6-9eeb-3a9836ce3dec.png
     * id : 5
     * idApp : 9
     * name : 公文阅办
     */

    @SerializedName("appModules")
    private List<AppModulesBean> appModules;

    public AppCategoryBean getAppCategory() {
        return appCategory;
    }

    public void setAppCategory(AppCategoryBean appCategory) {
        this.appCategory = appCategory;
    }

    public String getAppChannelNo() {
        return appChannelNo;
    }

    public void setAppChannelNo(String appChannelNo) {
        this.appChannelNo = appChannelNo;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppRealName() {
        return appRealName;
    }

    public void setAppRealName(String appRealName) {
        this.appRealName = appRealName;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public int getIsForcedUpdate() {
        return isForcedUpdate;
    }

    public void setIsForcedUpdate(int isForcedUpdate) {
        this.isForcedUpdate = isForcedUpdate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(String updateCount) {
        this.updateCount = updateCount;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public List<AppModulesBean> getAppModules() {
        return appModules;
    }

    public void setAppModules(List<AppModulesBean> appModules) {
        this.appModules = appModules;
    }

    public static class AppCategoryBean {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "AppCategoryBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class AppModulesBean {
        @SerializedName("appModularUri")
        private String appModularUri;
        @SerializedName("appName")
        private String appName;
        @SerializedName("icon")
        private String icon;
        @SerializedName("id")
        private String id;
        @SerializedName("idApp")
        private String idApp;
        @SerializedName("name")
        private String name;

        public String getAppModularUri() {
            return appModularUri;
        }

        public void setAppModularUri(String appModularUri) {
            this.appModularUri = appModularUri;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdApp() {
            return idApp;
        }

        public void setIdApp(String idApp) {
            this.idApp = idApp;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "AppModulesBean{" +
                    "appModularUri='" + appModularUri + '\'' +
                    ", appName='" + appName + '\'' +
                    ", icon='" + icon + '\'' +
                    ", id='" + id + '\'' +
                    ", idApp='" + idApp + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CheckUpdateResp{" +
                "appCategory=" + appCategory +
                ", appChannelNo='" + appChannelNo + '\'' +
                ", appName='" + appName + '\'' +
                ", appRealName='" + appRealName + '\'' +
                ", appSize='" + appSize + '\'' +
                ", appVersion=" + appVersion +
                ", appVersionName='" + appVersionName + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", idCategory='" + idCategory + '\'' +
                ", isForcedUpdate=" + isForcedUpdate +
                ", modifyBy='" + modifyBy + '\'' +
                ", modifyTime=" + modifyTime +
                ", packageName='" + packageName + '\'' +
                ", status=" + status +
                ", updateCount='" + updateCount + '\'' +
                ", versionDesc='" + versionDesc + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", appModules=" + appModules +
                '}';
    }
}
