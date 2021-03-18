package android.slc.appbase.data.repository.remote;

import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.appbase.data.entity.CheckUpdateResp;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author slc
 * @date 2020-09-23 15:12
 */
public interface VersionService {

    /**
     * 检测更新
     */
    @FormUrlEncoded
    @POST("http://101.132.144.109:8085/fire.mobile/qry_appInfo_by_packageName")
    Observable<SlcEntity<CheckUpdateResp>> checkUpdate(@FieldMap Map<String, String> data);

    /**
     * 下载apk
     */
    @GET
    @Streaming
    Call<ResponseBody> downloadApk(@Url String url);
}
