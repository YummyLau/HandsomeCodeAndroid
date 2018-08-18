package com.effective.android.base.util.system;

import android.view.View;

/**
 * Created by yummyLau on 18-7-17
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class ViewUtils {

    public static void measure(View view) {
        if (view == null) {
            return;
        }
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    }
}
