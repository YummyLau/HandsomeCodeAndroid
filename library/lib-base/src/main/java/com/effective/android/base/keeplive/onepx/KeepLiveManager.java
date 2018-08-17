package com.effective.android.base.keeplive.onepx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * Created by yummyLau on 2018/7/19.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class KeepLiveManager {

    private static volatile KeepLiveManager sInstance;
    private WeakReference<Activity> mKeepAct;

    public static KeepLiveManager getInstance() {
        if (sInstance == null) {
            synchronized (KeepLiveManager.class) {
                if (sInstance == null) {
                    sInstance = new KeepLiveManager();
                }
            }
        }
        return sInstance;
    }

    public void startKeepLiveActivity(Context context) {
        Intent intent = new Intent(context, KeepLiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void finishKeepLiveActivity() {
        if (null != mKeepAct) {
            Activity activity = mKeepAct.get();
            if (null != activity) {
                activity.finish();
            }
            mKeepAct = null;
        }
    }

    public void setKeep(KeepLiveActivity keep) {
        mKeepAct = new WeakReference<Activity>(keep);
    }
}
