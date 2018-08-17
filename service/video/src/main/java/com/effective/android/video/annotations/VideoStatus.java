package com.effective.android.video.annotations;

import android.support.annotation.IntDef;

/**
 * FIRST_FRAME
 * before load media source,init some video info,such as video duration,load glCoverView
 * BUFFERING
 * loading media source, happen before FINISHED but after FIRST_FRAME
 * Created by yummyLau on 2018/5/8.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

@IntDef({VideoStatus.NONE, VideoStatus.PREPARE, VideoStatus.BUFFERING, VideoStatus.PLAYING,
        VideoStatus.PAUSE, VideoStatus.FINISHED, VideoStatus.ERROR})
public @interface VideoStatus {
    int NONE = -1;
    int PREPARE = 1;
    int BUFFERING = 2;
    int PLAYING = 3;
    int PAUSE = 4;
    int FINISHED = 5;
    int ERROR = 6;
}
