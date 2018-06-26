package com.example.code.html;

import android.text.Editable;

import com.example.code.html.action.TagAction;
import com.example.code.html.tag.TagRegistrar;

import org.xml.sax.Attributes;

/**
 * 兼容处理未支持的标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class HtmlTagHandler implements HtmlParser.TagHandler {

    @Override
    public boolean handleTag(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        TagAction action = TagRegistrar.getHandleTag(tag.toLowerCase(), opening);
        if (action != null) {
            action.action(parser, opening, tag, output, attributes);
        }
        return TagRegistrar.isAddTag(tag);
    }
}
