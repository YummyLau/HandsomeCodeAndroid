package com.example.code.html.tag;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * html扩展标签
 * 常规的标签 https://commonsware.com/blog/Android/2010/05/26/html-tags-supported-by-textview.html
 * 对于使用 textview及其webview比较 https://www.hidroh.com/2016/02/27/richtext-textview-versus-webview/#fn:medium
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class TagActions {

    private static final String FONT = "font";

    private static final String H1 = "h1";
    private static final String B = "b";
    private static final String STRONG = "strong";
    private static final String STRIKE = "strike";
    private static final String DEL = "del";
    private static final String BLOCK_QUOTE = "blockquote";
    private static final String LI = "li";
    private static final String A = "a";
    private static final String IMG = "img";

    public static final Set<String> addTags = new HashSet<>();
    public static final Set<String> allTags = new HashSet<>();

    private static final Stack<TagAction> mActions = new Stack<>();

    static {
        addTags.add(H1);
        addTags.add(B);
        addTags.add(STRONG);
        addTags.add(STRIKE);
        addTags.add(DEL);
        addTags.add(BLOCK_QUOTE);
        addTags.add(LI);
        addTags.add(A);
        addTags.add(IMG);

        allTags.add(FONT);
        allTags.addAll(addTags);
    }

    public static boolean isAddTag(@NonNull String tag) {
        return !TextUtils.isEmpty(tag) && addTags.contains(tag);
    }


    @Nullable
    public static TagAction getHandleTag(@NonNull String tag, boolean pop) {
        TagAction action = null;
        if (!TextUtils.isEmpty(tag) && allTags.contains(tag)) {
            if (pop) {
                switch (tag) {
                    case H1:
                    case B:
                    case STRONG: {
                        action = new AddStrong();
                        break;
                    }
                    case DEL:
                    case STRIKE: {
                        action = new AddDel();
                        break;
                    }
                    case BLOCK_QUOTE: {
                        action = new AddBlockQuote();
                        break;
                    }
                    case LI: {
                        action = new AddLi();
                        break;
                    }
                    case A: {
                        action = new AddA();
                        break;
                    }
                    case IMG: {
                        action = new AddImg();
                        break;
                    }
                    case FONT: {
                        action = new AddBlockQuote();
                        break;
                    }
                    default: {
                        return null;
                    }
                }
                if (action != null && pop) {
                    mActions.push(action);
                }
            } else {
                action = mActions.pop();
            }
        }
        return action;
    }
}
