package android.slc.appbase.data.api.main.callback;

import android.slc.appbase.R;
import android.slc.appbase.data.api.ApiConfig;
import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.appbase.data.api.main.converter.SlcEntityErrorException;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.appbase.utils.LoadingUtils;
import android.slc.or.SlcCallbackConfig;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class SlcObserver<T> implements Observer<SlcEntity<T>> {
    protected SlcCallbackConfig mSlcCallbackConfig = SlcCallbackConfig.defSlcCallbackConfig;

    public SlcObserver() {

    }

    public SlcObserver(SlcCallbackConfig slcCallbackConfig) {
        this.mSlcCallbackConfig = slcCallbackConfig;
    }

    @Override
    public final void onSubscribe(@NotNull Disposable d) {
        onStart();
    }

    protected void onStart() {
        if (this.mSlcCallbackConfig.isIsShowDialog()) {
            LoadingUtils.showLoadingDialog(this.mSlcCallbackConfig.getDialogMsg());
        }
    }

    @Override
    public void onNext(@NonNull SlcEntity<T> tSlcEntity) {
        onFinish();
        onSucceed(tSlcEntity.getData());
    }


    @Override
    public void onError(@NotNull Throwable e) {
        SlcEntity<T> slcEntity = getErrorMsg(e);
        int errorCode = slcEntity.getCode();
        if (errorCode == ApiConfig.SUCCEED) {
            onNext(slcEntity);
            return;
        }
        String errorMessage = slcEntity.getMsg();
        onFinish();
        showToast(errorCode, errorMessage);
        onFailed(errorCode, errorMessage);
    }

    protected SlcEntity<T> getErrorMsg(Throwable e) {
        SlcEntity<T> slcEntity = null;
        if (e instanceof NullPointerException) {
            NullPointerException nullPointerException = (NullPointerException) e;
            if (StringUtils.equals(nullPointerException.getMessage(), ConstantsBase.Value.VALUE_RESPONSE_BODY_IS_NULL)) {
                slcEntity = new SlcEntity<>();
                slcEntity.setCode(200);
            }
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            Response response = httpException.response();
            if (response != null) {
                ResponseBody errorBody = response.errorBody();
                if (errorBody != null) {
                    slcEntity = GsonUtils.fromJson(errorBody.charStream(), SlcEntity.class);
                }
            }
        } else if (e instanceof SlcEntityErrorException) {
            SlcEntityErrorException slcEntityErrorException = (SlcEntityErrorException) e;
            slcEntity = new SlcEntity<>();
            slcEntity.setCode(slcEntityErrorException.getErrorCode());
            slcEntity.setMsg(slcEntityErrorException.getMessage());
            if (StringUtils.isEmpty(slcEntity.getMsg())) {
                slcEntity.setMsg(StringUtils.getString(R.string.net_date_connection_failure));
            }
        } else if (e instanceof ConnectException) {
            ConnectException connectException = (ConnectException) e;
            slcEntity = new SlcEntity<>();
            slcEntity.setCode(ApiConfig.NORMAL_ERROR);
            slcEntity.setMsg(connectException.getMessage());
            if (StringUtils.isEmpty(slcEntity.getMsg())) {
                slcEntity.setMsg(StringUtils.getString(R.string.net_date_connection_failure));
            }
        }
        if (slcEntity == null) {
            slcEntity = new SlcEntity<>();
            slcEntity.setCode(ApiConfig.NORMAL_ERROR);
            slcEntity.setMsg(StringUtils.getString(R.string.net_date_an_unknown_exception_occurred));
        }
        return slcEntity;
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
    protected void showToast(int code, String msg) {
        if (mSlcCallbackConfig.isIsShowToast()) {
            if (code == ApiConfig.USER_INFO_ERROR || code == ApiConfig.NORMAL_ERROR || code == ApiConfig.RESULT_UPLOAD_FAILURE) {
                ToastUtils.showShort(msg);
            } else {
                ToastUtils.showShort(mSlcCallbackConfig.getToastRes());
            }
        }
    }

    protected abstract void onSucceed(T data);

    protected abstract void onFailed(int errorCode, String errorMessage);
}
