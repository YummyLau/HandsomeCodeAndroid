package com.example.code.keeplive.notification;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 8.0以下尝试用前台服务降低优先级，但是7.0以下notification才不会提示，7.0会在通知栏显示isRunning
 * Created by yummyLau on 2018/7/19.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class KeepLiveService extends Service {

    private static final String TAG = KeepLiveService.class.getSimpleName();
    private static int notificationId = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int result = super.onStartCommand(intent, flags, startId);

        if (Build.VERSION.SDK_INT >= 26) {
            Log.i(TAG, "for system version >= Android O, we just ignore increasingPriority "
                    + "job to avoid crash or toasts.");
            return result;
        }

        if ("ZUK".equals(Build.MANUFACTURER)) {
            Log.i(TAG, "for ZUK device, we just ignore increasingPriority "
                    + "job to avoid crash.");
            return result;
        }

        Log.i(TAG, "try to increase process priority");

        try {
            Notification notification = new Notification();
            if (Build.VERSION.SDK_INT < 18) {
                startForeground(notificationId, notification);
            } else {
                startForeground(notificationId, notification);
                startService(new Intent(this, InnerService.class));
            }
        } catch (Throwable e) {
            Log.i(TAG, "try to increase patch process priority error:" + e);
        }

        return result;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * start a service to keep alive!
     */
    public static class InnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
            super.onCreate();
            try {
                startForeground(notificationId, new Notification());
            } catch (Throwable e) {
                Log.e(TAG, "InnerService set service for push exception:%s.", e);
            }
            // kill
            stopSelf();
        }

        @Override
        public void onDestroy() {
            stopForeground(true);
            super.onDestroy();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
