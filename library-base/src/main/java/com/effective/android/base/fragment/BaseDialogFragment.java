package com.effective.android.base.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.effective.android.base.activity.BaseBindingActivity;

/**
 *
 * 作为dialog，可以灵活显示，当做fragment成为activity一部分，或者弹出在activity之上
 * 且在屏幕旋转时传统的dialog无法保存数据，且activity销毁前不允许有对话框未关闭导致crash
 * Created by yummylau on 2018/8/12.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 * todo
 */

public abstract class BaseDialogFragment<DataBinding extends ViewDataBinding> extends DialogFragment {

    protected DataBinding binding;
    protected BaseBindingActivity attachActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachActivity = (BaseBindingActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        attachActivity = null;
    }

    @LayoutRes
    @NonNull
    public abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        } else {
            ((ViewGroup) binding.getRoot().getParent()).removeView(binding.getRoot());
        }
        return binding.getRoot();
    }
}
