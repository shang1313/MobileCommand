package android.slc.appbase.data.api.main.callback;


import android.slc.appbase.R;
import android.slc.appbase.data.api.ApiConfig;
import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.appbase.data.api.main.converter.SlcEntityErrorException;
import android.slc.appbase.utils.LoadingUtils;
import android.slc.or.SlcCallbackConfig;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.net.ConnectException;

import io.reactivex.observers.DisposableObserver;

public abstract class SlcDisposableObserver<T> extends DisposableObserver<SlcEntity<T>> {
    protected SlcCallbackConfig mSlcCallbackConfig = SlcCallbackConfig.defSlcCallbackConfig;

    public SlcDisposableObserver() {

    }

    public SlcDisposableObserver(SlcCallbackConfig slcCallbackConfig) {
        this.mSlcCallbackConfig = slcCallbackConfig;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.mSlcCallbackConfig.isIsShowDialog()) {
            LoadingUtils.showLoadingDialog(this.mSlcCallbackConfig.getDialogMsg());
        }
    }

    @Override
    public void onNext(SlcEntity<T> tSlcEntity) {
        onFinish();
        onSucceed(tSlcEntity.getData());
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFinish();
        int errorCode = 0;
        String errorMessage = e.getMessage();
        if (e instanceof SlcEntityErrorException) {
            errorCode = ((SlcEntityErrorException) e).getErrorCode();
        } else if (e instanceof ConnectException) {
            errorCode = 500;
            errorMessage = StringUtils.getString(R.string.net_date_connection_failure);
        }
        showToast(errorCode, errorMessage);
        onFailed(errorCode, errorMessage);
    }

    @Override
    public void onComplete() {

    }

    protected void onFinish() {
        if (mSlcCallbackConfig.isIsShowDialog() && mSlcCallbackConfig.isIsAutoDismissDialog()) {
            dismissDialog();
        }
    }

    protected void dismissDialog() {
        LoadingUtils.dismissLoadingDialog();
        //SlcPopup.dismissByKey(String.valueOf(hashCode()));
    }

    /**
     * 显示toast
     *
     * @param code
     * @param msg
     */
    private void showToast(int code, String msg) {
        if (mSlcCallbackConfig.isIsShowToast()) {
            if (code == ApiConfig.NORMAL_ERROR || code == ApiConfig.RESULT_UPLOAD_FAILURE) {
               ToastUtils.showShort(msg);
            } else {
                ToastUtils.showShort(mSlcCallbackConfig.getToastRes());
            }
        }
    }

    protected abstract void onSucceed(T data);

    protected abstract void onFailed(int errorCode, String errorMessage);
}
