package com.example.code.util.system;

import android.app.ActivityManager;
import android.content.Context;
import android.os.PowerManager;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.List;

/**
 * 系统级判断
 * Created by yummyLau on 2018/4/17.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.co
 */
public class SystemUtils {

    /**
     * 判断app是否在前台
     *
     * @param context
     * @return
     */
    public static final boolean isAppOnForeground(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> list = manager
                .getRunningAppProcesses();
        if (list == null)
            return false;
        boolean ret = false;
        Iterator<ActivityManager.RunningAppProcessInfo> it = list.iterator();
        while (it.hasNext()) {
            ActivityManager.RunningAppProcessInfo appInfo = it.next();
            if (appInfo.processName.equals(packageName)
                    && appInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 判断service是否在运行
     * @param context
     * @param name
     * @return
     */
    public static boolean isRunningService(Context context, String name) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : runningServices) {
            if (TextUtils.equals(info.service.getClassName(), name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断屏幕是否亮着
     *
     * @param context
     * @return
     */
    public static final boolean isScreenOn(Context context) {
        PowerManager powerManager = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        return powerManager.isScreenOn();
    }
}
