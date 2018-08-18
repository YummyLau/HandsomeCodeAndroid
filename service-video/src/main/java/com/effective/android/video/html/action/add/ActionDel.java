package com.effective.android.video.html.action.add;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

import com.effective.android.video.html.HtmlParser;
import com.effective.android.video.html.action.ActionType;
import com.effective.android.video.html.action.TagAction;

import org.xml.sax.Attributes;

import java.util.Stack;

/**
 * 删除标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ActionDel extends TagAction {

    private Stack<Integer> startIndex = new Stack<>();

    @Override
    public ActionType type() {
        return ActionType.ADD;
    }

    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startIndex.push(output.length());
        } else {
            output.setSpan(new StrikethroughSpan(), startIndex.pop(), output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
