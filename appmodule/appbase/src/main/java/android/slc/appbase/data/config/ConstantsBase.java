package android.slc.appbase.data.config;

import android.Manifest;
import android.os.Build;
import android.os.SystemClock;

import com.blankj.utilcode.util.PathUtils;

import java.io.File;

public class ConstantsBase {
    public static boolean VALUE_IS_ON_LINE = true;

    public static class Key {
        public static final String KEY_INTENT_CARRY_PARAM = "intentCarryParam";
        public static final String KEY_TITLE_LABEL = "titleLabel";
        public static final String KEY_INTENT_DATA = "intentData";
        public static final String KEY_SEARCH_TEXT = "searchText";
        public static final String KEY_LOCAL_HTML = "localHtml";
    }

    public static class Value {
        public static final String VALUE_TIME_PATTERN_BY_DATE_BY_THICK_LINE = "yyyy-MM-dd";
        public static final String VALUE_TIME_PATTERN_BY_DATE_TIME_BY_THICK_LINE = "yyyy-MM-dd HH:mm:ss";
        public static final String VALUE_TIME_PATTERN_BY_TIME = "HH:mm";
        public static final String VALUE_TIME_PATTERN_BY_DATE = "yyyy年MM月dd日";
        public static final String VALUE_TIME_PATTERN_BY_MONTH = "yyyy年MM月";
        public static final String VALUE_TIME_PATTERN_BY_MINUTE = "yyyy年MM月dd日 HH:mm";
        public static final String VALUE_RESPONSE_BODY_IS_NULL = "Null is not a valid element";
        public static final String VALUE_SYS_MUST_DATA = "sysMustData";//系统必须文件夹
        public static final String VALUE_SYS_USER_ICON = "sysUserIcon";//系统必须图片文件夹
        public static final String PATH_SYS_MUST_DATA = PathUtils.getExternalAppFilesPath() + File.separator + VALUE_SYS_MUST_DATA;//系统必须文件路径
        public static final String PATH_SYS_USER_ICON = PATH_SYS_MUST_DATA + File.separator + VALUE_SYS_USER_ICON;//系统必须用户头像路径
        public static final String PATH_NEWS_BODY_FILE_PATH = PathUtils.getExternalAppFilesPath() + File.separator + "new_body.html";//外置文件路径

        public static final int P_NONE = 0;         //无状态
        public static final int P_WAITING = 1;      //等待
        public static final int P_LOADING = 2;      //下载中
        public static final int P_PAUSE = 3;        //暂停
        public static final int P_ERROR = 4;        //错误
        public static final int P_FINISH = 5;       //完成
    }

    public static class Event {

    }

    public static class Path {

    }

    public static class Permission {
        public static String[] getBasePermissionArray() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
            } else {
                return new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
            }
        }
    }

    public static String systemBootTimeByMinute() {
        return String.valueOf(SystemClock.elapsedRealtime() / 1000 / 60);
    }
}
