package com.effective.android.video.interfaces.callback;

/**
 * 播放器暴露进度回调
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface ProcessCallback {

    void onDuration(long duration);

    void onCurrentPosition(long currentPosition);

    void onBufferPosition(long bufferPosition);
}
