package com.example.code.util.system;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

/**
 * 屏幕相关工具类
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class ScreenUtils {

    private static final String TAG = ScreenUtils.class.getSimpleName();
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAVIGATION_BAR_HEIGHT_RES_NAME = "navigation_bar_height";


    /**
     * 在{@link Activity#onCreate(Bundle)}中 setContentView 之前调用
     *
     * @param activity
     */
    public static void removeTitleBar(@NonNull Activity activity) {
        if (activity != null) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }


    /**
     * 判断当前是否是竖屏
     *
     * @param context
     * @return
     */
    public static boolean isPortrait(@NonNull Context context) {
        if (context == null)
            throw new IllegalArgumentException("context can't be null!");
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }


    /**
     * 是否有导航栏显示
     *
     * @param context
     * @return
     */
    public static boolean isNavigationBarShow(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);              //删减了系统decor elements，比如虚拟导航栏
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }

        }
    }


    /**
     * 选择性获取状态栏、导航栏
     *
     * @param res context.getResources()
     * @param key NAVIGATION_BAR_HEIGHT_RES_NAME 或 STATUS_BAR_HEIGHT_RES_NAME
     * @return
     */

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getStatusBarHeight(@NonNull Context context) {
        return getInternalDimensionSize(context.getResources(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    public static int getNavigationHeight(@NonNull Context context) {
        return getInternalDimensionSize(context.getResources(), NAVIGATION_BAR_HEIGHT_RES_NAME);
    }

    /**
     * 获取屏幕可操作区域大小，包含状态栏，标题栏高度
     *
     * @param context
     * @return
     */
    public static Point getDisplaySize(@NonNull Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }


    /**
     * 获取屏幕区域大小，包括系统装饰布局，比如导航栏
     *
     * @param context
     * @return
     */
    public static Point getScreenSize(@NonNull Context context) {
        Point point = new Point();
        try {
            final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                    Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Display.class.getMethod("getRealSize", Point.class).invoke(display, point);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(point);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return point;
    }


    /**
     * 获取屏幕物理尺寸
     *
     * @param context
     * @return 屏幕物理尺寸
     */
    public static float getScreenInch(Context context) {
        Point realSize = getScreenSize(context);
        return (float) Math.sqrt(
                Math.pow((float) realSize.x / DisplayUtils.getWidthPpi(context), 2) +
                        Math.pow((float) realSize.y / DisplayUtils.getWidthPpi(context), 2));
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();

        Point size = getDisplaySize(activity);

        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, size.x, size.y);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();

        //不包括状态栏
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        Point size = getDisplaySize(activity);
        int statusBarHeight = frame.top;

        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, size.x, size.y - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
