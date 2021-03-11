package com.slc.appdatabase.user;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

/**
 * 用户表
 */
@Entity
public class User implements Serializable {
    @Id(assignable = true)
    private long id;//Long类型id主键
    private String createTime;//创建时间
    private long createBy;//创建人id
    private String modifyTime;//更新时间
    private long modifyBy;//更新人id
    private String deviceId;//设备id
    private String deviceBrand;//设备品牌（1-32）
    private String deviceModel;//设备型号（1-32）
    private String phoneNo;//	手机号码（11）
    private String userName;//用户姓名（11）
    private long idDept;//部门id
    private String deptSimpleName;
    private String deptName;
    private String iMei;//设备iMei号（16）
    private int batteryStatus;//电源状态1:放电,0:充电
    private String diskSize;//存储容量（11）
    private String memorySize;//内存容量（11）
    private String cpu;//pu
    private String bluetoothMac;//蓝牙mac地址（64）
    private String wifiMac;// mac地址
    private int cameraStatus;//	摄像头状态：1，0
    private String workLong;//开机时长
    private String iMsi;//imsi号
    private int status;//	状态：0：激活,1：未激活,2：锁定,3：擦除,4：注销,5：恢复出厂设置
    private int systemEm;//系统类型0:android,1:ios,2:macOs,3:windows,4:linux,5:unix
    private int sourceEm;//	设备来源:1:个人,0:总队
    private int _userType;
    private String avatar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(long modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getIdDept() {
        return idDept;
    }

    public String getDeptSimpleName() {
        return deptSimpleName;
    }

    public void setDeptSimpleName(String deptSimpleName) {
        this.deptSimpleName = deptSimpleName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setIdDept(long idDept) {
        this.idDept = idDept;
    }

    public String getIMei() {
        return iMei;
    }

    public String getiMei() {
        return iMei;
    }

    public void setIMei(String iMei) {
        this.iMei = iMei;
    }

    public void setiMei(String iMei) {
        this.iMei = iMei;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(int batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public String getWifiMac() {
        return wifiMac;
    }

    public void setWifiMac(String wifiMac) {
        this.wifiMac = wifiMac;
    }

    public int getCameraStatus() {
        return cameraStatus;
    }

    public void setCameraStatus(int cameraStatus) {
        this.cameraStatus = cameraStatus;
    }

    public String getWorkLong() {
        return workLong;
    }

    public void setWorkLong(String workLong) {
        this.workLong = workLong;
    }

    public String getIMsi() {
        return iMsi;
    }

    public String getiMsi() {
        return iMsi;
    }

    public void setIMsi(String iMsi) {
        this.iMsi = iMsi;
    }

    public void setiMsi(String iMsi) {
        this.iMsi = iMsi;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSystemEm() {
        return systemEm;
    }

    public void setSystemEm(int systemEm) {
        this.systemEm = systemEm;
    }

    public int getSourceEm() {
        return sourceEm;
    }

    public void setSourceEm(int sourceEm) {
        this.sourceEm = sourceEm;
    }

    public void set_userType(int _userType) {
        this._userType = _userType;
    }

    public int get_userType() {
        return _userType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static User newSysUser(long id) {
        User user = new User();
        user.setId(id);
        user.set_userType(-1);
        return user;
    }

    public boolean isSysUser() {
        return get_userType() == -1;
    }
}