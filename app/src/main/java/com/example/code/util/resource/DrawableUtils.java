package com.example.code.util.resource;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * drawablexiang
 * Created by yummyLau on 2018/4/17.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class DrawableUtils {

    public static GradientDrawable getRectDrawable(Context context, @ColorRes int strokeColorId, int strokeWidth, @ColorRes int solidColorId) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setStroke(strokeWidth, ColorUtils.getColorInt(context, strokeColorId), 0, 0);
        gradientDrawable.setColor(ColorUtils.getColorInt(context, solidColorId));
        return gradientDrawable;
    }

    public static Drawable getRoundRectDrawable(Context context, @ColorRes int strokeColorId, int strokeWidth, @ColorRes int solidColorId, float radius, Point point) {
        GradientDrawable drawable = getRectDrawable(context, strokeColorId, strokeWidth, solidColorId);
        drawable.setCornerRadius(radius);
        if (point != null) {
            drawable.setSize(point.x, point.y);
        }
        return drawable;
    }

    public static Drawable getRoundRectDrawable(Context context, @ColorRes int strokeColorId, int strokeWidth, @ColorRes int solidColorId, float radius) {
        return getRoundRectDrawable(context, strokeColorId, strokeWidth, solidColorId, radius, null);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableId) {
        if (drawableId == 0) return null;
        return ContextCompat.getDrawable(context, drawableId);
    }

    public static void setBackground(View view, Drawable drawable) {
        if (view != null) {
            if (versionCompatible(Build.VERSION_CODES.JELLY_BEAN)) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }
    }

    private static boolean versionCompatible(int version) {
        return Build.VERSION.SDK_INT >= version;
    }
}
