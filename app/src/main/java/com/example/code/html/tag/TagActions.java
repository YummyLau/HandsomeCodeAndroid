package com.example.code.html.tag;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * html扩展标签
 * 常规的标签 https://commonsware.com/blog/Android/2010/05/26/html-tags-supported-by-textview.html
 * 对于使用 textview及其webview比较 https://www.hidroh.com/2016/02/27/richtext-textview-versus-webview/#fn:medium
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class TagActions {

    public static final Map<String, TagAction> tags = new HashMap<>();
    public static final Set<String> supportTags = new HashSet<>();

    static {
        tags.put("font", new CompatFontTag());
        tags.put("del", new SupportDelTag());

        supportTags.add("del");
    }

    public static boolean isSupportTag(@NonNull String tag) {
        return !TextUtils.isEmpty(tag) && supportTags.contains(tag);
    }

    public static boolean isHandleTag(@NonNull String tag) {
        return !TextUtils.isEmpty(tag) && tags.containsKey(tag);
    }

    @Nullable
    public static TagAction getHandleTag(@NonNull String tag) {
        if (!TextUtils.isEmpty(tag)) {
            return tags.get(tag);
        }
        return null;
    }
}
