package com.example.code.html.tag;

import android.text.Editable;

import org.xml.sax.Attributes;

/**
 * tag需要处理的行为
 *
 * CompatxxTag 处理的是扩展原支持的Tag缺失的属性 {@link CompatFontTag}
 * SupportxxTag 处理的是扩展不支持的Tag {@link SupportDelTag}
 *
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface TagAction {
    void action(boolean opening, String tag, Editable output, Attributes attributes);
}
