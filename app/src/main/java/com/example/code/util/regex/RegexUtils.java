package com.example.code.util.regex;

import android.text.TextUtils;

/**
 * 正则工具类
 * Created by yummyLau on 17-4-24
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class RegexUtils {

    private static final String EMAIL_REGEX = "^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
    private static final String MOBILE_REGEX = "^1[0-9]{10}";
    private static final String PHONE_REGEX ="((0086)?|(086)?|(86)?|(0)?)1[23456789][0-9]{9}";

    public static boolean isEmail(String text) {
        return !TextUtils.isEmpty(text) && text.matches(EMAIL_REGEX);
    }

    public static boolean isMobile(String text) {
        return !TextUtils.isEmpty(text) && text.matches(MOBILE_REGEX);
    }

    public static boolean isPhoneNumber(String text){
        return !TextUtils.isEmpty(text) && text.matches(PHONE_REGEX);
    }
}
