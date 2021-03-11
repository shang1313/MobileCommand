package android.slc.user.data.repository.remote;

import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.extras.user.entity.LoginResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author slc
 * @date 2020-07-14 17:51
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @return
     */
    @POST("client/login")
    Observable<SlcEntity<LoginResult>> login(@Body Map<String, Object> data);

}
