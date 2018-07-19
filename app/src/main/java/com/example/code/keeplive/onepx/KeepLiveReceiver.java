package com.example.code.keeplive.onepx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

/**
 * 监听屏幕点亮和关闭
 * Created by yummyLau on 2018/7/19.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class KeepLiveReceiver extends BroadcastReceiver {
    private static final String TAG = KeepLiveReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e(TAG, "receive action:" + action);
        if (TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {
            KeepLiveManager.getInstance().startKeepLiveActivity(context);
        } else if (TextUtils.equals(action, Intent.ACTION_USER_PRESENT)) {
            KeepLiveManager.getInstance().finishKeepLiveActivity();
        }
    }
}
