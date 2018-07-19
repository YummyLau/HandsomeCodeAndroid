package com.example.code.keeplive.onepx;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 1像素activity
 * Created by yummyLau on 2018/7/19.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class KeepLiveActivity extends Activity{

    private static final String TAG = KeepLiveActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"start Keep app activity");
        Window window = getWindow();
        window.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = 1;
        attributes.height = 1;
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);
        KeepLiveManager.getInstance().setKeep(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"stop keep app activity");
    }
}
