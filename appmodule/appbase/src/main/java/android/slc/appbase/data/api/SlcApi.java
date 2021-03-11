package android.slc.appbase.data.api;

import android.content.Context;
import android.slc.appbase.data.api.ds.converter.SlcConverterFactoryDs;
import android.slc.appbase.data.api.main.converter.SlcConverterFactory;
import android.slc.appbase.data.api.main.converter.upload.SlcUploadConverterFactory;
import android.slc.appbase.data.api.main.interceptor.TokenInterceptor;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.or.SlcNu;
import android.util.Log;

import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.core.connection.DownloadOkHttp3ConnectionFixBug;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class SlcApi {
    public static void init(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = null;
        if (!ConstantsBase.VALUE_IS_ON_LINE) {
            httpLoggingInterceptor = new HttpLoggingInterceptor((String message) -> Log.d("OkHttp", message));
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .connectTimeout(12, TimeUnit.SECONDS)
                .addNetworkInterceptor(new TokenInterceptor());
        if (httpLoggingInterceptor != null) {
            builder.addNetworkInterceptor(httpLoggingInterceptor);
        }
        //main
        SlcNu.getInstance().setDefRetrofit(new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .client(builder.build())
                .addConverterFactory(SlcConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build());
        //upload
        SlcNu.getInstance().putRetrofit(ApiConfig.API_MAIN_UPLOAD, new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .client(builder.build())
                .addConverterFactory(SlcUploadConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build());
        //多胜接口的
        OkHttpClient.Builder builderDs = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(new android.slc.appbase.data.api.ds.interceptor.TokenInterceptor());
        if (httpLoggingInterceptor != null) {
            builderDs.addNetworkInterceptor(httpLoggingInterceptor);
        }
        SlcNu.getInstance().putRetrofit(ApiConfig.API_DS, new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL_DS)
                .client(builderDs.build())
                .addConverterFactory(SlcConverterFactoryDs.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build());

        OkDownload.Builder okDownloadBuilder = new OkDownload.Builder(context).connectionFactory(new DownloadOkHttp3ConnectionFixBug.Factory());
        OkDownload.setSingletonInstance(okDownloadBuilder.build());
    }
}
