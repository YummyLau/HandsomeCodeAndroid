package com.effective.android.video.html.action.add;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;

import com.effective.android.video.html.HtmlParser;
import com.effective.android.video.html.action.ActionType;
import com.effective.android.video.html.action.TagAction;

import org.xml.sax.Attributes;

import java.util.Stack;


/**
 * 水平分割线
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ActionHr extends TagAction {

    private Stack<Integer> startIndex = new Stack<>();

    @Override
    public ActionType type() {
        return ActionType.ADD;
    }

    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startIndex.push(output.length());
            appendNewlines(output, 2);
            start(output, new Newline(2));
            start(output, new Alignment(Layout.Alignment.ALIGN_CENTER));
            output.append("•  •  •  •  •  •");
        } else {
            int start = startIndex.pop();
            output.setSpan(new ForegroundColorSpan(Color.parseColor("#989ABF")), start, output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            output.setSpan(new RelativeSizeSpan(0.5f), start, output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            endBlockElement(output);
        }
    }
}
