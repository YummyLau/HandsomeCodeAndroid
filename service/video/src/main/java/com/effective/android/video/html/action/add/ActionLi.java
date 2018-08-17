package com.effective.android.video.html.action.add;

import android.text.Editable;
import android.text.style.BulletSpan;

import com.effective.android.video.html.HtmlParser;
import com.effective.android.video.html.action.ActionType;
import com.effective.android.video.html.action.TagAction;

import org.xml.sax.Attributes;

/**
 * li 标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ActionLi extends TagAction {


    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startLi(output, attributes);
        } else {
            endLi(output);
        }
    }

    @Override
    public ActionType type() {
        return ActionType.ADD;
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

