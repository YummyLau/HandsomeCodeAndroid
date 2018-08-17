package com.effective.android.base.util.system;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.view.Window;
import android.view.WindowManager;


/**
 * 亮度管理
 * https://developer.android.com/reference/android/provider/Settings.System.html
 * 写入需要 <uses-permission android:name="android.permission.WRITE_SETTINGS"/> 权限
 * 关于window亮度说明
 * {@link WindowManager.LayoutParams#BRIGHTNESS_OVERRIDE_NONE}  默认使用System，当前window未覆盖
 * {@link WindowManager.LayoutParams#BRIGHTNESS_OVERRIDE_OFF}   黑
 * {@link WindowManager.LayoutParams#BRIGHTNESS_OVERRIDE_FULL}  亮
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class BrightnessSettingUtils {


    @IntDef({Mode.AUTOMATIC, Mode.MANUAL})
    public @interface Mode {
        int AUTOMATIC = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        int MANUAL = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
    }

    public static boolean isScreenAutomaticMode(ContentResolver aContentResolver) {
        boolean automatic = false;
        try {
            automatic = Settings.System.getInt(aContentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE)
                    == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automatic;
    }

    private static void setScreenBrightnessMode(Activity activity, @Mode int mode) {
        ContentResolver contentResolver = activity.getContentResolver();
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
    }

    public static void startScreenBrightnessAutomatic(Activity activity) {
        setScreenBrightnessMode(activity, Mode.AUTOMATIC);
    }

    public static void startScreenBrightnessManual(Activity activity) {
        setScreenBrightnessMode(activity, Mode.MANUAL);
    }

    public static int getScreenBrightness(Activity activity) {
        return getScreenBrightness(activity, 125);
    }

    public static int getScreenBrightness(Activity activity, @IntRange(from = 0, to = 255) int def) {
        ContentResolver contentResolver = activity.getContentResolver();
        return Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, def);
    }

    public static void setWindowBrightness(Activity activity, @IntRange(from = 0, to = 255) int brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }

    public static void setWindowBrightness(Activity activity, float brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness < 0) {
            brightness = 0f;
        } else if (brightness > 1) {
            brightness = 1;
        }
        lp.screenBrightness = brightness;
        window.setAttributes(lp);
    }

    public static float getWindowBrightness(Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        return lp.screenBrightness;
    }
}
