package com.example.code.exoplayer.list.videoVerticalPager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class VerticalViewPagerCompat extends VerticalViewPager {

    private boolean disableScroll;

    public VerticalViewPagerCompat(Context context) {
        super(context);
    }

    public VerticalViewPagerCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void enableScroll() {
        disableScroll = false;
    }

    public void disableScroll() {
        disableScroll = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (disableScroll) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (disableScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
