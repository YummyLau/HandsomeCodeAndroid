package com.effective.android.base.view.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * http://codethink.me/2015/04/23/improving-comment-rendering-on-android/
 * 协助构建 staticlayout
 * Created by yummyLau on 2018/11/29.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class StaticLayoutHelper {

    @Nullable
    public static StaticLayout getStaticLayout(@NonNull TextPaint textPaint, int width, Layout.Alignment alignment, CharSequence content) {
        if (textPaint == null) {
            return null;
        }
        if (alignment == null) {
            alignment = Layout.Alignment.ALIGN_NORMAL;
        }
        StaticLayout longStringLayout = new StaticLayout(content, textPaint, width, alignment, 1.0f, 0f, true);
        Canvas dummyCanvas = new Canvas();
        longStringLayout.draw(dummyCanvas);
        return longStringLayout;
    }
}
