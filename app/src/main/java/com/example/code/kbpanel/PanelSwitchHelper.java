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

    private boolean isShowKeyboard;
    private int flag = Constants.FLAG_NONE;
    private boolean userHide = false;
    private boolean onlyRequestFocus = false;

    private final List<OnViewClickListener> viewClickListeners;
    private final List<OnPanelChangeListener> panelChangeListeners;
    private final List<OnKeyboardStateListener> keyboardStatusListeners;
    private final List<OnEditFocusChangeListener> editFocusChangeListeners;

    private PanelSwitchHelper(Builder builder) {

        activity = builder.innerActivity;
        contentView = builder.innerContentView;
        editView = builder.innerEditText;
        emptyView = builder.innerEmptyView;

        LogTrackListener logTrackListener = new LogTrackListener();
        viewClickListeners = builder.innerViewClickListeners;
        panelChangeListeners = builder.innerPanelChangeListeners;
        keyboardStatusListeners = builder.innerKeyboardStatusListeners;
        editFocusChangeListeners = builder.innerEditFocusChangeListeners;

        viewClickListeners.add(logTrackListener);
        editFocusChangeListeners.add(logTrackListener);
        keyboardStatusListeners.add(logTrackListener);
        panelChangeListeners.add(logTrackListener);


        panelItemSparseArray = builder.innerPanelArray;
        this.activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.activity.getWindow().getDecorView().getRootView().getViewTreeObserver().addOnGlobalLayoutListener(this);

        if (editView != null) {
            /**
             * 1. 如果当前没有任何面板显示，则显示输入法;否则，则隐藏面板之后显示输入法
             * 2. 如果当前是NONE_FLAG，则不能不能锁住面板
             */
            editView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag == Constants.FLAG_NONE) {
                        checkoutPanelByFlag(Constants.FLAG_KEYBOARD);
                    } else if (flag != Constants.FLAG_KEYBOARD) {
                        lockContentHeight(contentView);//显示输入法时，锁定内容高度，防止跳闪。
                        hidePanelByFlag(flag);
                        checkoutPanelByFlag(Constants.FLAG_KEYBOARD);
                        unlockContentHeightDelayed(contentView); //输入法显示后，释放内容高度
                    }
                    notifyViewClick(v);
                }
            });
            editView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (!onlyRequestFocus) {
                            checkoutPanelByFlag(Constants.FLAG_KEYBOARD);
                        }
                    }
                    onlyRequestFocus = false;
                    notifyEditFocusChange(v, hasFocus);
                }
            });
        }

        //设置空白view，用户点击面板之上隐藏内容。举个栗子，处于输入状态下，点击聊天内容区域时隐藏输入框。
        if (emptyView != null) {
            emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyViewClick(v);
                    notifyPanelChange(Constants.FLAG_NONE);
                    checkoutPanelByFlag(Constants.FLAG_NONE);
                }
            });
        }

        //保存面板列表
        for (int i = 0; i < panelItemSparseArray.size(); i++) {
            final PanelItem panelItem = panelItemSparseArray.get(panelItemSparseArray.keyAt(i));
            panelItem.getKeyView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (System.currentTimeMillis() - preClickTime <= PROTECT_CLICK_DURATION) {
                        Log.d(getClass().getSimpleName(), "panelItem invalid click!");
                        Log.d(getClass().getSimpleName(), "panelItem preClickTime: " + preClickTime);
                        Log.d(getClass().getSimpleName(), "panelItem currentClickTime: " + System.currentTimeMillis());
                        return;
                    }

                    notifyViewClick(v);
                    /**
                     * 1. 如果当前没有任何面板显示，则显示keyview对应的panelview
                     * 2. 如果当前显示的是输入法，则显示keyview对应的panelView
                     * 3. 如果当前实现的是panelView
                     *  3.1 显示的panelView为即将显示的view，判断是否可二次点击返回输入法
                     *  3.2 反之，则隐藏当前panelView，显示目标View
                     */
                    switch (flag) {
                        case Constants.FLAG_NONE: {
                            checkoutPanelByFlag(v.getId());
                            break;
                        }
                        case Constants.FLAG_KEYBOARD: {
                            lockContentHeight(contentView);
                            hidePanelByFlag(Constants.FLAG_KEYBOARD);
                            checkoutPanelByFlag(v.getId());
                            unlockContentHeightDelayed(contentView);
                            break;
                        }
                        default: {
                            if (flag == v.getId()) {
                                if (panelItem.isToggle() && ((View) panelItem.getPanelView()).isShown()) {
                                    lockContentHeight(contentView);
                                    hidePanelByFlag(flag);
                                    checkoutPanelByFlag(Constants.FLAG_KEYBOARD);
                                    unlockContentHeightDelayed(contentView);
                                }
                            } else {
                                hidePanelByFlag(flag);
                                checkoutPanelByFlag(v.getId());
                            }
                            break;
                        }
                    }
                    preClickTime = System.currentTimeMillis();
                }
            });
        }
    }

    /**
     * 获取焦点并设置为输入法状态
     */
    public void requestFocusForEditView() {
        requestFocusForEditView(false);
    }

    /**
     * 单独只是获得焦点
     *
     * @param onlyRequestFocus
     */
    public void requestFocusForEditView(boolean onlyRequestFocus) {
        if (editView != null) {
            this.onlyRequestFocus = onlyRequestFocus;
            editView.requestFocus();
        }
    }

    public void showKeyboardByUserAction() {
        if (editView != null) {
            if (editView.hasFocus()) {
                editView.performClick();
            } else {
                requestFocusForEditView();
            }
        }
    }

    /**
     * 显示pannel面板，状态恢复到flag，回调pannelSwitchListener方法
     *
     * @param flag
     */
    private void checkoutPanelByFlag(int flag) {
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
                IPanelView panelView = panelItemSparseArray.get(flag).getPanelView();
                ((View) panelView).getLayoutParams().height = KbPanelHelper.getKeyBoardHeight(activity);
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
        }, 300L);
    }

    private void setEmptyViewVisible(boolean isVisible) {
        if (emptyView != null) {
            emptyView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);            //不包含SystemUI高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();             //包含SystemUI高度
        int heightDiff = screenHeight - (r.bottom - r.top);

        int mStatusBarHeight = KbPanelHelper.getStatusBarHeight(activity);
        int mNavigationBatHeight = KbPanelHelper.getNavigationBarHeight(activity);

        int systemUIHeight = 0;
        if (KbPanelHelper.isPortrait(activity)) {
            systemUIHeight = KbPanelHelper.isNavigationBarShow(activity) ? mStatusBarHeight + mNavigationBatHeight : mStatusBarHeight;
        } else {
            systemUIHeight = mStatusBarHeight;
        }

        int keyboardHeight = 0;
        if (keyboardHeight == 0 && heightDiff > systemUIHeight) {
            keyboardHeight = heightDiff - systemUIHeight;
        }

        if (isShowKeyboard) {
            if (heightDiff <= systemUIHeight) {
                if (userHide) {
                    userHide = false;
                } else {
                    handleSystemBack();
                }
                isShowKeyboard = false;
                notifyKeyboardState(false);
            }
        } else {
            if (heightDiff > systemUIHeight) {
                isShowKeyboard = true;
                KbPanelHelper.setKeyBoardHeight(activity, keyboardHeight);
                notifyKeyboardState(true);
            }
        }
    }

    /**
     * 用户按下返回键是调用：
     * 1. 处理按下系统返回键隐藏键盘；
     * 2. 如果当前显示panelview且需要拦截返回事件，则需要在activity onBackPressed 调用handleSystemBack方法
     */
    public boolean handleSystemBack() {
        if (flag != Constants.FLAG_NONE) {
            //如果当前是输入法，则系统会默认隐藏，不需要触发主动隐藏输入法
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
