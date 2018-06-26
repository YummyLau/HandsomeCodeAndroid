package com.example.code.html.action.add;

import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;

import com.example.code.html.HtmlParser;
import com.example.code.html.action.ActionType;
import com.example.code.html.action.TagAction;

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
                    case "center": {
                        start(output, new Alignment(Layout.Alignment.ALIGN_CENTER));
                        break;
                    }
                    case "left": {
                        start(output, new Alignment(Layout.Alignment.ALIGN_NORMAL));
                        break;
                    }
                    case "right": {
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
