package android.slc.appbase.data.entity;

import java.util.List;

/**
 * @author slc
 * @date 2020-12-25 22:55
 */
public class Dict extends BaseEntity {

    /**
     * 字典主键
     */
    private long dictId;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    private List<DictDetail> dictDetails;

    public long getDictId() {
        return dictId;
    }

    public void setDictId(long dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DictDetail> getDictDetails() {
        return dictDetails;
    }

    public void setDictDetails(List<DictDetail> dictDetails) {
        this.dictDetails = dictDetails;
    }
}
