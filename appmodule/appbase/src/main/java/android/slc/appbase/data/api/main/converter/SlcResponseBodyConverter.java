/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.slc.appbase.data.api.main.converter;

import android.slc.appbase.data.api.ApiConfig;
import android.slc.appbase.data.api.EncryptionAndDecryptionTools;
import android.slc.appbase.data.api.main.callback.po.SlcEntity;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public final class SlcResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public SlcResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        if (value.contentLength() == 0) {
            return null;
        }
        SlcEntity<Object> slcEntityTemp = GsonUtils.fromJson(value.charStream(), GsonUtils.getType(SlcEntity.class, Object.class));
        if (slcEntityTemp.isSuccess()) {
            String retData = slcEntityTemp.getRetData();
            try {
                slcEntityTemp.setData(GsonUtils.fromJson(EncryptionAndDecryptionTools.aesDecrypt(retData, slcEntityTemp.getSalt() + EncryptionAndDecryptionTools.E_KEY), Object.class));
                slcEntityTemp.setRetData(null);
            } catch (Exception e) {
                throw new SlcEntityErrorException(ApiConfig.NORMAL_ERROR, "解密错误");
            }
        }
        try {
            T result = adapter.fromJson(GsonUtils.toJson(slcEntityTemp));
            if (result instanceof SlcEntity) {
                SlcEntity slcEntity = (SlcEntity) result;
                if (!slcEntity.isSuccess()) {
                    throw new SlcEntityErrorException(slcEntity.getCode(), slcEntity.getMsg());
                }
            }
            return result;
        } finally {
            value.close();
        }
    }
}
