package com.example.code.kbpanel.listener;

import android.view.View;

/**
 * preventing listeners that {@link com.example.code.kbpanel.PanelSwitchHelper} set these to view from being overwritten
 * Created by yummyLau on 18-7-07
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface OnViewClickListener {

    void onViewClick(View view);
}
