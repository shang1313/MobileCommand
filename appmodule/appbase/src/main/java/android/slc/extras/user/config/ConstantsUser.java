package android.slc.extras.user.config;

import android.content.Context;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.extras.user.repository.local.UserSp;
import android.slc.appbase.vm.GlobalDataVm;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;

/**
 * @author slc
 * @date 2020-06-19 15:21
 */
public class ConstantsUser extends ConstantsBase {
    public static class Key extends ConstantsBase.Key {
        public static final String KEY_UP_DATE = "upDateApp";
        public static final long KEY_SYSTEM_NOTIFY_INT = 99999990;//系统消息用户id
        public static final long KEY_ANNOUNCEMENT_INT = 99999991;//公告消息用户id
        public static final int KEY_SYSTEM_NOTIFY_INT_SERVICE = 0;//系统消息后类型
        public static final int KEY_ANNOUNCEMENT_INT_SERVICE = 1;//通知公告后台类型
        public static final String KEY_SYSTEM_NOTIFY = String.valueOf(KEY_SYSTEM_NOTIFY_INT);//系统消息手机号，本地虚拟定义
        public static final String KEY_ANNOUNCEMENT = String.valueOf(KEY_ANNOUNCEMENT_INT);//通知公告手机号，本地虚拟定义
        public static final String[] KEY_ARRAY_MSG_INFO_TYPE = {KEY_SYSTEM_NOTIFY, KEY_ANNOUNCEMENT};
        public static final long[] KEY_ARRAY_MSG_INFO_TYPE_INT = {KEY_SYSTEM_NOTIFY_INT, KEY_ANNOUNCEMENT_INT};
    }

    public static class Value extends ConstantsBase.Value {
        public static final int USER_STATUS_ACTIVATION = 0;
        public static final int USER_STATUS_INACTIVATED = 1;
        public static final int USER_STATUS_LOCKING = 2;
        public static final int USER_STATUS_ERASE = 3;
        public static final int USER_STATUS_LOGOUT = 4;
        public static final int USER_STATUS_RESET = 5;
    }

    public static class Event extends ConstantsBase.Event {

    }

    public static class Path extends ConstantsBase.Path {
        public static final String PATH_USER_INFO = "/user/user_info_activity";
        public static final String PATH_LOGIN = "/user/login_activity";
        public static final String PATH_UP_DATA_ACTIVITY = "/user/up_data_activity";
        public static final String PATH_MY_INFO_FRAGMENT_SERVICE = "/user/my_info_fragment_service";
    }

    public static void outLogin(Context context) {
        UserSp.setAutoLogin(false);
        GlobalDataVm.getInstance().userOf.set(null);
        ActivityUtils.finishAllActivities();
        ARouter.getInstance().build(Path.PATH_LOGIN).navigation(context);
    }
}
