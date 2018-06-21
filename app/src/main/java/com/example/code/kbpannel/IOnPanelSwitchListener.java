package com.example.code.kbpannel;


/**
 * 面板切换回调
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public interface IOnPanelSwitchListener {

    /**
     * 绑定的panelview flag为keyViewId
     * 输入法面板，flag为 KEYBOARD_FLAG
     * 默认不显示面板，则flag为 NONE_FALG
     *
     * @param panelFlag
     */
    void onPanelSwitch(int panelFlag);
}
