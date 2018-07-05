package com.example.code.kbpanel.listener;


import com.example.code.kbpanel.panel.PanelItem;

/**
 * 面板切换回调
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public interface OnPanelChangeListener {

    void onPanelChange(boolean keyboardVisible,PanelItem panelItem);
}
