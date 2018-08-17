package com.effective.android.video.interfaces.callback;


import com.effective.android.video.annotations.VideoStatus;

/**
 * 播放器暴露状态回调
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface VideoStatusCallback {
    void onStatus(@VideoStatus int videoStatus);
}
