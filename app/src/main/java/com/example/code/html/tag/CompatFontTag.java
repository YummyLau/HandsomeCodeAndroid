package com.example.code.html.tag;

import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

import com.example.code.html.HtmlParser;

import org.xml.sax.Attributes;

import java.util.Stack;

/**
 * font标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class CompatFontTag implements TagAction {

    private Stack<Integer> startIndex = new Stack<>();
    private Stack<String> propertyValue = new Stack<>();

    @Override
    public void action(boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startIndex.push(output.length());
            propertyValue.push(HtmlParser.getValue(attributes, "size"));
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
