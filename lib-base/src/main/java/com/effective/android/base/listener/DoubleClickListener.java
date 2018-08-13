package com.effective.android.base.listener;

import android.os.SystemClock;
import android.view.View;

/**
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public abstract class DoubleClickListener implements View.OnClickListener {

    long[] HITS = new long[2];

    @Override
    public void onClick(View v) {
        System.arraycopy(HITS, 1, HITS, 0, HITS.length - 1);
        HITS[HITS.length - 1] = SystemClock.uptimeMillis();
        long currentTime = SystemClock.uptimeMillis();
        if (HITS[0] >= (currentTime - 500)) {
            onDoubleClick(v);
        }
    }

    public abstract void onDoubleClick(View v);

}
