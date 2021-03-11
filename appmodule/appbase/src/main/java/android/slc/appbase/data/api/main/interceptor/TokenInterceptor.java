package android.slc.appbase.data.api.main.interceptor;

import android.slc.appbase.data.api.ApiConfig;
import android.slc.or.ApiConstants;

import com.blankj.utilcode.util.StringUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Headers headers = request.headers();
        String contentType = ApiConstants.VALUE_APPLICATION_JSON;
        boolean isHaveContentType = false, isHaveToken = false;
        for (int i = 0; i < headers.size(); i++) {
            String name = headers.name(i);
            if (ApiConstants.KEY_CONTENT_TYPE.equals(name)) {
                contentType = headers.get(name);
                isHaveContentType = true;
            } else if (ApiConfig.KEY_TOKEN.equals(name)) {
                isHaveToken = true;
            }
        }
        String token = null;
        //GET方法
        Request.Builder requestBuilder = request.newBuilder();
        if (METHOD_GET.equals(request.method())) {
            HttpUrl.Builder urlBuilder = request.url().newBuilder();
            HttpUrl httpUrl = urlBuilder.build();
            httpUrl.queryParameter(ApiConfig.KEY_TOKEN);
        } else if (METHOD_POST.equals(request.method())) { // POST方法
            RequestBody body = request.body();// 得到请求体
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                for (int i = 0; i < formBody.size(); i++) {
                    if (StringUtils.equals(ApiConfig.KEY_TOKEN, formBody.encodedName(i))) {
                        token = formBody.encodedValue(i);
                        break;
                    }
                }
            } /*else if (body instanceof MultipartBody) {
                MultipartBody.Builder multipartBodyBuilder = SlcParam.createMultBodyBuilder();
                MultipartBody multipartBody = (MultipartBody) body;
                List<MultipartBody.Part> partList = multipartBody.parts();
                if (CollectionUtils.isNotEmpty(partList)) {
                    for (MultipartBody.Part part : partList) {
                        if (part.body() instanceof FileRequestBody) {
                            multipartBodyBuilder.addPart(part.body());
                        } else {
                            RequestBody requestBody = (RequestBody) part.body();
                            Buffer buffer = new Buffer();
                            requestBody.writeTo(buffer);//将请求体内容,写入缓存
                            String parameterStr = buffer.readUtf8();
                            buffer.close();
                            //Map<String, String> parameter = GsonUtils.fromJson(parameterStr, GsonUtils.getMapType(String.class, String.class));
                            //if (parameter.get(ApiConfig.KEY_TOKEN) != null) {
                            //    token = parameter.get(ApiConfig.KEY_TOKEN);
                            //}
                            multipartBodyBuilder.addPart(RequestBody.create(parameterStr, MediaType.parse(contentType)));
                        }
                    }
                }
                requestBuilder.method(request.method(), multipartBodyBuilder.build());
            }*/
        }
        if (!isHaveContentType) {
            requestBuilder.addHeader(ApiConstants.KEY_CONTENT_TYPE, ApiConstants.VALUE_APPLICATION_JSON);
        }
        if (!isHaveToken && token != null) {
            requestBuilder.addHeader(ApiConfig.KEY_TOKEN, token);
        }
        request = requestBuilder.build();
        return chain.proceed(request);
    }
}
