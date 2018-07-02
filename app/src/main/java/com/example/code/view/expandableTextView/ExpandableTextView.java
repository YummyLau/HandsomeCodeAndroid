package com.example.code.view.expandableTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import com.example.code.R;

/**
 * Created by yummyLau on 2018/7/02.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ExpandableTextView extends AppCompatTextView {

    private static final String ELLIPSIZE_END_TEXT = "...";
    private static final int DEFAULT_COLLAPSE_LINE_COUNT = 1;
    private static final String DEFAULT_COLLAPSE_TEXT = "<展开>";
    private static final int DEFAULT_EXPAND_LINE_COUNT = Integer.MAX_VALUE;
    private static final String DEFAULT_EXPAND_TEXT = "<收起>";

    private CharSequence originText;
    private Callback callback;

    private int collapseLine;
    private String collapseText;

    private int expandLine;
    private String expandText;

    @Action
    public int action = Action.NONE;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        if (typedArray != null) {
            collapseLine = typedArray.getInteger(R.styleable.ExpandableTextView_collapse_line, DEFAULT_COLLAPSE_LINE_COUNT);
            collapseText = typedArray.getString(R.styleable.ExpandableTextView_collapse_text);
            expandLine = typedArray.getInteger(R.styleable.ExpandableTextView_expand_line, DEFAULT_EXPAND_LINE_COUNT);
            expandText = typedArray.getString(R.styleable.ExpandableTextView_expand_text);
            typedArray.recycle();
        }

        if (TextUtils.isEmpty(collapseText)) {
            collapseText = DEFAULT_COLLAPSE_TEXT;
        }

        if (TextUtils.isEmpty(expandText)) {
            expandText = DEFAULT_EXPAND_TEXT;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        StaticLayout staticLayout = new StaticLayout(originText, getPaint(), getMeasuredWidth() - getPaddingLeft(),
                Layout.Alignment.ALIGN_CENTER, 1, 0, true);
        int originLineCount = staticLayout.getLineCount();
        if (originLineCount > collapseLine) {
            switch (action) {
                case Action.EXPAND: {

                    /**
                     * 1. 如果展开状态中，originLineCount比expandLine小，则直接拼接 expandText,如果拼接之后溢出，则需要考虑 expandText 换行
                     * 2. 如果展开状态中，originLineCount大于等于expandLine，则拼接 ELLIPSIZE_END_TEXT + expandText
                     */
                    //可用宽度
                    float availableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
                    SpannableStringBuilder builder;

                    if (originLineCount < expandLine) {
                        int limitLine = originLineCount;
                        int lineStart = staticLayout.getLineStart(limitLine - 1);
                        int lineEnd = staticLayout.getLineEnd(limitLine - 1);
                        CharSequence ellipsizedText = staticLayout.getText().subSequence(0, lineEnd);
                        builder = new SpannableStringBuilder(ellipsizedText);
                        builder.append(expandText);
                        float appendWidth = staticLayout.getPaint().measureText(builder, lineStart, builder.length());
                        if (availableWidth < appendWidth) {
                            builder.delete(ellipsizedText.length(), builder.length());
                            builder.append("\n");
                            builder.append(expandText);
                        }
                    } else {
                        int limitLine = expandLine;
                        int lineStart = staticLayout.getLineStart(limitLine - 1);
                        int lineEnd = staticLayout.getLineEnd(limitLine - 1);
                        CharSequence ellipsizedText = staticLayout.getText().subSequence(0, lineEnd);
                        builder = new SpannableStringBuilder(ellipsizedText);
                        builder.append(ELLIPSIZE_END_TEXT);
                        builder.append(expandText);
                        float appendWidth = staticLayout.getPaint().measureText(builder, lineStart, builder.length());
                        while (appendWidth > availableWidth) {
                            int deleteIndex = builder.length() - 1 - expandText.length() - ELLIPSIZE_END_TEXT.length();
                            builder.delete(deleteIndex, deleteIndex + 1);
                            appendWidth = getPaint().measureText(builder, lineStart, builder.length());
                        }
                    }
                    int spanEnd = builder.length();
                    int spanStart = spanEnd - expandText.length();
                    builder.setSpan(
                            new ClickableSpan() {
                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    ds.setColor(Color.RED);
                                    ds.setUnderlineText(false);
                                }

                                @Override
                                public void onClick(View widget) {
                                    setText(originText, false, callback);
                                }
                            }, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    setText(builder);
                    onStatus(Action.EXPAND);
                    break;
                }
                case Action.COLLAPSE: {

                    int lineStart = staticLayout.getLineStart(collapseLine - 1);
                    int lineEnd = staticLayout.getLineEnd(collapseLine - 1);
                    CharSequence ellipsizedText = staticLayout.getText().subSequence(0, lineEnd);

                    //可用宽度
                    float availableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
                    SpannableStringBuilder builder = new SpannableStringBuilder(ellipsizedText);
                    builder.append(ELLIPSIZE_END_TEXT);
                    builder.append(collapseText);

                    float appendWidth = staticLayout.getPaint().measureText(builder, lineStart, builder.length());

                    while (appendWidth > availableWidth) {
                        int deleteIndex = builder.length() - 1 - collapseText.length() - ELLIPSIZE_END_TEXT.length();
                        builder.delete(deleteIndex, deleteIndex + 1);
                        appendWidth = getPaint().measureText(builder, lineStart, builder.length());
                    }

                    int spanEnd = builder.length();
                    int spanStart = spanEnd - collapseText.length();
                    builder.setSpan(
                            new ClickableSpan() {
                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    ds.setColor(Color.RED);
                                    ds.setUnderlineText(false);
                                }

                                @Override
                                public void onClick(View widget) {
                                    setText(originText, true, callback);
                                }
                            }, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    setText(builder);
                    onStatus(Action.COLLAPSE);
                    break;
                }
                case Action.NONE: {
                    setText(originText);
                    onStatus(Action.NONE);
                    break;
                }
            }
        } else {
            setText(originText);
            onStatus(Action.NONE);
        }
    }

    public void onStatus(@Action int action) {
        this.action = action;
        if (callback != null) {
            callback.onAction(action);
        }
    }

    public void setText(CharSequence text, boolean toExpand, Callback callback) {
        this.originText = text;
        if (toExpand) {
            action = Action.EXPAND;
        } else {
            action = Action.COLLAPSE;
        }
        this.callback = callback;
        setText(text);
        requestLayout();
    }
}
