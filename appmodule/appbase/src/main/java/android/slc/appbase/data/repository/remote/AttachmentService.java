package android.slc.appbase.data.repository.remote;


import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.appbase.data.entity.AttFileInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author slc
 * @date 2020-09-07 14:40
 */
public interface AttachmentService {
    @POST("api/localStorage/uploadFile")
    Observable<SlcEntity<AttFileInfo>> uploadFile(@Body MultipartBody multipartBody);

    @GET("api/localStorage/queryByIds")
    Observable<SlcEntity<List<AttFileInfo>>> findListById(@Query("ids") Long[] ids);

    @GET("api/localStorage/queryByIds")
    Call<SlcEntity<List<AttFileInfo>>> findListByIdByCall(@Query("ids") Long[] ids);

}
