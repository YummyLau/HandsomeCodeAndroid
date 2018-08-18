package com.effective.android.base.core.net.retrofit.coverts;

import android.util.Log;


import com.effective.android.base.core.net.MediaTypes;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 请求数据转字节
 * Created by yummyLau on 2018/7/30.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class ToByteConvertFactory extends Converter.Factory {

    private static final String TAG = ToByteConvertFactory.class.getSimpleName();

    public static ToByteConvertFactory create() {
        return new ToByteConvertFactory();
    }

    private ToByteConvertFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Log.e(TAG, "convert: Converter<?, RequestBody>000" + type + "    " + byte[].class);
        if ("byte[]".equals(type + "")) {
            return new Converter<ResponseBody, byte[]>() {
                @Override
                public byte[] convert(ResponseBody value) throws IOException {
                    Log.e(TAG, "convert: Converter<ResponseBody, ?>");
                    return value.bytes();
                }
            };
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        Log.e(TAG, "convert: Converter<?, RequestBody>000");
        if ("byte[]".equals(type + "")) {
            return new Converter<byte[], RequestBody>() {
                @Override
                public RequestBody convert(byte[] value) throws IOException {
                    Log.e(TAG, "convert: Converter<?, RequestBody>");
                    return RequestBody.create(MediaTypes.STREAM, value);
                }
            };
        }
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
