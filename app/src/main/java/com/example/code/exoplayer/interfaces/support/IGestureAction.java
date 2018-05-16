package com.example.code.exoplayer.interfaces.support;

/**
 * 手势层回调回传播放器
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface IGestureAction {

    boolean onLongClick();

    boolean onSingleClick();

    boolean onDoubleClick();

    void seekToPosition(long position);

    long getDuration();

    long getCurrentPosition();
}
