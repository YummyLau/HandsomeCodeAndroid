package com.effective.android.jswebview.interfaces;

/**
 * 监听页面加载完成
 * Created by yummyLau on 2018/7/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface OnFinishCallback {
    void onSuccess(String url);

    void onFail(String url);
}
