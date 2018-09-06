package com.example.code;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.effective.android.base.activity.BaseBindingActivity;
import com.effective.router.annotation.Autowired;
import com.effective.router.annotation.RouteNode;
import com.effective.router.core.service.AutowiredService;

@RouteNode(path = "/second", desc = "第二个页面")
public class SecondActivity extends BaseBindingActivity {

    private static final String TAG = SecondActivity.class.getSimpleName();

    @Autowired()
    String content;

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.app_activity_second;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutowiredService.Factory.getInstance().create().autowire(this);
        Log.d(TAG, "this is SecondActivity!");
        if (TextUtils.isEmpty(content)) {
            content = "null";
        }
        Log.d(TAG, "从其他模块接收到的数据： " + content);
    }
}
