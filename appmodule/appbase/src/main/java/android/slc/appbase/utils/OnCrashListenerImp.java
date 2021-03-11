package android.slc.appbase.utils;

import android.os.Handler;
import android.os.Looper;
import android.slc.code.exception.MvpNullPointerException;
import android.slc.commonlibrary.util.compat.SlcCrashUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;

public class OnCrashListenerImp implements SlcCrashUtils.OnCrashListener {
    @Override
    public boolean onCrash(String crashInfo, Throwable e) {
        if (e instanceof MvpNullPointerException) {
            return true;
        }
        /*Looper.loop();
        ActivityUtils.getTopActivity().finish();*/
        LogUtils.iTag(crashInfo, e);
        /*if (!IntentConstant.VALUE_IS_ON_LINE) {
            AppUtils.exitApp();
        }*/
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtils.exitApp();
            }
        }, 3000);
        return false;
    }
}