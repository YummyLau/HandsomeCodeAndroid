package com.example.feature.a;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.effective.android.base.activity.BaseActivity;
import com.effective.router.annotation.Autowired;
import com.effective.router.annotation.RouteNode;
import com.effective.router.core.service.AutowiredService;


/**
 * Created by mrzhang on 2017/6/15.
 */
@RouteNode(path = "/test", desc = "测试页面")
public class TestActivity extends BaseActivity {

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.readerbook_activity_test;
    }

    @Autowired()
    String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutowiredService.Factory.getInstance().create().autowire(this);
        if(TextUtils.isEmpty(content)){
            content = "接收数据为null";
        }
        ((TextView)findViewById(R.id.content)).setText(content);
        ((TextView)findViewById(R.id.click)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
