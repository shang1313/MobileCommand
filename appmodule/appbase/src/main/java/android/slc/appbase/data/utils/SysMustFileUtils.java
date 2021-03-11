package android.slc.appbase.data.utils;

import android.slc.appbase.data.config.ConstantsBase;
import android.slc.rx.SlcRxJavaUtils;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

public class SysMustFileUtils {
    public static Single<Boolean> initAssets() {
        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            if (!FileUtils.isFileExists(ConstantsBase.Value.PATH_SYS_USER_ICON)) {
                String sysUserPathAbs = ConstantsBase.Value.VALUE_SYS_MUST_DATA + File.separator + ConstantsBase.Value.VALUE_SYS_USER_ICON;
                String[] userIconNameArray = Utils.getApp().getAssets().list(ConstantsBase.Value.VALUE_SYS_MUST_DATA + File.separator + ConstantsBase.Value.VALUE_SYS_USER_ICON);
                for (String userIconName : userIconNameArray) {
                    FileIOUtils.writeFileFromIS(ConstantsBase.Value.PATH_SYS_USER_ICON + File.separator + userIconName,
                            Utils.getApp().getAssets().open(sysUserPathAbs + File.separator + userIconName));
                }
            }
            emitter.onSuccess(true);
        }).compose(SlcRxJavaUtils.applyOsAndroidSchedulers());
    }
}
