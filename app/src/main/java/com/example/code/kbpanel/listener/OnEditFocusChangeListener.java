package com.example.code.kbpanel.listener;

import android.view.View;

/**
 * 监听 {@link android.widget.EditText} 焦点变化
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public interface OnEditFocusChangeListener {

    void onFocusChange(View view, boolean hasFocus);
}
