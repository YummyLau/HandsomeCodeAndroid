package com.example.code.exoplayer.interfaces.support;

/**
 * 控制层
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface IControlView {

    void updateBufferPosition(long bufferPosition);

    void setupDuration(long duration);
}
