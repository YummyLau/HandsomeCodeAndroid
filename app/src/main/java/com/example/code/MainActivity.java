package com.example.code;

import android.support.annotation.NonNull;
import android.os.Bundle;

import com.effective.android.base.activity.BaseActivity;


public class MainActivity extends BaseActivity {

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, FeatureListFragment.newInstance(), "").commit();
    }

}
