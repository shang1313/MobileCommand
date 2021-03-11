package android.slc.appbase.data.repository.remote;

import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.extras.user.entity.UserVo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppService {
    @POST("terminal_verify")
    @FormUrlEncoded
    Observable<SlcEntity<UserVo>> terminalVerify(@FieldMap Map<String, String> data);

    @POST("terminal_register")
    @FormUrlEncoded
    Observable<SlcEntity<Object>> terminalRegister(@FieldMap Map<String, String> data);

    @POST("terminal_info_update")
    @FormUrlEncoded
    Observable<SlcEntity<Object>> terminalInfoUpdate(@FieldMap Map<String, String> data);

}
