package com.example.code.kbpannel;

/**
 * 面板接口
 * Created by yummyLau on 2018/6/821.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public interface IPanelView {

    /**
     * 是否在每次切换都需要改变view
     *
     * @return
     */
    boolean isRechange();

    /**
     * 每次切换是回调输入法宽高
     *
     * @param width
     * @param height
     */
    void doChange(int width, int height);
}
