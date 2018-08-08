package com.example.code.exoplayer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.effective.android.base.activity.BaseActivity;
import com.example.code.R;
import com.example.code.databinding.ActivityExoLayoutBinding;

/**
 * 播放器测试类
 * Created by yummyLau on 2018/5/21.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ExoActivity extends BaseActivity<ActivityExoLayoutBinding> {

    public static void start(Context context) {
        Intent intent = new Intent(context, ExoActivity.class);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.activity_exo_layout;
    }
}
