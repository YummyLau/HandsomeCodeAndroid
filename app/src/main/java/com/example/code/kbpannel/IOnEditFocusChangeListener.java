package com.example.code.kbpannel;

import android.view.View;

/**
 * 输入edittext焦点监听暴露
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public interface IOnEditFocusChangeListener {

    void onFocusChange(View view, boolean hasFocus);
}
