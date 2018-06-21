package com.example.code.kbpannel;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘助手
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public final class PanelHelper {

    /**
     * 检测状态栏导航栏高度
     **/
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAVIGATION_BAR_HEIGHT_RES_NAME = "navigation_bar_height";


    public static void showKeyboard(Context context, View view) {
        InputMethodManager mInputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        mInputManager.showSoftInput(view, 0);
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager mInputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int getStatusbarHeight(Context context) {
        return getInternalDimensionSize(context.getResources(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    public static int getNavigationbarHeight(Context context) {
        return getInternalDimensionSize(context.getResources(), NAVIGATION_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @TargetApi(14)
    public static boolean isNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !(menu || back);
        }
    }
}
