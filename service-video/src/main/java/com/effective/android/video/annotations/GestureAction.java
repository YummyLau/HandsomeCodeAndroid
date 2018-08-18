package com.effective.android.video.annotations;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 手势行为
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@IntDef({GestureAction.MODIFY_NONE, GestureAction.MODIFY_PROGRESS,GestureAction.MODIFY_VOLUME,GestureAction.MODIFY_BRIGHT})
@Retention(RetentionPolicy.SOURCE)
public @interface GestureAction {
    int MODIFY_NONE = 0;
    int MODIFY_PROGRESS = 1;
    int MODIFY_VOLUME = 2;
    int MODIFY_BRIGHT = 3;
}
