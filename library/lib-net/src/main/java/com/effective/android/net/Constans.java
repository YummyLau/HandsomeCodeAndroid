package com.effective.android.net;


import java.nio.charset.Charset;

/**
 * Created by yummyLau on 2018/7/30.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class Constans {

    public static final Charset UTF8 = Charset.forName("UTF-8");
    public final static int MAX_AGE = 10;
    public final static int MAX_STALE = 60;

    public final static int READ_TIME_OUT = 30;
    public final static int WRITE_TIME_OUT = 30;
    public final static int CONNECT_TIME_OUT = 15;

    public final static int READ_TIME_OUT_FAST = 5;
    public final static int WRITE_TIME_OUT_FAST = 5;
    public final static int CONNECT_TIME_OUT_FAST = 5;
}
