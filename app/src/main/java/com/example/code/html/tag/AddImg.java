package com.example.code.html.tag;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.code.html.DisplayUtil;
import com.example.code.html.HtmlImageGetter;
import com.example.code.html.HtmlParser;

import org.xml.sax.Attributes;

/**
 * img 标签
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class AddImg extends TagAction {

    @Override
    public void action(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes) {
        if (opening) {
            startImg(parser.getTextView(), output, attributes);
        } else {
        }
    }

    public static class UrlDrawable extends BitmapDrawable {
        private Drawable remoteDrawable;
        private Attributes attributes;

        public UrlDrawable(Attributes attributes) {
            this.attributes = attributes;
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (remoteDrawable != null) {
                remoteDrawable.draw(canvas);
            }
        }

        public void setRemoteDrawable(Context context, Drawable remoteDrawable) {
            this.remoteDrawable = remoteDrawable;
            int width = DisplayUtil.getWindowWidth(context);
            int height = 0;
            float radius = DisplayUtil.getWindowWidth(context) * 1.0f / DisplayUtil.getWindowHeight(context);
            try {
                int sourceWidth = Integer.valueOf(attributes.getValue("", "width"));
                int sourceHeight = Integer.valueOf(attributes.getValue("", "height"));
                if (sourceWidth != 0 && sourceHeight != 0) {
                    radius = sourceWidth * 1.0f / sourceHeight;
                }
            } catch (Exception e) {
                //nothing to do
            }
            height = (int) (DisplayUtil.getWindowWidth(context) / radius);
            remoteDrawable.setBounds(0, 0, width, height);
            setBounds(0, 0, width, height);
        }
    }

    private static void startImg(TextView textView, Editable text, Attributes attributes) {

        String src = attributes.getValue("", "src");
        UrlDrawable urlDrawable = new UrlDrawable(attributes);
        Glide.with(textView)
                .load(src)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        urlDrawable.setRemoteDrawable(textView.getContext(), resource);
                        textView.invalidate();
                        textView.setText(textView.getText());
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });

        int len = text.length();
        text.append("\uFFFC");

        text.setSpan(new ImageSpan(urlDrawable, src), len, text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}

