package com.slc.appdatabase.msg;

import com.slc.appdatabase.user.User;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;

@Entity
public class NewMsg implements Serializable {
    @Id
    private long _id;
    @Index
    private String msgId;
    @Index
    private String fromId;//发送者账户
    @Index
    private String toId;//接收者账户
    @Index
    private String msgHoldAccount;//msg拥有者账户
    private long createTime;//创建时间
    private long count;//新消息个数
    public ToOne<MsgBody> msgBody;
    public ToOne<User> user;
    @Transient
    private String createTimeFormat;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getMsgHoldAccount() {
        return msgHoldAccount;
    }

    public void setMsgHoldAccount(String msgHoldAccount) {
        this.msgHoldAccount = msgHoldAccount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

}
