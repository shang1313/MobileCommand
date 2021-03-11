package com.slc.appdatabase.version;

import java.util.List;

/**
 * @author slc
 * @date 2020-03-30 11:19
 */
public class TableDdVersion<T> {
    private int dataDiversityVersion;
    private List<T> remove;
    private List<T> put;

    public int getDataDiversityVersion() {
        return dataDiversityVersion;
    }

    public void setDataDiversityVersion(int dataDiversityVersion) {
        this.dataDiversityVersion = dataDiversityVersion;
    }

    public List<T> getRemove() {
        return remove;
    }

    public void setRemove(List<T> remove) {
        this.remove = remove;
    }

    public List<T> getPut() {
        return put;
    }

    public void setPut(List<T> put) {
        this.put = put;
    }
}