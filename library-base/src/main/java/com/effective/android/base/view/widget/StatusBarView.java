package com.effective.android.base.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.effective.android.base.R;
import com.effective.android.base.util.system.ScreenUtils;

/**
 * 状态栏view
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class StatusBarView extends View {

    private int minVersionLimitVisible = Build.VERSION_CODES.KITKAT;

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView(AttributeSet attrs, int defStyle) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StatusBarView, defStyle, 0);
        if (typedArray != null) {
            minVersionLimitVisible = typedArray.getInt(R.styleable.StatusBarView_min_version, Build.VERSION_CODES.KITKAT);
        }
        int statusBarHeight = ScreenUtils.getStatusBarHeight(getContext());
        getLayoutParams().height = minVersionLimitVisible < Build.VERSION_CODES.KITKAT ? 0 : statusBarHeight;
    }
}
