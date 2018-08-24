package com.effective.android.base.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * activity基类
 * Created by yummyLau on 2018/5/21.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public abstract class BaseBindingActivity<DataBinding extends ViewDataBinding> extends ColorStatusBarActivity {

    protected DataBinding binding;
    protected View contentView;

    @LayoutRes
    @NonNull
    public abstract int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        contentView = binding.getRoot();
    }
}
