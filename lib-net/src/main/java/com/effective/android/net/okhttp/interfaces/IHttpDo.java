package com.effective.android.net.okhttp.interfaces;

import java.util.Map;


/**
 * 封装常规请求
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface IHttpDo {

    String get(String url);

    String post(String url, String json);

    String post(String url, Map<String, String> mapParams);

    String uploadFile(String url, String pathName, String fileName);
}
