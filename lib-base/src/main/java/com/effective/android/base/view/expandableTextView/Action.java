package com.effective.android.base.view.expandableTextView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * textView show text by action way
 * Created by yummyLau on 2018/7/02.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    int NONE = 0;               //show as normal TextView
    int EXPAND = 1;             //to expand
    int COLLAPSE = 2;           //to collapse
}
