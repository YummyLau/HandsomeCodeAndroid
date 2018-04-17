package com.example.code.util.system;

import android.os.Build;

/**
 * 设备相关的信息处理
 * Created by yummyLau on 2018/4/17.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class DeviceUtils {

    public static final String getOS() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static final String getPhoneModelWithManufacturer() {
        return Build.MANUFACTURER + " " + android.os.Build.MODEL;
    }

    public static final String getPhoneMode() {
        return Build.MODEL;
    }


    /**
     * 判断是否是小米
     * @return
     */
    public static boolean isMiui(){
        return Build.MANUFACTURER.contains("Xiaomi");
    }
}
