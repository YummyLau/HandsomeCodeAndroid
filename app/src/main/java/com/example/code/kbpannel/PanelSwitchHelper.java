package com.example.code.kbpannel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理输入法切换
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public final class PanelSwitchHelper implements ViewTreeObserver.OnGlobalLayoutListener {

    /**
     * 保存横竖屏输入法高度
     **/
    public static final String SHARE_PREFERENCE_NAME = "KeyboardUtil_SP_NAME";
    public static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT_FOR_L = "soft_input_height_for_l";
    public static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT_FOR_P = "soft_input_height_for_p";
    public static final float DEFAULT_KEYBOARD_HEIGHT_FOR_L = 263.33f;
    public static final float DEFAULT_KEYBOARD_HEIGHT_FOR_P = 263.33f;
    /**
     * 标志、状态
     **/
    public static final int NONE_FLAG = -1;
    public static final int KEYBOARD_FLAG = 0;
    private static final long PROTEXT_CLICK_DURATION = 500;
    public static Pattern PATTERN = Pattern.compile("[\u4e00-\u9fa5]"); // 匹配中文
    private static long preClickTime = 0;
    /**
     * 布局相关
     **/
    private Activity activity;
    private View contentView;    //内容布局view，即表情布局以上部分，含编辑框
    private EditText editView;   //内容布局内的编辑框
    private View emptyView;      //空白区域，用于点击之后隐藏输入法或者表情布局
    private SparseArray<PanelItem> panelItemSparseArray = new SparseArray<>(); //面板布局

    /**
     * 业务变量
     **/
    private boolean isShowKeyboard;
    private int flag = NONE_FLAG;
    private SharedPreferences sp;
    private boolean userHide = false;
    private boolean onlyRequestFocus = false;

    /**
     * 接口声明
     **/
    private IOnKeyboardStateListener keyboardStateListener;
    private IOnPanelSwitchListener pannelSwitchListener;
    private IOnKeyClickListener keyClickListener;
    private IOnEditFocusChangeListener editFocusChangeListener;


    private PanelSwitchHelper(Builder builder) {
        this.activity = builder.innerActivity;
        this.contentView = builder.innerContentView;
        this.editView = builder.innerEditText;
        this.emptyView = builder.innerEmptyView;

        this.keyClickListener = builder.innerKeyClickListener;
        this.keyboardStateListener = builder.innerKeyboardStateListener;
        this.pannelSwitchListener = builder.innerPanelSwitchListener;
        this.editFocusChangeListener = builder.innerEditFocusChangeListener;

        this.panelItemSparseArray = builder.innerPanelArray;

        this.sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
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
                    if (flag == NONE_FLAG) {
                        showPanel(KEYBOARD_FLAG);
                    } else if (flag != KEYBOARD_FLAG) {
                        lockContentHeight(contentView);//显示输入法时，锁定内容高度，防止跳闪。
                        hidePanel(flag);
                        showPanel(KEYBOARD_FLAG);
                        unlockContentHeightDelayed(contentView); //输入法显示后，释放内容高度
                    }
                }
            });
            editView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (!onlyRequestFocus) {
                            showPanel(KEYBOARD_FLAG);
                        }
                    }
                    onlyRequestFocus = false;
                    if (editFocusChangeListener != null) {
                        editFocusChangeListener.onFocusChange(v, hasFocus);
                    }
                }
            });
        }
        if (emptyView != null) {
            emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (keyClickListener != null) {
                        keyClickListener.onKeyClick(v.getId());
                    }
                    if (pannelSwitchListener != null) {
                        pannelSwitchListener.onPanelSwitch(NONE_FLAG);
                    }
                    showPanel(NONE_FLAG);
                }
            });
        }

        for (int i = 0; i < panelItemSparseArray.size(); i++) {
            final PanelItem panelItem = panelItemSparseArray.get(panelItemSparseArray.keyAt(i));
            panelItem.getKeyView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (System.currentTimeMillis() - preClickTime <= PROTEXT_CLICK_DURATION) {
                        Log.d(getClass().getSimpleName(), "panelItem invalid click!");
                        Log.d(getClass().getSimpleName(), "panelItem preClickTime: " + preClickTime);
                        Log.d(getClass().getSimpleName(), "panelItem currentClickTime: " + System.currentTimeMillis());
                        return;
                    }

                    if (keyClickListener != null) {
                        keyClickListener.onKeyClick(v.getId());
                    }
                    /**
                     * 1. 如果当前没有任何面板显示，则显示keyview对应的panelview
                     * 2. 如果当前显示的是输入法，则显示keyview对应的panelView
                     * 3. 如果当前实现的是panelView
                     *  3.1 显示的panelView为即将显示的view，判断是否可二次点击返回输入法
                     *  3.2 反之，则隐藏当前panelView，显示目标View
                     */
                    switch (flag) {
                        case NONE_FLAG: {
                            showPanel(v.getId());
                            break;
                        }
                        case KEYBOARD_FLAG: {
                            lockContentHeight(contentView);
                            hidePanel(KEYBOARD_FLAG);
                            showPanel(v.getId());
                            unlockContentHeightDelayed(contentView);
                            break;
                        }
                        default: {
                            if (flag == v.getId()) {
                                if (panelItem.isSupportoggle() && ((View) panelItem.getPanelView()).isShown()) {
                                    lockContentHeight(contentView);
                                    hidePanel(flag);
                                    showPanel(KEYBOARD_FLAG);
                                    unlockContentHeightDelayed(contentView);
                                }
                            } else {
                                hidePanel(flag);
                                showPanel(v.getId());
                            }
                            break;
                        }
                    }
                    preClickTime = System.currentTimeMillis();
                }
            });
        }
    }

    public static int getLength(CharSequence s) {
        String string = s.toString();
        Matcher matcher = PATTERN.matcher(string);
        int chineseCount = 0;
        while (matcher.find()) {
            chineseCount++;
        }
        int chinesePunctuationCount = 0; // 中文标点
        for (int i = 0; i < s.length(); i++) {
            char singleChar = s.charAt(i);
//            if (StringUtils.isChinesePunctuation(singleChar)) {
//                chinesePunctuationCount++;
//            }
        }
        int allCount = string.length();
        int otherCount = allCount - chineseCount - chinesePunctuationCount;
        int charCount = chineseCount * 2 + chinesePunctuationCount * 2 + otherCount; // 中文和中文标点占两个字符，其他的占一个字符
        return charCount;
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
    private void showPanel(int flag) {
        switch (flag) {
            case NONE_FLAG: {            //回复到NONE_FLAG状态
                hidePanel(this.flag);
                this.flag = NONE_FLAG;
                break;
            }
            case KEYBOARD_FLAG: {
                editView.requestFocus();
                editView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PanelHelper.showKeyboard(activity, editView);
                        setEmptyViewVisible(true);
                    }
                }, 200);
                break;
            }
            default: {
                IPanelView panelView = panelItemSparseArray.get(flag).getPanelView();
                ((View) panelView).getLayoutParams().height = getKeyBoardHeight();
                if (panelView.isRechange()) {
                    panelView.doChange(-1, getKeyBoardHeight());
                }
                ((View) panelView).setVisibility(View.VISIBLE);
                setEmptyViewVisible(true);
            }
        }
        this.flag = flag;
        if (pannelSwitchListener != null) {
            pannelSwitchListener.onPanelSwitch(flag);
        }
    }

    /**
     * 隐藏面板，状态恢复到NONE_FLAG
     *
     * @param flag
     */
    private void hidePanel(int flag) {
        setEmptyViewVisible(false);
        switch (flag) {
            case NONE_FLAG: {
                break;
            }
            case KEYBOARD_FLAG: {
                userHide = true;
                PanelHelper.hideKeyboard(activity, editView);
                break;
            }
            default: {
                IPanelView panelView = panelItemSparseArray.get(flag).getPanelView();
                if (((View) panelView).isShown()) {
                    ((View) panelView).setVisibility(View.GONE);
                }
            }
        }
        this.flag = NONE_FLAG;
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

    public int getKeyBoardHeight() {
//        if (!PanelHelper.isPortrait(activity)) {
//            return sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT_FOR_L, DisplayUtils.dip2px(activity, DEFAULT_KEYBOARD_HEIGHT_FOR_L));
//        } else {
//            return sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT_FOR_P, DisplayUtils.dip2px(activity, DEFAULT_KEYBOARD_HEIGHT_FOR_P));
//        }
        return -1;
    }

    public void setKeyBoardHeight(int hetght) {
        if (!PanelHelper.isPortrait(activity)) {
            sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT_FOR_L, hetght).commit();
        } else {
            sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT_FOR_P, hetght).commit();
        }
    }

    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);            //不包含SystemUI高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();             //包含SystemUI高度
        int heightDiff = screenHeight - (r.bottom - r.top);

        int mStatusBarHeight = PanelHelper.getStatusbarHeight(activity);
        int mNavigationBatHeight = PanelHelper.getNavigationbarHeight(activity);

        int systemUIHeight = 0;
        if (PanelHelper.isPortrait(activity)) {
            systemUIHeight = PanelHelper.isNavigationBarShow(activity) ? mStatusBarHeight + mNavigationBatHeight : mStatusBarHeight;
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
                if (keyboardStateListener != null) {
                    keyboardStateListener.onKeyboardChange(false);
                }
            }
        } else {
            if (heightDiff > systemUIHeight) {
                isShowKeyboard = true;
                setKeyBoardHeight(keyboardHeight);
                if (keyboardStateListener != null) {
                    keyboardStateListener.onKeyboardChange(true);
                }
            }
        }
    }

    /**
     * 用户按下返回键是调用：
     * 1. 处理按下系统返回键隐藏键盘；
     * 2. 如果当前显示panelview且需要拦截返回事件，则需要在activity onBackPressed 调用handleSystemBack方法
     */
    public boolean handleSystemBack() {
        if (flag != NONE_FLAG) {
            //如果当前是输入法，则系统会默认隐藏，不需要触发主动隐藏输入法
            if (flag != KEYBOARD_FLAG) {
                hidePanel(flag);
            }
            if (pannelSwitchListener != null) {
                pannelSwitchListener.onPanelSwitch(NONE_FLAG);
            }
            setEmptyViewVisible(false);
            flag = NONE_FLAG;
            return true;
        }
        return false;
    }

    /**
     * 重置状态
     */
    public void resetNoneState() {
        if (flag != NONE_FLAG) {
            hidePanel(flag);
            if (pannelSwitchListener != null) {
                pannelSwitchListener.onPanelSwitch(NONE_FLAG);
            }
            setEmptyViewVisible(false);
            flag = NONE_FLAG;
        }
    }

    public static class Builder {

        private IOnKeyClickListener innerKeyClickListener;
        private IOnPanelSwitchListener innerPanelSwitchListener;
        private IOnKeyboardStateListener innerKeyboardStateListener;
        private IOnEditFocusChangeListener innerEditFocusChangeListener;

        private Activity innerActivity;
        private View innerContentView;
        private EditText innerEditText;
        private View innerEmptyView;
        private SparseArray<PanelItem> innerPanelArray;
        private int maxLength;

        public Builder(Activity activity) {
            this.innerActivity = activity;
            this.innerPanelArray = new SparseArray<>();
        }

        public Builder maxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
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
            if (!(panelView instanceof View)) {
                return this;
            }
            innerPanelArray.append(keyView.getId(), new PanelItem(keyView.getId(), keyView, panelView, toggleKeyboard));
            return this;
        }

        public Builder bindPanelItem(@NonNull View keyView, @NonNull IPanelView panelView) {
            return bindPanelItem(keyView, panelView, false);
        }

        public Builder bindKeyClickListener(IOnKeyClickListener listener) {
            this.innerKeyClickListener = listener;
            return this;
        }

        public Builder bindPanelSwitchListener(IOnPanelSwitchListener listener) {
            this.innerPanelSwitchListener = listener;
            return this;
        }

        public Builder bindKeyboardStateListener(IOnKeyboardStateListener listener) {
            this.innerKeyboardStateListener = listener;
            return this;
        }

        public Builder bindIEdittextFocesChangeListener(IOnEditFocusChangeListener listener) {
            this.innerEditFocusChangeListener = listener;
            return this;
        }

        public PanelSwitchHelper build(boolean showKeyboard) {
            final PanelSwitchHelper panelSwitchHelper = new PanelSwitchHelper(this);
            if (showKeyboard && panelSwitchHelper.editView != null) {
                panelSwitchHelper.editView.requestFocus();
            }

            // 增加文字字数控制
            if (panelSwitchHelper.editView != null && maxLength > 0) {
                try {
                    InputFilter[] inputFilters = panelSwitchHelper.editView.getFilters();
                    int size = inputFilters != null ? inputFilters.length : 0;
                    InputFilter[] newInputFilter = new InputFilter[size + 1];
                    if (size > 0) {
                        System.arraycopy(inputFilters, 0, newInputFilter, 0, size);
                    }
                    newInputFilter[size] = new InputFilter.LengthFilter(maxLength);
                    panelSwitchHelper.editView.setFilters(newInputFilter);
                } catch (Exception e) {
//                    LogUtils.error("", e.getMessage());
                }
            }

            return panelSwitchHelper;
        }

        public PanelSwitchHelper build() {
            return build(false);
        }

        public class LimitTextWatcher implements TextWatcher {
            private int maxLength;
            private Context appContext;
            private EditText editView;

            private CharSequence beforeText = "";
            private boolean overLength;

            public LimitTextWatcher(int maxLength, Context appContext, EditText editView) {
                this.maxLength = maxLength;
                this.appContext = appContext;
                this.editView = editView;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!overLength) {
                    if (s != null && s.length() > 0) {
                        beforeText = s.subSequence(0, s.length());
                    } else {
                        beforeText = "";
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && getLength(s) > maxLength) {
                    if (!overLength) {
//                        ToastUtils.showShort(appContext, "输入超过长度：" + maxLength);
                    }
                    overLength = true;
                    editView.removeTextChangedListener(this);
                    if (android.text.TextUtils.isEmpty(beforeText) || getLength(beforeText) < maxLength) {
                        beforeText = sumString(s, maxLength);
                    }
                    editView.setText(beforeText);
                    editView.setSelection(beforeText.length());
                    editView.addTextChangedListener(this);
                } else {
                    overLength = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            private String sumString(CharSequence originCharSequence, int length) {
                if (originCharSequence == null || originCharSequence.length() < 1) {
                    return "";
                }
                int count = 0;
                for (int i = 0; i < originCharSequence.length(); i++) {
                    CharSequence singleCharSequence = originCharSequence.subSequence(i, i + 1);
                    if (PanelSwitchHelper.PATTERN.matcher(singleCharSequence).matches()
                            ) { // 中文和标点符号占2个字符
//                        || StringUtils.isChinesePunctuation(singleCharSequence.charAt(0))
                        count += 2;
                    } else {
                        count++;
                    }
                    if (count == length) {
                        return originCharSequence.subSequence(0, i + 1).toString();
                    } else if (count > length) {
                        return originCharSequence.subSequence(0, i).toString();
                    }
                }
                return "";
            }

            public int getMaxLength() {
                return maxLength;
            }
        }
    }
}
