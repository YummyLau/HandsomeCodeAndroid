package com.example.code.html.action.add;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.StyleSpan;

import com.example.code.html.HtmlParser;
import com.example.code.html.action.ActionType;
import com.example.code.html.action.TagAction;

import org.xml.sax.Attributes;

import java.util.Stack;

/**
 * 加粗标签
 * created by g8931 on 2018/6/25
 */
public class ActionStrong extends TagAction {

    private Stack<Integer> startIndex = new Stack<>();

    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startIndex.push(output.length());
        } else {
            output.setSpan(new StyleSpan(Typeface.BOLD), startIndex.pop(), output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    @Override
    public ActionType type() {
        return ActionType.ADD;
    }
}

