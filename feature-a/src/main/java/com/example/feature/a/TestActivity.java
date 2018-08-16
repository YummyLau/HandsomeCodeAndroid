package com.example.feature.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.effective.feature.a.R;
import com.effective.router.annotation.Autowired;
import com.effective.router.annotation.RouteNode;
import com.effective.router.core.Router;
import com.effective.router.core.service.AutowiredService;
import com.effective.router.core.ui.UIRouter;


/**
 * Created by mrzhang on 2017/6/15.
 */
@RouteNode(path = "/test", desc = "测试页面")
public class TestActivity extends AppCompatActivity {

    @Autowired()
    String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutowiredService.Factory.getInstance().create().autowire(this);
        setContentView(R.layout.readerbook_activity_test);
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
