package com.effective.android.base.util;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by yummyLau on 2018/4/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class LogUtils {

    private static boolean sDebug = true;

    public static final void debug(boolean open) {
        sDebug = open;
    }


    public static final void v(String tag, String msg) {
        if (sDebug) {
            Log.v(tag, msg);
        }
    }

    public static final void v(String tag, String msg, Throwable thr) {
        if (sDebug) {
            Log.v(tag, msg, thr);
        }
    }

    public static final void d(@NonNull Class c, String msg) {
        if (sDebug) {
            Log.d(c.getSimpleName(), msg);
        }
    }


    public static final void d(String tag, String msg) {
        if (sDebug) {
            Log.d(tag, msg);
        }
    }

    public static final void d(String tag, String msg, Throwable thr) {
        if (sDebug) {
            Log.d(tag, msg, thr);
        }
    }


    public static final void i(String tag, String msg) {
        if (sDebug) {
            Log.i(tag, msg);
        }
    }

    public static final void i(String tag, String msg, Throwable thr) {
        if (sDebug) {
            Log.i(tag, msg, thr);
        }
    }


    public static final void w(String tag, String msg) {
        if (sDebug) {
            Log.w(tag, msg);
        }
    }

    public static final void w(String tag, Throwable thr) {
        if (sDebug) {
            Log.w(tag, thr);
        }
    }

    public static final void w(String tag, String msg, Throwable thr) {
        if (sDebug) {
            Log.w(tag, msg, thr);
        }
    }

    public static final void e(String tag, String msg) {
        if (sDebug) {
            Log.e(tag, msg);
        }
    }

    public static final void e(String tag, String msg, Throwable thr) {
        if (sDebug) {
            Log.e(tag, msg, thr);
        }
    }
}
