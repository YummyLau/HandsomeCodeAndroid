package com.effective.android.net.retrofit.coverts;

import com.effective.android.net.MediaTypes;

import java.lang.annotation.Annotation;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * string 转化
 * Created by yummyLau on 2018/7/30.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public final class ToStringConverterFactory extends Converter.Factory {

    public static ToStringConverterFactory create() {
        return new ToStringConverterFactory();
    }

    private ToStringConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<String, RequestBody>() {
                @Override
                public RequestBody convert(String value) throws IOException {
                    return RequestBody.create(MediaTypes.TEXT, value);
                }
            };
        }
        return null;
    }
}

