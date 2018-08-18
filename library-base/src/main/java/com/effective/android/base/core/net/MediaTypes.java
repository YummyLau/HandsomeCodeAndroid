package com.effective.android.base.core.net;

import okhttp3.MediaType;

/**
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class MediaTypes {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType FILE
            = MediaType.parse("application/octet-stream");

    public static final MediaType STREAM =
            MediaType.parse("application/octet-stream");

    public static final MediaType TEXT = MediaType.parse("text/plain");

}
