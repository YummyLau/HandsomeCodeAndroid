package com.example.code.view.expandableTextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.effective.android.base.activity.BaseActivity;
import com.example.code.R;
import com.example.code.databinding.ActivityExpandableTextLayoutBinding;
import com.example.code.view.textview.SpanTouchListenerCompat;

/**
 * 测试数据
 * Created by yummyLau on 2018/7/02.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ExpandableTextActivity extends BaseActivity<ActivityExpandableTextLayoutBinding> {

    public static void start(Context context) {
        Intent intent = new Intent(context, ExpandableTextActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.test1.setText(getString(R.string.expand_text), true, new Callback() {
            @Override
            public void onAction(int action) {

            }
        });
        binding.test1.setOnTouchListener(SpanTouchListenerCompat.getInstance());
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.activity_expandable_text_layout;
    }
}

