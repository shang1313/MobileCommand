package com.slc.appdatabase.dept;

import java.io.Serializable;

/**
 * @author slc
 * @date 2021/1/14 14:25
 */
public class Dept implements Serializable {
    private long id;//Long类型id主键
    private String createTime;//创建时间
    private long createBy;//创建人id
    private String modifyTime;//更新时间
    private long modifyBy;//更新人id
    private String simplename;//部门简称
    private String fullname;// 部门全称

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

    public String getSimplename() {
        return simplename;
    }

    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
