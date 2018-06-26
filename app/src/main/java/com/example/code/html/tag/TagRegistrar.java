package com.example.code.html.tag;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.code.html.Constants.Tag;
import com.example.code.html.action.TagAction;
import com.example.code.html.action.add.ActionA;
import com.example.code.html.action.add.ActionBlockQuote;
import com.example.code.html.action.add.ActionDel;
import com.example.code.html.action.add.ActionHr;
import com.example.code.html.action.add.ActionImg;
import com.example.code.html.action.add.ActionLi;
import com.example.code.html.action.add.ActionP;
import com.example.code.html.action.add.ActionStrong;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 标签注册
 * 常规的标签 https://commonsware.com/blog/Android/2010/05/26/html-tags-supported-by-textview.html
 * 对于使用 textview及其webview比较 https://www.hidroh.com/2016/02/27/richtext-textview-versus-webview/#fn:medium
 * Created by yummyLau on 2018/6/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class TagRegistrar {

    private static final Set<String> addTags = new HashSet<>();
    private static final Set<String> allTags = new HashSet<>();
    private static final Stack<TagAction> sHandleAction = new Stack<>();

    static {
        addTags.add(Tag.H1);
        addTags.add(Tag.B);
        addTags.add(Tag.STRONG);
        addTags.add(Tag.STRIKE);
        addTags.add(Tag.DEL);
        addTags.add(Tag.BLOCK_QUOTE);
        addTags.add(Tag.LI);
        addTags.add(Tag.A);
        addTags.add(Tag.IMG);
        addTags.add(Tag.P);
        addTags.add(Tag.HR);

        allTags.add(Tag.FONT);
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
                    case Tag.H1:
                    case Tag.B:
                    case Tag.STRONG: {
                        action = new ActionStrong();
                        break;
                    }
                    case Tag.DEL:
                    case Tag.STRIKE: {
                        action = new ActionDel();
                        break;
                    }
                    case Tag.BLOCK_QUOTE: {
                        action = new ActionBlockQuote();
                        break;
                    }
                    case Tag.LI: {
                        action = new ActionLi();
                        break;
                    }
                    case Tag.A: {
                        action = new ActionA();
                        break;
                    }
                    case Tag.IMG: {
                        action = new ActionImg();
                        break;
                    }
                    case Tag.P: {
                        action = new ActionP();
                        break;
                    }
                    case Tag.HR: {
                        action = new ActionHr();
                        break;
                    }
                    case Tag.FONT: {
                        action = new ActionBlockQuote();
                        break;
                    }
                    default: {
                        return null;
                    }
                }
                if (action != null && pop) {
                    sHandleAction.push(action);
                }
            } else {
                action = sHandleAction.pop();
            }
        }
        return action;
    }
}
