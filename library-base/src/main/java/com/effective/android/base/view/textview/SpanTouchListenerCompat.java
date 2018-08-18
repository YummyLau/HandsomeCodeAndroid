package com.effective.android.base.view.textview;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


/**
 * 兼容处理textview spanable点击事件的问题：
 * 1. 处理列表内容滑动
 * 2. 处理textview存在空白区域，点击时可能触发最近span的点击事件的问题
 * Created by yummyLau on 2018/6/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class SpanTouchListenerCompat implements View.OnTouchListener {

    private static SpanTouchListenerCompat sInstance;

    public static SpanTouchListenerCompat getInstance() {
        if (sInstance == null) {
            sInstance = new SpanTouchListenerCompat();
        }
        return sInstance;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!(v instanceof TextView)) {
            return false;
        }
        int action = event.getAction();

        //拦截外层事件
        if (action == MotionEvent.ACTION_MOVE) {
            return true;
        }


        TextView widget = (TextView) v;
        Spannable buffer = Spannable.Factory.getInstance().newSpannable(widget.getText());

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            //获取当前行的空白宽度，如果lineWidth 比 x 小，则意味着点击的时候，触碰点已经在当前内容之外（控件之内）
            float lineWidth = layout.getLineWidth(line);

            ClickableSpan[] links = buffer.getSpans(off, off, ClickableSpan.class);

            if (links.length != 0 && lineWidth >= x) {
                if (action == MotionEvent.ACTION_UP) {
                    links[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer, buffer.getSpanStart(links[0]), buffer.getSpanEnd(links[0]));

                }
                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
        return false;
    }

}
