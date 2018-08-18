package com.effective.android.video.interfaces.support;

/**
 * 控制层回传播放器
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface IControlAction {
    void onBack();

    void onVolume();

    void onPlay();

    void onPause();

    void onReplay();

    boolean onFullScreen();
}
