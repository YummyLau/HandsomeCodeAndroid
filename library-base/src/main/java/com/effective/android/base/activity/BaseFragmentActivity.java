package com.effective.android.base.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.effective.android.base.fragment.BaseFragment;

/**
 * Created by yummyLau on 2018/8/12.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public abstract class BaseFragmentActivity<DataBinding extends ViewDataBinding> extends AppCompatActivity {

    protected DataBinding binding;
    protected View contentView;

    @LayoutRes
    @NonNull
    protected abstract int getLayoutRes();

    @IdRes
    @NonNull
    protected abstract int getFragmentContentId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        contentView = binding.getRoot();
        if (findViewById(getFragmentContentId()) == null
                || !(findViewById(getFragmentContentId()) instanceof ViewGroup)) {
            throw new RuntimeException("#getFragmentContentId get an invalid Id !");
        }
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}