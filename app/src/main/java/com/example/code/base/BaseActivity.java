package com.example.code.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * activity基类
 * Created by yummyLau on 2018/5/21.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    public View contentView;

    @LayoutRes
    @NonNull
    public abstract int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = LayoutInflater.from(this).inflate(getLayoutRes(), null, false);
        setContentView(contentView);
    }
}
