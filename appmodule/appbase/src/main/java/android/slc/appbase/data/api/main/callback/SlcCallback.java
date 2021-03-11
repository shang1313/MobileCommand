package android.slc.appbase.data.api.main.callback;

import android.slc.appbase.R;
import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.appbase.data.api.main.converter.SlcEntityErrorException;
import android.slc.appbase.utils.LoadingUtils;
import android.slc.or.SlcCallbackConfig;
import android.slc.slcdialog.SlcPopup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract class SlcCallback<T> implements Callback<SlcEntity<T>> {
    //后台定义
    public static final int SUCCEED = 200;//成功
    public static final int NORMAL_ERROR = 500;//后台提示
    //客户端定义
    public static final int RESULT_UPLOAD_FAILURE = 30003;//上传文件失败
    public static final String RESULT_UPLOAD_FAILURE_MSG = StringUtils.getString(R.string.label_upload_file_failed);//返回结果list为空提示
    protected SlcCallbackConfig mSlcCallbackConfig = SlcCallbackConfig.defSlcCallbackConfig;

    public SlcCallback() {
        onStart();
    }

    public SlcCallback(SlcCallbackConfig slcCallbackConfig) {
        this.mSlcCallbackConfig = slcCallbackConfig;
        this.onStart();
    }

    protected void onStart() {
        if (mSlcCallbackConfig.isIsShowDialog()) {
            new SlcPopup.LoadingBuilder(ActivityUtils.getTopActivity()).setMessage(mSlcCallbackConfig.getDialogMsg()).setKey(String.valueOf(hashCode())).create().show();
        }
    }

    @Override
    public void onResponse(Call<SlcEntity<T>> call, Response<SlcEntity<T>> response) {
        LoadingUtils.dismissLoadingDialog();
        onSucceed(response.body().getData());
        onFinish();
    }

    @Override
    public void onFailure(Call<SlcEntity<T>> call, Throwable t) {
        t.printStackTrace();
        LoadingUtils.dismissLoadingDialog();
        int errorCode = 0;
        String errorMessage = t.getMessage();
        if (t instanceof SlcEntityErrorException) {
            errorCode = ((SlcEntityErrorException) t).getErrorCode();
        } else if (t instanceof ConnectException) {
            errorCode = 500;
            errorMessage = StringUtils.getString(R.string.net_date_connection_failure);
        }
        showToast(errorCode, errorMessage);
        onFailed(errorCode, errorMessage);
        onFinish();
    }

    /**
     * 显示toast
     *
     * @param code
     * @param msg
     */
    private void showToast(int code, String msg) {
        if (mSlcCallbackConfig.isIsShowToast()) {
            if (code == NORMAL_ERROR) {
                ToastUtils.showShort(msg);
            } else {
                ToastUtils.showShort(mSlcCallbackConfig.getToastRes());
            }
        }
    }

    protected abstract void onSucceed(T data);

    protected abstract void onFailed(int errorCode, String errorMessage);

    protected void onFinish() {
        if (mSlcCallbackConfig.isIsShowDialog()) {
            SlcPopup.dismissByKey(String.valueOf(hashCode()));
        }
    }

}
