package com.example.code;

import android.support.annotation.NonNull;
import android.os.Bundle;

import com.effective.android.base.activity.BaseBindingActivity;
import com.effective.router.annotation.RouteNode;

@RouteNode(path = "/main",desc = "主app页面")
public class MainActivity extends BaseBindingActivity {

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.app_activity_main;
    }

    @Override
    protected int getThemeColor() {
        return R.color.app_colorPrimary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        throw new RuntimeException("");
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, FeatureListFragment.newInstance(), "").commit();
    }

}
