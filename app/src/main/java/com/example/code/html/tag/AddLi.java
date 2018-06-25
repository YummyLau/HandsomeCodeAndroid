package com.example.code.html.tag;

import android.text.Editable;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.StrikethroughSpan;

import com.example.code.html.HtmlParser;

import org.xml.sax.Attributes;

import java.util.Stack;

/**
 * li 标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class AddLi extends TagAction {


    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startLi(output, attributes);
        } else {
            endLi(output);
        }
    }

    private static class Bullet { }

    private void startLi(Editable text, Attributes attributes) {
        startBlockElement(text, attributes, 1);
        start(text, new Bullet());
    }

    private static void endLi(Editable text) {
        endBlockElement(text);
        end(text, Bullet.class, new MyBulletSpan());
    }

    public static class MyBulletSpan extends BulletSpan{

        @Override
        public int getLeadingMargin(boolean first) {
            return 20;
        }
    }

}

