package android.slc.extras.user.entity;

/**
 * 登录返回结果
 *
 * @author slc
 * @date 2020-12-25 20:26
 */
public class LoginResult {
    private UserVo user;
    private String token;

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
