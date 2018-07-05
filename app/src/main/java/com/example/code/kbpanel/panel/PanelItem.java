package com.example.code.kbpanel.panel;

import android.view.View;

/**
 * 面板项
 * Created by yummyLau on 2018/6/21.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class PanelItem {

    private View mKeyView;
    private IPanelView mPanelView;
    private int mFlag;
    private boolean toggle;

    public PanelItem(int flag, View keyView, IPanelView panelView, boolean toggle) {
        mFlag = flag;
        mKeyView = keyView;
        mPanelView = panelView;
        this.toggle = toggle;
    }

    public int getFlag() {
        return mFlag;
    }

    public View getKeyView() {
        return mKeyView;
    }

    public IPanelView getPanelView() {
        return mPanelView;
    }

    public boolean isToggle() {
        return toggle;
    }
}
