package com.example.code.util.resource;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by yummyLau on 2018/4/17.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ColorUtils {

    @ColorInt
    public static int getColorInt(Context context, @ColorRes int colorId) {
        if (colorId == 0) return 0;
        return ContextCompat.getColor(context, colorId);
    }

}
