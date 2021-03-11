package com.slc.appdatabase.msg;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.UUID;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToOne;

/**
 * 聊天消息体
 */
@Entity
public class MsgBody implements Serializable {
    @Id
    private long id;//用于数据库存储
    @Index
    private long service_id;//服务器给的消息id
    @Index
    private String msgId;//消息id
    private int action;//消息类型
    private String title;//消息标题
    private String content;//消息内容
    private long timestamp;//创建时间
    private String sender;//发送者账户
    private String receiver;//接收者账户
    private String msgHoldAccount;//msg拥有者账户
    private int state;
    //private boolean isSynchronous;//是否同步（当该消息为客户端发送出去时，此字段为是否发送成功的意思）
    private boolean hasRed;//是否已读（针对自己）
    private boolean isDelete;//是否被删除
    public ToOne<MsgAttach> msgAttachToOne;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getService_id() {
        return service_id;
    }

    public void setService_id(long service_id) {
        this.service_id = service_id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsgHoldAccount() {
        return msgHoldAccount;
    }

    public void setMsgHoldAccount(String msgHoldAccount) {
        this.msgHoldAccount = msgHoldAccount;
    }

    public boolean isFinish() {
        return this.state == MsgBodyState.FINISH;
    }

    public boolean isWaiting() {
        return this.state == MsgBodyState.WAITING;
    }

    public boolean isError() {
        return this.state == MsgBodyState.ERROR;
    }

    public boolean isLoading() {
        return this.state == MsgBodyState.LOADING;
    }

    public void setState(@MsgBodyState.State int state) {
        this.state = state;
    }

    @MsgBodyState.State
    public int getState() {
        return state;
    }
    /*public boolean isSynchronous() {
        return isSynchronous;
    }

    public void setSynchronous(boolean synchronous) {
        isSynchronous = synchronous;
    }*/

    public boolean isHasRed() {
        return hasRed;
    }

    public void setHasRed(boolean hasRed) {
        this.hasRed = hasRed;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static Builder newTextBuilder() {
        return new Builder().setAction(MsgType.TYPE_TEXT);
    }

    public static Builder newImgBuilder() {
        return new Builder().setAction(MsgType.TYPE_IMG);
    }

    public static Builder newAnnouncementBuilder() {
        return new Builder().setAction(MsgType.TYPE_SYS_MSG);
    }

    public static class Builder {
        private MsgBody msgBody;

        public Builder() {
            msgBody = new MsgBody();
        }

        public Builder(MsgBody msgBody) {
            this.msgBody = new MsgBody();
            this.msgBody.setMsgId(msgBody.msgId);
            this.msgBody.setContent(msgBody.content);
            this.msgBody.setTimestamp(msgBody.timestamp);
            this.msgBody.setDelete(msgBody.isDelete);
            this.msgBody.setHasRed(msgBody.hasRed);
            this.msgBody.setMsgHoldAccount(msgBody.msgHoldAccount);
            this.msgBody.setAction(msgBody.action);
            this.msgBody.setReceiver(msgBody.receiver);
            this.msgBody.setSender(msgBody.sender);
            //this.msgBody.setSynchronous(msgBody.isSynchronous);
        }

        public Builder setMsgId(String msgId) {
            msgBody.setMsgId(msgId);
            return this;
        }

        public Builder setAction(int action) {
            msgBody.setAction(action);
            return this;
        }

        public Builder setTitle(String title) {
            msgBody.setTitle(title);
            return this;
        }
        public Builder setContent(String content) {
            msgBody.setContent(content);
            return this;
        }

        public Builder setTimestamp(long timestamp) {
            msgBody.setTimestamp(timestamp);
            return this;
        }

        public Builder setSender(String sender) {
            msgBody.setSender(sender);
            return this;
        }

        public Builder setReceiver(String receiver) {
            msgBody.setReceiver(receiver);
            return this;
        }

        public Builder setMsgHoldAccount(String msgHoldAccount) {
            msgBody.setMsgHoldAccount(msgHoldAccount);
            return this;
        }

        public Builder setHasRed(boolean hasRed) {
            msgBody.setHasRed(hasRed);
            return this;
        }

        public Builder setDelete(boolean delete) {
            msgBody.setDelete(delete);
            return this;
        }

        public Builder setState(@MsgBodyState.State int state) {
            msgBody.setState(state);
            return this;
        }

        public Builder setMsgAttach(MsgAttach msgAttach) {
            msgBody.msgAttachToOne.setTarget(msgAttach);
            return this;
        }

        public MsgBody build() {
            if (TextUtils.isEmpty(msgBody.getMsgId())) {
                msgBody.setMsgId(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            return msgBody;
        }
    }
}
