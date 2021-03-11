package android.slc.user.data.repository.remote;

import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.extras.user.entity.LoginResult;
import android.slc.extras.user.entity.UserVo;
import android.slc.or.SlcNu;
import android.slc.or.SlcParam;
import android.slc.rx.SlcRxJavaUtils;

import io.reactivex.Observable;

/**
 * @author slc
 * @date 2020-06-19 14:44
 */
public class UserServiceDelegate {

    /**
     * 用户登录
     *
     * @param account
     * @param password
     * @return
     */
    public static Observable<SlcEntity<UserVo>> login(String account, String password) {
        return SlcNu.getInstance().create(UserService.class)
                .login(SlcParam.newBuilder().put("username", account).put("password", password).build())
                .map(slcEntity -> {
                    LoginResult loginResult = slcEntity.getData();
                    SlcEntity<UserVo> userSlcEntity = new SlcEntity<>();
                    userSlcEntity.setCode(slcEntity.getCode());
                    userSlcEntity.setMsg(slcEntity.getMsg());
                    userSlcEntity.setData(loginResult.getUser());
                    return userSlcEntity;
                }).compose(SlcRxJavaUtils.applyOtAndroidSchedulers());
    }
}
