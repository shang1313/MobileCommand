package android.slc.appbase.data.api.main.callback;

import android.slc.appbase.data.api.main.callback.po.SlcEntity;
import android.slc.appbase.data.api.main.converter.SlcEntityErrorException;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @author slc
 * @date 2019/11/5 14:37
 */
public class SlcEntityConverter {
    /**
     * ResponseBody转实体
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> Function<ResponseBody, T> applyResponseBodyToEntity(Type type) {
        return new Function<ResponseBody, T>() {
            @Override
            public T apply(ResponseBody responseBody) throws IOException {
                JsonReader jsonReader = GsonUtils.getGson().newJsonReader(responseBody.charStream());
                try {
                    T result = (T) GsonUtils.getGson().getAdapter(TypeToken.get(GsonUtils.getType(SlcEntity.class, type))).read(jsonReader);
                    if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                        throw new JsonIOException("JSON document was not fully consumed.");
                    }
                    if (result instanceof SlcEntity) {
                        SlcEntity slcEntity = (SlcEntity) result;
                        if (!slcEntity.isSuccess()) {
                            throw new SlcEntityErrorException(slcEntity.getCode(), slcEntity.getMsg());
                        }
                    }
                    return result;
                } finally {
                    responseBody.close();
                }
            }
        };
    }
}
