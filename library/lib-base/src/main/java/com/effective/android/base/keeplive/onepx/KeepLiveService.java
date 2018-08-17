package com.effective.android.base.keeplive.onepx;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * 监听锁屏等时间
 * Created by yummyLau on 2018/7/19.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class KeepLiveService extends Service {

    private static final String TAG = KeepLiveService.class.getSimpleName();

    KeepLiveReceiver keepLiveReceiver;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "start KeepLiveService !");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(keepLiveReceiver, filter);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(keepLiveReceiver);
        super.onDestroy();
    }


    public static class KeepLiveReceiver extends BroadcastReceiver {
        private static final String TAG = com.effective.android.base.keeplive.onepx.KeepLiveReceiver.class.getSimpleName();

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
}
