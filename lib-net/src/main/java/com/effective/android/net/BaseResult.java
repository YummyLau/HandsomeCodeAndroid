package com.effective.android.net;

import com.google.gson.annotations.SerializedName;

/**
 * 自主使用 postman 自定义 mockserver 测试
 * 统一管理返回值
 * {
 * "code" : 2000,
 * "message" : "ok",
 * "data" : {
 * "a" : "a",
 * "b" : "b",
 * "c" : 1
 * }
 * }
 * Created by yummyLau on 2018/7/30.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class BaseResult<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return code == 200;
    }
}
