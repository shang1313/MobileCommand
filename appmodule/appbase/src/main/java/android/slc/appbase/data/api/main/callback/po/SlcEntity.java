package android.slc.appbase.data.api.main.callback.po;

public class SlcEntity<T> {
    private String retData;
    private T data;
    private String retCode;
    private String retMsg;
    private String salt;

    public String getRetData() {
        return retData;
    }

    public void setRetData(String retData) {
        this.retData = retData;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        try {
            return Integer.parseInt(retCode);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void setCode(int code) {
        this.retCode = String.valueOf(code);
    }

    public String getMsg() {
        return retMsg;
    }

    public void setMsg(String msg) {
        this.retMsg = msg;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isSuccess() {
        return getCode()==0;
    }
}
