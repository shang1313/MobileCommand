package android.slc.appbase.data.entity.wb;

import java.util.List;

/**
 * @author slc
 * @date 2020-08-19 12:24
 */
public class WbGroup {
    private String groupCode;
    private String groupName;
    private int color;
    private List<WbItem> wbItemList;
    private int span;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<WbItem> getWbItemList() {
        return wbItemList;
    }

    public void setWbItemList(List<WbItem> wbItemList) {
        this.wbItemList = wbItemList;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }
}
