package com.example.code.exoplayer.callback;

import com.example.code.exoplayer.annotations.VideoStatus;

/**
 * 视频状态回调
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface VideoStatusCallback {
    void onStatus(@VideoStatus int videoStatus);
}
