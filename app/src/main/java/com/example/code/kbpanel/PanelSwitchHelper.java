package com.example.code.kbpanel;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.code.kbpanel.listener.OnEditFocusChangeListener;
import com.example.code.kbpanel.listener.OnKeyboardStateListener;
import com.example.code.kbpanel.listener.OnPanelChangeListener;
import com.example.code.kbpanel.listener.OnViewClickListener;
import com.example.code.kbpanel.panel.IPanelView;
import com.example.code.kbpanel.panel.PanelItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理输入法切换
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public final class PanelSwitchHelper implements ViewTreeObserver.OnGlobalLayoutListener {


    private static final long PROTECT_CLICK_DURATION = 500;
    private static long preClickTime = 0;

    private Activity activity;
    private View contentView;                                       //内容布局view，即表情布局以上部分，含编辑框
    private EditText editView;                                      //内容布局内的编辑框
    private View emptyView;                                         //空白区域，用于点击之后隐藏输入法或者表情布局
    private final SparseArray<PanelItem> panelItemSparseArray;      //面板布局

    private boolean keyboardShowing;
    private int flag = Constants.FLAG_NONE;
    private boolean userHide = false;
    private boolean onlyRequestFocus = false;

    private boolean preventOpeningKeyboard = false;

    private final List<OnViewClickListener> viewClickListeners;
    private final List<OnPanelChangeListener> panelChangeListeners;
    private final List<OnKeyboardStateListener> keyboardStatusListeners;
    private final List<OnEditFocusChangeListener> editFocusChangeListeners;

    private PanelSwitchHelper(Builder builder) {

        activity = builder.innerActivity;
        contentView = builder.innerContentView;
        editView = builder.innerEditText;
        emptyView = builder.innerEmptyView;
        panelItemSparseArray = builder.innerPanelArray;

        LogTrackListener logTrackListener = new LogTrackListener();
        viewClickListeners = builder.innerViewClickListeners;
        panelChangeListeners = builder.innerPanelChangeListeners;
        keyboardStatusListeners = builder.innerKeyboardStatusListeners;
        editFocusChangeListeners = builder.innerEditFocusChangeListeners;
        viewClickListeners.add(logTrackListener);
        editFocusChangeListeners.add(logTrackListener);
        keyboardStatusListeners.add(logTrackListener);
        panelChangeListeners.add(logTrackListener);

        this.activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.activity.getWindow().getDecorView().getRootView().getViewTreeObserver().addOnGlobalLayoutListener(this);


        /**
         * 1. if current flag is None,should show keyboard
         * 2. current flag is not None or KeyBoard that means some panel is showing,hide it and show keyboard
         */
        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == Constants.FLAG_NONE) {
                    showPanelByFlag(Constants.FLAG_KEYBOARD);                   //show keyboard
                } else if (flag != Constants.FLAG_KEYBOARD) {
                    lockContentHeight(contentView);                             //before showing keyboard，should lock the contentHeight to prevent the beating
                    hidePanelByFlag(flag);
                    showPanelByFlag(Constants.FLAG_KEYBOARD);
                    unlockContentHeightDelayed(contentView);                    //unlock the contentHeight
                }
                notifyViewClick(v);
            }
        });

        /**
         * 1.
         */
        editView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!onlyRequestFocus) {
                        showPanelByFlag(Constants.FLAG_KEYBOARD);
                    }
                }
                onlyRequestFocus = false;
                notifyEditFocusChange(v, hasFocus);
            }
        });


        /**
         * the EmptyView will help you to hide contentView when click it
         * for example，when you chatting in pager and keyboard is showing， you click the EmptyView to hide keyboard
         */
        if (emptyView != null) {
            emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyViewClick(v);
                    notifyPanelChange(Constants.FLAG_NONE);
                    showPanelByFlag(Constants.FLAG_NONE);
                }
            });
        }

        /**
         * save panel that you want to use these to checkout
         */
        for (int i = 0; i < panelItemSparseArray.size(); i++) {
            final PanelItem panelItem = panelItemSparseArray.get(panelItemSparseArray.keyAt(i));
            panelItem.getKeyView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //protect click
                    if (System.currentTimeMillis() - preClickTime <= PROTECT_CLICK_DURATION) {
                        Log.d(Constants.LOG_TAG, "panelItem invalid click!");
                        Log.d(Constants.LOG_TAG, "panelItem preClickTime: " + preClickTime);
                        Log.d(Constants.LOG_TAG, "panelItem currentClickTime: " + System.currentTimeMillis());
                        return;
                    }


                    /**
                     * 1. if current flag is None，show panel which meets flag
                     * 2. if current flag is keyboard，hide it and show panel which meets flag
                     * 3. if current flag is one of panels(exclude keyboard)
                     *  3.1 the flag that will be set is the same as the current flag and support toggling, hide it and show keyboard
                     *  3.2 on the contrary,hide it and show other panel
                     */
                    switch (flag) {
                        case Constants.FLAG_NONE: {
                            showPanelByFlag(v.getId());
                            break;
                        }
                        case Constants.FLAG_KEYBOARD: {
                            lockContentHeight(contentView);
                            hidePanelByFlag(Constants.FLAG_KEYBOARD);
                            showPanelByFlag(v.getId());
                            unlockContentHeightDelayed(contentView);
                            break;
                        }
                        default: {
                            if (flag == v.getId()) {
                                if (panelItem.isToggle() && ((View) panelItem.getPanelView()).isShown()) {
                                    lockContentHeight(contentView);
                                    hidePanelByFlag(flag);
                                    showPanelByFlag(Constants.FLAG_KEYBOARD);
                                    unlockContentHeightDelayed(contentView);
                                }
                            } else {
                                hidePanelByFlag(flag);
                                showPanelByFlag(v.getId());
                            }
                            break;
                        }
                    }
                    preClickTime = System.currentTimeMillis();

                    notifyViewClick(v);
                }
            });
        }
    }


    public void requestFocus2(boolean preventOpeningKeyboard) {
        this.preventOpeningKeyboard = preventOpeningKeyboard;
        editView.requestFocus();
    }


    public void showKeyboardInitiatively() {
        if (editView.hasFocus()) {
            editView.performClick();
        } else {
            requestFocus2(false);
        }
    }

    /**
     * 显示pannel面板，状态恢复到flag，回调pannelSwitchListener方法
     *
     * @param flag
     */
    private void showPanelByFlag(int flag) {
        switch (flag) {
            case Constants.FLAG_NONE: {            //回复到NONE_FLAG状态
                hidePanelByFlag(this.flag);
                this.flag = Constants.FLAG_NONE;
                break;
            }
            case Constants.FLAG_KEYBOARD: {
                editView.requestFocus();
                editView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        KbPanelHelper.showKeyboard(activity, editView);
                        setEmptyViewVisible(true);
                    }
                }, 200);
                break;
            }
            default: {
                int keyboardHeight = KbPanelHelper.getKeyBoardHeight(activity);
                Log.d(Constants.LOG_TAG, "keyboard get height is : " + keyboardHeight);
                IPanelView panelView = panelItemSparseArray.get(flag).getPanelView();
                ((View) panelView).getLayoutParams().height = keyboardHeight;
                if (panelView.isRechange()) {
                    panelView.doChange(-1, KbPanelHelper.getKeyBoardHeight(activity));
                }
                ((View) panelView).setVisibility(View.VISIBLE);
                setEmptyViewVisible(true);
            }
        }
        this.flag = flag;
        notifyPanelChange(flag);
    }

    /**
     * 隐藏面板，状态恢复到NONE_FLAG
     *
     * @param flag
     */
    private void hidePanelByFlag(int flag) {
        setEmptyViewVisible(false);
        switch (flag) {
            case Constants.FLAG_NONE: {
                break;
            }
            case Constants.FLAG_KEYBOARD: {
                userHide = true;
                KbPanelHelper.hideKeyboard(activity, editView);
                break;
            }
            default: {
                IPanelView panelView = panelItemSparseArray.get(flag).getPanelView();
                if (((View) panelView).isShown()) {
                    ((View) panelView).setVisibility(View.GONE);
                }
            }
        }
        this.flag = Constants.FLAG_NONE;
    }

    private void lockContentHeight(@NonNull View contentView) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        params.height = contentView.getHeight();
        params.weight = 0.0F;
    }

    private void unlockContentHeightDelayed(@NonNull final View contentView) {
        contentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) contentView.getLayoutParams()).weight = 1.0F;
            }
        }, 500l);
    }

    private void setEmptyViewVisible(boolean isVisible) {
        if (emptyView != null) {
            emptyView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onGlobalLayout() {

        //get window height exclude SystemUi(statusBar and navigationBar)
        Rect r = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int contentHeight = r.bottom - r.top;

        //get statusBar 和 navigationBar height
        int systemUIHeight;
        int statusBarHeight = KbPanelHelper.getStatusBarHeight(activity);
        int navigationBatHeight = KbPanelHelper.getNavigationBarHeight(activity);
        if (KbPanelHelper.isPortrait(activity)) {
            systemUIHeight = KbPanelHelper.isNavigationBarShow(activity) ? statusBarHeight + navigationBatHeight : statusBarHeight;
        } else {
            systemUIHeight = statusBarHeight;
        }

        //get window height include SystemUi
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        int heightDiff = screenHeight - (contentHeight);

        //get keyboard height
        int keyboardHeight = 0;
        if (keyboardHeight == 0 && heightDiff > systemUIHeight) {
            keyboardHeight = heightDiff - systemUIHeight;
        }

        if (keyboardShowing) {
            //meet Hinding keyboard
            if (keyboardHeight <= 0) {
                if (userHide) {
                    userHide = false;
                } else {
                    hookSystemBackForHindPanel();
                }
                keyboardShowing = false;
                notifyKeyboardState(false);
            }
        } else {
            //meet Showing keyboard,
            if (keyboardHeight > 0) {
                Log.d(Constants.LOG_TAG, "keyboard set height is : " + keyboardHeight);
                KbPanelHelper.setKeyBoardHeight(activity, keyboardHeight);
                keyboardShowing = true;
                notifyKeyboardState(true);
            }
        }
    }

    /**
     * This will be called when User press System Back Button.
     * 1. if keyboard is showing, should be hide;
     * 2. if you want to hide panel(exclude keyboard),you should call it before {@link Activity#onBackPressed()} to hook it.
     */
    public boolean hookSystemBackForHindPanel() {
        if (flag != Constants.FLAG_NONE) {
            if (flag != Constants.FLAG_KEYBOARD) {
                hidePanelByFlag(flag);
            }
            notifyPanelChange(Constants.FLAG_NONE);
            setEmptyViewVisible(false);
            flag = Constants.FLAG_NONE;
            return true;
        }
        return false;
    }

    /**
     * 重置状态
     */
    public void resetNoneState() {
        if (flag != Constants.FLAG_NONE) {
            hidePanelByFlag(flag);
            setEmptyViewVisible(false);
            flag = Constants.FLAG_NONE;
            notifyPanelChange(Constants.FLAG_NONE);
        }
    }

    @Nullable
    private PanelItem getPanelItem(int flag) {
        return panelItemSparseArray.get(flag);
    }

    private void notifyViewClick(View view) {
        for (OnViewClickListener listener : viewClickListeners) {
            listener.onViewClick(view);
        }
    }

    private void notifyPanelChange(int flag) {
        switch (flag) {
            case Constants.FLAG_NONE: {
                notifyPanelChange(false, null);
                break;
            }
            case Constants.FLAG_KEYBOARD: {
                notifyPanelChange(true, null);
                break;
            }
            default: {
                notifyPanelChange(false, getPanelItem(flag));
            }
        }
    }

    private void notifyPanelChange(boolean keyboardVisible, PanelItem panelView) {
        for (OnPanelChangeListener listener : panelChangeListeners) {
            listener.onPanelChange(keyboardVisible, panelView);
        }
    }

    private void notifyKeyboardState(boolean showing) {
        for (OnKeyboardStateListener listener : keyboardStatusListeners) {
            listener.onKeyboardChange(showing);
        }
    }

    private void notifyEditFocusChange(View view, boolean hasFocus) {
        for (OnEditFocusChangeListener listener : editFocusChangeListeners) {
            listener.onFocusChange(view, hasFocus);
        }
    }

    /**
     * 用于构建切换面板
     * 必须设置： innerContentView  innerEditText
     * 选择添加： innerPanelArray
     */
    public static class Builder {

        private List<OnViewClickListener> innerViewClickListeners;
        private List<OnPanelChangeListener> innerPanelChangeListeners;
        private List<OnKeyboardStateListener> innerKeyboardStatusListeners;
        private List<OnEditFocusChangeListener> innerEditFocusChangeListeners;

        private Activity innerActivity;
        private View innerContentView;
        private EditText innerEditText;
        private View innerEmptyView;
        private SparseArray<PanelItem> innerPanelArray;

        public Builder(Activity activity) {
            this.innerActivity = activity;
            innerPanelArray = new SparseArray<>();
            innerViewClickListeners = new ArrayList<>();
            innerPanelChangeListeners = new ArrayList<>();
            innerKeyboardStatusListeners = new ArrayList<>();
            innerEditFocusChangeListeners = new ArrayList<>();
        }

        public Builder bindContentView(View contentView) {
            this.innerContentView = contentView;
            return this;
        }

        public Builder bindEditText(EditText editText) {
            this.innerEditText = editText;
            return this;
        }

        public Builder bindEmptyView(View emptyView) {
            this.innerEmptyView = emptyView;
            return this;
        }

        /**
         * 绑定切换布局
         *
         * @param keyView        触发切换panelView的布局
         * @param panelView      面板布局
         * @param toggleKeyboard true，则当面板为panelView时，再次点击keyView则切换回输入法
         */
        public Builder bindPanelItem(@NonNull View keyView, @NonNull IPanelView panelView, final boolean toggleKeyboard) {
            if (keyView == null) {
                throw new IllegalArgumentException("keyView is not a view!");
            }
            if (panelView == null || !(panelView instanceof IPanelView)) {
                throw new IllegalArgumentException("panelView is not a view!");
            }

            innerPanelArray.append(keyView.getId(), new PanelItem(keyView.getId(), keyView, panelView, toggleKeyboard));
            return this;
        }

        public Builder bindPanelItem(@NonNull View keyView, @NonNull IPanelView panelView) {
            return bindPanelItem(keyView, panelView, false);
        }

        public Builder addViewClickListener(OnViewClickListener listener) {
            if (listener != null) {
                innerViewClickListeners.add(listener);
            }
            return this;
        }

        public Builder addPanelChangeListener(OnPanelChangeListener listener) {
            if (listener != null) {
                innerPanelChangeListeners.add(listener);
            }
            return this;
        }

        public Builder addKeyboardStateListener(OnKeyboardStateListener listener) {
            if (listener != null) {
                innerKeyboardStatusListeners.add(listener);
            }
            return this;
        }

        public Builder addEdittextFocesChangeListener(OnEditFocusChangeListener listener) {
            if (listener != null) {
                innerEditFocusChangeListeners.add(listener);
            }
            return this;
        }

        public PanelSwitchHelper build(boolean showKeyboard) {
            if (innerActivity == null) {
                throw new IllegalArgumentException("PanelSwitchHelper$Builder#build : innerActivity can't be null!please set value by call #Builder");
            }

            if (innerContentView == null) {
                throw new IllegalArgumentException("PanelSwitchHelper$Builder#build : contentView can't be null,please set value by call #bindContentView");
            }

            if (innerEditText == null) {
                throw new IllegalArgumentException("PanelSwitchHelper$Builder#build : innerEditText can't be null!please set value by call #bindEditText");
            }

            final PanelSwitchHelper panelSwitchHelper = new PanelSwitchHelper(this);
            if (showKeyboard && panelSwitchHelper.editView != null) {
                panelSwitchHelper.editView.requestFocus();
            }
            return panelSwitchHelper;
        }

        public PanelSwitchHelper build() {
            return build(false);
        }
    }

}
