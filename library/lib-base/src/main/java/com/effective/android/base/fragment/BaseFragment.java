package com.effective.android.base.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.effective.android.base.activity.BaseBindingActivity;

/**
 * onAttach
 * onCreate
 * onCreateView
 * onActivityCreate
 * <p>
 * onStart
 * <p>
 * onResume
 * <p>
 * onPause
 * <p>
 * onStop
 * <p>
 * onDestroyView
 * onDestroy
 * onDetach
 * <p>
 * 1. 在activity#onCreate中创建fragment，需要考虑是否activity重启，所以需要判断savedInstanceState是否为null
 * 2. 如果启动另一个fragment并想获取返回的数据，则可以使用 setTargetFragment，重写 onActivityResult 获取
 * <p>
 * https://blog.csdn.net/lmj623565791/article/details/42628537
 * <p>
 * Created by yummylau on 2018/8/12.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public abstract class BaseFragment<DataBinding extends ViewDataBinding> extends Fragment {

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
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        } else {
            ((ViewGroup) binding.getRoot().getParent()).removeView(binding.getRoot());
        }
        return binding.getRoot();
    }
}
