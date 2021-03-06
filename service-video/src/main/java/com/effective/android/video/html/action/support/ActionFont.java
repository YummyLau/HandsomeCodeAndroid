package com.effective.android.video.html.action.support;

import android.text.Editable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import com.effective.android.video.html.Constants;
import com.effective.android.video.html.HtmlParser;
import com.effective.android.video.html.action.ActionType;
import com.effective.android.video.html.action.TagAction;

import org.xml.sax.Attributes;

import java.util.Stack;

/**
 * font标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ActionFont extends TagAction {

    private Stack<Integer> startIndex = new Stack<>();
    private Stack<String> propertyValue = new Stack<>();

    @Override
    public ActionType type() {
        return ActionType.SUPPORT;
    }

    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startIndex.push(output.length());
            propertyValue.push(HtmlParser.getValue(attributes, Constants.Attributes.FONT_SIZE));
        } else {
            if (!propertyValue.empty()) {
                try {
                    int value = Integer.parseInt(propertyValue.pop());
                    output.setSpan(new AbsoluteSizeSpan(value * 2, true), startIndex.pop(), output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
