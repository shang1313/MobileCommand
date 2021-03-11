package android.slc.appbase.data.amap;

import android.slc.commonlibrary.subutil.LocationUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.blankj.utilcode.util.Utils;

import io.reactivex.Observable;

public class AMapLocationByService {
    @Deprecated
    private void start() {
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        //设置定位模式
        locationOption.setLocationMode(LocationUtils.isGpsEnabled() ? AMapLocationClientOption.AMapLocationMode.Hight_Accuracy :
                AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置连续定位时间间隔
        //locationOption.setInterval(60000);
        //使用三秒内最精确的一次结果
        //locationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息
        locationOption.setNeedAddress(true);
        //start(locationOption);
    }

    /**
     * 单次获取位置
     *
     * @return
     */
    public static Observable<AMapLocation> startSignIn() {
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        locationOption.setLocationMode(LocationUtils.isGpsEnabled() ? AMapLocationClientOption.AMapLocationMode.Hight_Accuracy :
                AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        locationOption.setNeedAddress(true);
        return getLocation(locationOption);
    }

    /**
     * 获取运动位置
     * @return
     */
    public static Observable<AMapLocation> startSport() {
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport);
        locationOption.setLocationMode(LocationUtils.isGpsEnabled() ? AMapLocationClientOption.AMapLocationMode.Hight_Accuracy :
                AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        locationOption.setNeedAddress(true);
        return getLocation(locationOption);
    }

    /**
     * 单词获取位置最精确的结果
     *
     * @return
     */
    public static Observable<AMapLocation> getSignInByOnceLocationLatest() {
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        locationOption.setLocationMode(LocationUtils.isGpsEnabled() ? AMapLocationClientOption.AMapLocationMode.Hight_Accuracy :
                AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //使用三秒内最精确的一次结果
        locationOption.setOnceLocationLatest(true);
        locationOption.setNeedAddress(true);
        return getLocation(locationOption);
    }

    /**
     * 获取位置
     *
     * @param locationOption
     * @return
     */
    public static Observable<AMapLocation> getLocation(AMapLocationClientOption locationOption) {
        return Observable.create(emitter -> {
            //初始化定位
            AMapLocationClient mLocationClient = new AMapLocationClient(Utils.getApp());
            //设置定位回调监听
            mLocationClient.setLocationListener(aMapLocation -> {
                emitter.onNext(aMapLocation);
                if (locationOption.getLocationPurpose() == AMapLocationClientOption.AMapLocationPurpose.SignIn) {
                    emitter.onComplete();
                }
            });
            mLocationClient.setLocationOption(locationOption);
            //启动定位
            mLocationClient.startLocation();
            emitter.setCancellable(() -> {
                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
            });
        });
    }

}
