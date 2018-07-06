package com.example.code.kbpanel.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.code.kbpanel.Constants;
import com.example.code.kbpanel.panel.IPanelView;

/**
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class TestPanel extends FrameLayout implements IPanelView {

    public TestPanel(@NonNull Context context) {
        super(context);
    }

    public TestPanel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestPanel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isRechange() {
        return true;
    }

    @Override
    public void doChange(int width, int height) {
        Log.d(Constants.LOG_TAG, "panel height is : " + getLayoutParams().height);
        getLayoutParams().height = height;
    }
}
