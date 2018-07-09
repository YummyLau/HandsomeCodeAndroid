package com.example.code.kbpanel;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.code.kbpanel.listener.OnEditFocusChangeListener;
import com.example.code.kbpanel.listener.OnKeyboardStateListener;
import com.example.code.kbpanel.listener.OnPanelChangeListener;
import com.example.code.kbpanel.listener.OnViewClickListener;
import com.example.code.kbpanel.panel.PanelItem;

/**
 * Created by yummyLau on 18-7-07
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class LogTracker implements OnEditFocusChangeListener, OnKeyboardStateListener, OnPanelChangeListener, OnViewClickListener {

    public boolean open;

    public LogTracker(boolean open) {
        this.open = open;
    }

    public void log(@NonNull String message) {
        if (message == null) {
            return;
        }
        if (open) {
            Log.d(Constants.LOG_TAG, message);
        }
    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        log("#onFocusChange : EditText has focus ( " + hasFocus + " )");
    }

    @Override
    public void onKeyboardChange(boolean show) {
        log("onKeyboardChange : Keyboard is showing ( " + show + " )");
    }

    @Override
    public void onPanelChange(boolean keyboardVisible, PanelItem panelItem) {
        log("onPanelChange : Keyboard is showing ( "
                + keyboardVisible + " ) IPanelView is " + (panelItem != null ? panelItem.getPanelName() : "null"));
    }

    @Override
    public void onViewClick(View view) {
        log("onViewClick : IPanelView is " + (view != null ? view.toString() : "null"));
    }
}
