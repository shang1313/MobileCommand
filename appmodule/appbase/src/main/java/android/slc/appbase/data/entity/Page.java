package android.slc.appbase.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author slc
 * @date 2020-12-25 23:00
 */
public class Page<T> implements Serializable {
    private List<T> dataList;
    private int totalNum;
    private int pageNum;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
