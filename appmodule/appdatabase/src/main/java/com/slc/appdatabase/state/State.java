package com.slc.appdatabase.state;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author slc
 * @date 2020-09-03 16:58
 */
@Entity
public class State {
    @Id
    private long id;
    private String hostName;
    private String hostId;
    private int state;
    private long saveDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(long saveDate) {
        this.saveDate = saveDate;
    }
}
