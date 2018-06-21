package com.example.code.kbpannel;

import android.view.View;

/**
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.c
 */

public class PanelItem {

    private View mKeyView;
    private IPanelView mPanelView;
    private int mFlag;
    private boolean supportoggle;

    public PanelItem(int flag, View keyView, IPanelView panelView, boolean toggle) {
        mFlag = flag;
        mKeyView = keyView;
        mPanelView = panelView;
        supportoggle = toggle;
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

    public boolean isSupportoggle() {
        return supportoggle;
    }
}
