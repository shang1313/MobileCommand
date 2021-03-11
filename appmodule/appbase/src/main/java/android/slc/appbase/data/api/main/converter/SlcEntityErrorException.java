package android.slc.appbase.data.api.main.converter;

import java.io.IOException;

public class SlcEntityErrorException extends IOException {
    private int mErrorCode;

    public SlcEntityErrorException(int errorCode, String message) {
        super(message);
        this.mErrorCode = errorCode;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        this.mErrorCode = errorCode;
    }
}
