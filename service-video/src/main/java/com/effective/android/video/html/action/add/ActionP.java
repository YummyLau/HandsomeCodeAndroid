package com.effective.android.video.html.action.add;

import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;

import com.effective.android.video.html.Constants;
import com.effective.android.video.html.HtmlParser;
import com.effective.android.video.html.action.ActionType;
import com.effective.android.video.html.action.TagAction;

import org.xml.sax.Attributes;


/**
 * p 标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ActionP extends TagAction {

    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            appendNewlines(output, 2);
            start(output, new Newline(2));

            String align = attributes.getValue("", "align");
            if (!TextUtils.isEmpty(align)) {
                switch (align) {
                    case Constants.Attributes.P_ALIGN_CENTER: {
                        start(output, new Alignment(Layout.Alignment.ALIGN_CENTER));
                        break;
                    }
                    case Constants.Attributes.P_ALIGN_LEFT: {
                        start(output, new Alignment(Layout.Alignment.ALIGN_NORMAL));
                        break;
                    }
                    case Constants.Attributes.P_ALIGN_RIGHT: {
                        start(output, new Alignment(Layout.Alignment.ALIGN_OPPOSITE));
                        break;
                    }
                }
            }
        } else {
            endBlockElement(output);
        }
    }

    @Override
    public ActionType type() {
        return ActionType.ADD;
    }

}
