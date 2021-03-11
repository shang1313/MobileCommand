package android.slc.appbase.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.slc.appbase.data.amap.AMapLocationByService;
import android.slc.appbase.data.api.main.callback.SlcObserver;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.appbase.data.repository.remote.AppServiceRepository;
import android.slc.appbase.data.utils.SimpleDisposableObserver;
import android.slc.appbase.vm.GlobalDataVm;
import android.slc.appbase.vm.GlobalNotifyVm;
import android.slc.or.SlcCallbackConfig;
import android.slc.or.SlcParam;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.DeviceUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;


/**
 * @author slc
 * @date 2020-09-11 11:00
 */
public class BaseBackService extends Service {
    private Disposable LocationDisposable;
    private final Observable.OnPropertyChangedCallback notifyUploadUserInfoChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            AppServiceRepository.terminalInfoUpdate(SlcParam.newBuilder()
                    .put("deviceId", DeviceUtils.getUniqueDeviceId())
                    .put("workLong", ConstantsBase.systemBootTimeByMinute())
                    .put("latitude", (Double) GlobalDataVm.getInstance().getData("latitude"))
                    .put("longitude", (Double) GlobalDataVm.getInstance().getData("longitude"))
                    .build())
                    .subscribe(new SlcObserver<Object>(SlcCallbackConfig.newBuilder().showToast(false).build()) {
                        @Override
                        protected void onSucceed(Object data) {

                        }

                        @Override
                        protected void onFailed(int errorCode, String errorMessage) {

                        }
                    });
        }
    };
    private final Observable.OnPropertyChangedCallback notifyUpdateLocationChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            LocationDisposable = AMapLocationByService.startSignIn().subscribeWith(new SimpleDisposableObserver<AMapLocation>() {

                @Override
                public void onNext(@NotNull AMapLocation aMapLocation) {
                    super.onNext(aMapLocation);
                    GlobalDataVm.getInstance().putData("latitude", aMapLocation.getLatitude());
                    GlobalDataVm.getInstance().putData("longitude", aMapLocation.getLongitude());
                    notifyUploadUserInfoChangedCallback.onPropertyChanged(GlobalNotifyVm.getInstance().notifyUploadUserInfoOf, 0);
                }

            });
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalNotifyVm.getInstance().notifyUploadUserInfoOf.addOnPropertyChangedCallback(notifyUploadUserInfoChangedCallback);
        GlobalNotifyVm.getInstance().notifyUpdateLocationOf.addOnPropertyChangedCallback(notifyUpdateLocationChangedCallback);
        notifyUpdateLocationChangedCallback.onPropertyChanged(GlobalNotifyVm.getInstance().notifyUpdateLocationOf, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (LocationDisposable != null && !LocationDisposable.isDisposed()) {
            LocationDisposable.dispose();
        }
        GlobalNotifyVm.getInstance().notifyUploadUserInfoOf.removeOnPropertyChangedCallback(notifyUploadUserInfoChangedCallback);
        GlobalNotifyVm.getInstance().notifyUploadUserInfoOf.removeOnPropertyChangedCallback(notifyUploadUserInfoChangedCallback);
    }
}
