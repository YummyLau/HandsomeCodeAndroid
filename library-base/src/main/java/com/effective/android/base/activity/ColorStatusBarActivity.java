package com.effective.android.base.activity;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.effective.android.base.R;
import com.effective.android.base.util.system.StatusBarUtils;

/**
 * 状态栏着色activity
 * Created by yummyLau on 2018/5/21.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ColorStatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.transparent(this);
    }

    @ColorRes
    public int getColorPrimaryRes() {
        return R.color.colorPrimary;
    }

    @ColorInt
    public int getColorPromaryInt() {
        return ContextCompat.getColor(this, R.color.colorPrimary);
    }
}
