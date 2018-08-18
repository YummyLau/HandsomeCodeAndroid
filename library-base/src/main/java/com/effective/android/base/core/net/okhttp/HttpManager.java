package com.effective.android.base.core.net.okhttp;

import android.util.Log;

import com.effective.android.base.core.net.MediaTypes;
import com.effective.android.base.core.net.okhttp.interfaces.IHttpDo;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装常规请求
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class HttpManager implements IHttpDo {

    private static final String TAG = HttpManager.class.getSimpleName();

    @Override
    public String get(String url) {
        String result = null;
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = HttpClient.getInstance().newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }


    @Override
    public String post(String url, String json) {
        String result = null;
        try {
            RequestBody body = RequestBody.create(MediaTypes.JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = HttpClient.getInstance().newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }

    @Override
    public String post(String url, Map<String, String> mapParams) {
        String result = null;
        try {
            FormBody.Builder builder = new FormBody.Builder();
            if (mapParams != null && !mapParams.isEmpty()) {
                for (String key : mapParams.keySet()) {
                    builder.add(key, mapParams.get(key));
                }
            }
            Request request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();
            Response response = HttpClient.getInstance().newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }

    @Override
    public String uploadFile(String url, String pathName, String fileName) {
        String result = null;
        try {
            RequestBody body = RequestBody.create(MediaTypes.FILE, new File(pathName));
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(MediaTypes.FILE.type(), fileName, body);
            Request request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();
            Response response = HttpClient.getInstance().newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }
}
