package android.slc.appbase.utils;

import android.slc.slcdialog.SlcPopup;

import androidx.annotation.StringRes;

import com.blankj.utilcode.util.ActivityUtils;

public class LoadingUtils {
    private static final String KEY_PRESENTER_DIALOG = "loadingDialog";

    public static void showLoadingDialog(String loadingMsg) {
        showLoadingDialog(KEY_PRESENTER_DIALOG, loadingMsg);
    }

    public static void showLoadingDialog(String key, String loadingMsg) {
        new SlcPopup.LoadingBuilder(ActivityUtils.getTopActivity()).setMessage(loadingMsg).setKey(key).setCancelable(false).create().show();
    }

    public static void showLoadingDialog(@StringRes int loadingRes) {
        showLoadingDialog(KEY_PRESENTER_DIALOG, loadingRes);
    }

    public static void showLoadingDialog(String key, @StringRes int loadingRes) {
        new SlcPopup.LoadingBuilder(ActivityUtils.getTopActivity()).setMessage(loadingRes).setKey(key).setCancelable(false).create().show();
    }

    public static void dismissLoadingDialog() {
        dismissLoadingDialog(KEY_PRESENTER_DIALOG);
    }
    public static void dismissLoadingDialog(String key) {
        SlcPopup.dismissByKey(key);
    }
}
