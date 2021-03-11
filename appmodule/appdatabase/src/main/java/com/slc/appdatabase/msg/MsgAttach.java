package com.slc.appdatabase.msg;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

/**
 * @author slc
 * @date 2019/10/29 14:53
 */
@Entity
public class MsgAttach implements Serializable {
    @Id
    private long _id;
    /**
     * @ MsgBodyState 具体状态见此处
     */
    private int progress;
    @Index
    private String localPath;
    @Index
    private String networkPath;
    private String errorMsg;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getNetworkPath() {
        return networkPath;
    }

    public void setNetworkPath(String networkPath) {
        this.networkPath = networkPath;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private MsgAttach msgAttach;

        public Builder() {
            msgAttach = new MsgAttach();
        }

        public Builder setProgress(int progress) {
            msgAttach.setProgress(progress);
            return this;
        }

        public Builder setLocalPath(String localPath) {
            msgAttach.setLocalPath(localPath);
            return this;
        }

        public Builder setNetworkPath(String networkPath) {
            msgAttach.setNetworkPath(networkPath);
            return this;
        }

        public Builder setErrorMsg(String errorMsg) {
            msgAttach.setErrorMsg(errorMsg);
            return this;
        }

        public MsgAttach build() {
            return msgAttach;
        }
    }
}
