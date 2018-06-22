package com.example.code.html.tag;

import android.text.Editable;

import com.example.code.html.HtmlParser;

import org.xml.sax.Attributes;

/**
 * 兼容处理未支持的标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class TagHandler implements HtmlParser.TagHandler {

    @Override
    public boolean handleTag(boolean opening, String tag, Editable output, Attributes attributes) {
        TagAction action = TagActions.getHandleTag(tag.toLowerCase());
        if (action != null) {
            action.action(opening, tag, output, attributes);
        }
        return TagActions.isSupportTag(tag);
    }
}
