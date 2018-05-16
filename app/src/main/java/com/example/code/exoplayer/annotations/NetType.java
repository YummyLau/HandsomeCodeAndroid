package com.example.code.exoplayer.annotations;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 视频感知的网络类型
 * Created by yummyLau on 2018/5/8.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@IntDef({NetType.NETWORK_WIFI, NetType.NETWORK_MOBILE, NetType.NETWORK_UNKNOWN})
@Retention(RetentionPolicy.SOURCE)
public @interface NetType {

    int NETWORK_UNKNOWN = -1;
    int NETWORK_WIFI = 0;
    int NETWORK_MOBILE = 1;
}
