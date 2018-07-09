package com.example.code.kbpanel.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.code.R;
import com.example.code.base.BaseActivity;
import com.example.code.databinding.ActivityKeyboardLayoutBinding;
import com.example.code.kbpanel.PanelSwitchHelper;

/**
 * 测试
 * Created by yummyLau on 18-7-07
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class KeyboardActivity extends BaseActivity<ActivityKeyboardLayoutBinding> {

    private PanelSwitchHelper helper;

    public static void start(Context context) {
        Intent intent = new Intent(context, KeyboardActivity.class);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.activity_keyboard_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        helper = new PanelSwitchHelper.Builder(this)
                .bindContentView(binding.contentView)
                .bindEmptyView(binding.emptyView)
                .bindEditText(binding.editText)
                .bindPanelItem(binding.redClick, binding.panelRed, true)
                .bindPanelItem(binding.greenClick, binding.panelGreen, true)
                .bindPanelItem(binding.blueClick, binding.panelBlue, true)
                .logTrack(true)
                .build();
    }

    @Override
    public void onBackPressed() {
        if (helper != null && helper.hookSystemBackForHindPanel()) {
            return;
        }
        super.onBackPressed();
    }
}
