package com.example.code.html.tag;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.support.annotation.ColorInt;
import android.text.Editable;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.QuoteSpan;

import com.example.code.html.HtmlParser;

import org.xml.sax.Attributes;

import java.util.Stack;

/**
 * 覆盖 blockquote 实现
 * created by g8931 on 2018/6/25
 */
public class AddBlockQuote extends TagAction {

    private Stack<Integer> startIndex = new Stack<>();

    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startBlockQuote(output, attributes);
            startIndex.push(output.length());
        } else {
            endBlockQuote(output);
            output.setSpan(new ForegroundColorSpan(Color.parseColor("#989ABF")), startIndex.pop(), output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private static class BlockQuote {
    }

    private void startBlockQuote(Editable text, Attributes attributes) {
        startBlockElement(text, attributes, 2);
        start(text, new BlockQuote());
    }

    private static void endBlockQuote(Editable text) {
        endBlockElement(text);
        end(text, BlockQuote.class, new MyQuoteSpan(Color.parseColor("#989ABF")));
    }

    public static class MyQuoteSpan extends QuoteSpan {

        private final int color;


        public MyQuoteSpan(int color) {
            super(color);
            this.color = color;
        }

        @Override
        public int getLeadingMargin(boolean first) {
            return 20;
        }

        @Override
        public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                      int top, int baseline, int bottom,
                                      CharSequence text, int start, int end,
                                      boolean first, Layout layout) {
            Paint.Style style = p.getStyle();
            int color = p.getColor();

            p.setStyle(Paint.Style.FILL);
            p.setColor(this.color);
            c.drawRect(x, top, x + dir * 4, bottom, p);

            p.setStyle(style);
            p.setColor(color);
        }
    }
}
