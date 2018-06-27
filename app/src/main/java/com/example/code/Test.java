package com.example.code;


import example.com.apt_annotation.sp.SharedPreferencesField;
import example.com.apt_annotation.sp.SharedPreferencesFileName;

/**
 * Created by Administrator on 2018/6/24.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@SharedPreferencesFileName(name = "test_config")
public class Test {

    @SharedPreferencesField
    public String a;
}
