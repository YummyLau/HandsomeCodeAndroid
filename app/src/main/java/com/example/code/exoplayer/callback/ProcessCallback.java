package com.example.code.exoplayer.callback;

/**
 * 进度回调
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface ProcessCallback {

    void onDuration(long duration);

    void onCurrentPosition(long currentPosition);

    void onBufferPosition(long bufferPosition);
}
