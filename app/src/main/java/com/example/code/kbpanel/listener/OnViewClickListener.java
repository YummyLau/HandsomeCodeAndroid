package com.example.code.kbpanel.listener;

import android.view.View;

/**
 * 用于解决内部setOnClick之后外部无法获取点击事件
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface OnViewClickListener {

    void onViewClick(View view);
}
