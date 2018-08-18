package com.example.router;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.effective.android.base.activity.BaseActivity;
import com.effective.router.annotation.Autowired;
import com.effective.router.annotation.RouteNode;
import com.effective.router.core.service.AutowiredService;
import com.effective.router.core.ui.UIRouter;


/**
 * Created by mrzhang on 2017/6/15.
 */
@RouteNode(path = "/test", desc = "测试页面")
public class RouterActivity extends BaseActivity {

    private static final String TAG = RouterActivity.class.getSimpleName();

    private TextView finishTv;
    private TextView backTv;

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.fr_activity_feature_router;
    }

    @Autowired()
    String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutowiredService.Factory.getInstance().create().autowire(this);
        finishTv = findViewById(R.id.finish);
        backTv = findViewById(R.id.back_to_other_activity);
        if (TextUtils.isEmpty(content)) {
            content = "null";
        }
        Log.d(TAG, "从其他模块接收到的数据： " + content);

        finishTv.setOnClickListener((View v) -> {
            finish();
        });

        backTv.setOnClickListener((View v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("content", "this content to a from router");
            UIRouter.getInstance().openUri(this, "HandsomeCodeAndroid://app/second", bundle);

        });
    }
}
