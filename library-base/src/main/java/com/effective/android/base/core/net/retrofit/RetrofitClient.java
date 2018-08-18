package com.effective.android.base.core.net.retrofit;


import com.effective.android.base.core.net.okhttp.HttpClient;
import com.effective.android.base.core.net.retrofit.coverts.ToByteConvertFactory;
import com.effective.android.base.core.net.retrofit.coverts.ToStringConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yummyLau on 2018/7/30.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class RetrofitClient {

    private static volatile RetrofitClient sClient;
    private static String BASE_URL = "https://c7a894f7-7fd9-4536-935d-5c0d073e7c03.mock.pstmn.io/";


    private RetrofitClient() {
    }

    public static RetrofitClient getInstance() {
        if (sClient == null) {
            synchronized (RetrofitClient.class) {
                if (sClient == null) {
                    sClient = new RetrofitClient();
                }
            }
        }
        return sClient;
    }

    public <T> T getService(Class<T> tClass) {
        return getRetrofit(RetrofitType.GSON).create(tClass);
    }

    public <T> T getService(@RetrofitType int type, Class<T> tClass) {
        return getRetrofit(type).create(tClass);
    }

    private Retrofit getRetrofit(@RetrofitType int type) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(HttpClient.getInstance());
        switch (type) {
            case RetrofitType.GSON: {
                builder.addConverterFactory(GsonConverterFactory.create());
                break;
            }
            case RetrofitType.BTYP: {
                builder.addConverterFactory(ToByteConvertFactory.create());
                break;
            }
            case RetrofitType.STRING: {
                builder.addConverterFactory(ToStringConverterFactory.create());
                break;
            }
            default: {
                builder.addConverterFactory(GsonConverterFactory.create());
            }
        }
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL);
        return builder.build();
    }
}
