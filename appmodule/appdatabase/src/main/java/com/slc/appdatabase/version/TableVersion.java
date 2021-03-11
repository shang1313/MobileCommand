package com.slc.appdatabase.version;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

/**
 * @author slc
 * @date 2020-03-30 12:10
 */
@Entity
public class TableVersion {
    @Id
    private long id;
    @Index
    private String tableName;
    private int version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
