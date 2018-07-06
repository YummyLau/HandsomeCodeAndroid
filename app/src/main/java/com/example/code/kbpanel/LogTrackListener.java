package com.example.code.kbpanel;

import android.util.Log;
import android.view.View;

import com.example.code.kbpanel.listener.OnEditFocusChangeListener;
import com.example.code.kbpanel.listener.OnKeyboardStateListener;
import com.example.code.kbpanel.listener.OnPanelChangeListener;
import com.example.code.kbpanel.listener.OnViewClickListener;
import com.example.code.kbpanel.panel.PanelItem;

/**
 * log 追中事件变化
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class LogTrackListener implements OnEditFocusChangeListener, OnKeyboardStateListener, OnPanelChangeListener, OnViewClickListener {

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        Log.d(Constants.LOG_TAG, "#onFocusChange : EditText has focus ( " + hasFocus + " )");
    }

    @Override
    public void onKeyboardChange(boolean show) {
        Log.d(Constants.LOG_TAG, "onKeyboardChange : Keyboard is showing ( " + show + " )");
    }

    @Override
    public void onPanelChange(boolean keyboardVisible, PanelItem panelItem) {
        Log.d(Constants.LOG_TAG, "onPanelChange : Keyboard is showing ( "
                + keyboardVisible + " ) IPanelView is " + (panelItem != null ? panelItem.toString() : "null"));
    }

    @Override
    public void onViewClick(View view) {
        Log.d(Constants.LOG_TAG, "onViewClick : IPanelView is " + (view != null ? view.toString() : "null"));
    }
}
