package com.example.code;


import com.example.spannotation.SharedPreferencesField;
import com.example.spannotation.SharedPreferencesFileName;

/**
 * Created by Administrator on 2018/6/24.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@SharedPreferencesFileName(key = "test_config")
public class Test {

    @SharedPreferencesField
    public String a;
}
