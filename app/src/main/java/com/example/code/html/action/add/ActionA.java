package com.example.code.html.action.add;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextPaint;
import android.text.style.URLSpan;

import com.example.code.html.Constants;
import com.example.code.html.HtmlParser;
import com.example.code.html.action.ActionType;
import com.example.code.html.action.TagAction;

import org.xml.sax.Attributes;

/**
 * a 标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ActionA extends TagAction {

    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startA(output, attributes);
        } else {
            endA(output);
        }
    }

    @Override
    public ActionType type() {
        return ActionType.ADD;
    }

    private static void startA(Editable text, Attributes attributes) {
        String href = attributes.getValue("", Constants.Attributes.A_HREF);
        start(text, new Href(href));
    }

    private static void endA(Editable text) {
        Href h = getLast(text, Href.class);
        if (h != null) {
            if (h.mHref != null) {
                setSpanFromMark(text, h, new MyUrlSpan((h.mHref)));
            }
        }
    }

    private static class MyUrlSpan extends URLSpan {

        public MyUrlSpan(String url) {
            super(url);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#00A5FE"));
            ds.setUnderlineText(false);
        }
    }

    private static class Href {
        public String mHref;

        public Href(String href) {
            mHref = href;
        }
    }
}
