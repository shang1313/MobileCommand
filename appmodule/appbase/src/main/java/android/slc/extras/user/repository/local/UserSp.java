package android.slc.extras.user.repository.local;


import com.blankj.utilcode.util.SPUtils;

public class UserSp {
    private final static String SP_NAME = "user";
    private final static String KEY_CURRENT_USER = "currentUser";
    private final static String KEY_CURRENT_PASSWORD = "currentPassword";
    private final static String KEY_IS_LOGIN = "autoLogin";
    private final static String KEY_SAVE_PASSWORD = "savePassword";

    public static SPUtils getInstance() {
        return SPUtils.getInstance(SP_NAME);
    }

    /**
     * 获取当前保存的用户
     *
     * @return
     */
    public static String getCurrentUser() {
        return getInstance().getString(KEY_CURRENT_USER);
    }

    /**
     * 获取当前保存的密码
     *
     * @return
     */
    public static String getCurrentPassword() {
        return getInstance().getString(KEY_CURRENT_PASSWORD);
    }

    /**
     * 是否自动登录
     *
     * @return
     */
    public static boolean getIsAutoLogin() {
        return getInstance().getBoolean(KEY_IS_LOGIN, true);
    }

    /**
     * 是否保存密码
     *
     * @return
     */
    public static boolean getIsSavePassword() {
        return getInstance().getBoolean(KEY_SAVE_PASSWORD, true);
    }

    /**
     * 保存当前用户
     *
     * @param account
     */
    public static void saveCurrentUser(String account) {
        getInstance().put(KEY_CURRENT_USER, account);
    }

    /**
     * 保存当前密码
     *
     * @param password
     */
    public static void saveCurrentPassword(String password) {
        getInstance().put(KEY_CURRENT_PASSWORD, password);
    }

    /**
     * 自动登录
     *
     * @param isAutoLogin
     */
    public static void setAutoLogin(boolean isAutoLogin) {
        getInstance().put(KEY_IS_LOGIN, isAutoLogin);
    }

    /**
     * 保存密码
     *
     * @param isSavePassword
     */
    public static void setSavePassword(boolean isSavePassword) {
        getInstance().put(KEY_SAVE_PASSWORD, isSavePassword);
    }
}
