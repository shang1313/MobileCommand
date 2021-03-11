package android.slc.appbase.data.repository.remote;

import android.slc.appbase.data.api.EncryptionAndDecryptionTools;
import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.extras.user.entity.UserVo;
import android.slc.extras.user.repository.local.UserDaoRepository;
import android.slc.or.SlcNu;
import android.slc.or.SlcParam;
import android.slc.rx.SlcRxJavaUtils;

import java.util.Map;

import io.reactivex.Observable;

public class AppServiceRepository {
    /**
     * 验证设备
     *
     * @param deviceId
     * @return
     */
    public static Observable<SlcEntity<UserVo>> terminalVerify(String deviceId) {
        return SlcNu.getInstance().create(AppService.class)
                .terminalVerify(EncryptionAndDecryptionTools.makeDataWithToken(SlcParam.newBuilder().put("deviceId", deviceId).build()))
                .map(UserDaoRepository.saveUserByMap())
                .compose(SlcRxJavaUtils.applyOtAndroidSchedulers());
    }


    /**
     * 注册设备
     *
     * @param data
     * @return
     */
    public static Observable<SlcEntity<Object>> terminalRegister(Map<String, Object> data) {
        return SlcNu.getInstance().create(AppService.class)
                .terminalRegister(EncryptionAndDecryptionTools.makeDataWithToken(data))
                .compose(SlcRxJavaUtils.applyOtAndroidSchedulers());
    }

    /**
     * 更新设备信息
     *
     * @param data
     * @return
     */
    public static Observable<SlcEntity<Object>> terminalInfoUpdate(Map<String, Object> data) {
        return SlcNu.getInstance().create(AppService.class)
                .terminalInfoUpdate(EncryptionAndDecryptionTools.makeDataWithToken(data))
                .compose(SlcRxJavaUtils.applyOtAndroidSchedulers());
    }

}
