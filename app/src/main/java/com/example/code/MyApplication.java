package com.example.code;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.effective.android.component.ComponentManager;
import com.effective.android.crash.CrashComponentImpl;
import com.effective.android.net.okhttp.HttpClient;

/**
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class MyApplication extends MultiDexApplication {

    private static MyApplication application;

    public static MyApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        HttpClient.init(this, BuildConfig.DEBUG);

        //组件绑定
        ComponentManager.bind(this, CrashComponentImpl.class);

        //模块添加
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
