package com.example.code.html;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * 获取图片
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ImageGetter implements Html.ImageGetter {

    public TextView text;

    public ImageGetter(TextView text) {
        this.text = text;
    }

    @Override
    public Drawable getDrawable(String source) {
        UrlDrawable urlDrawable = new UrlDrawable();
        Glide.with(text)
                .load(source)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        urlDrawable.setRemoteDrawable(resource);
                        text.invalidate();
                        text.setText(text.getText());
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });
        return urlDrawable;
    }

    public class UrlDrawable extends BitmapDrawable {

        private Drawable remoteDrawable;

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (remoteDrawable != null) {
                remoteDrawable.draw(canvas);
            }
        }

        public void setRemoteDrawable(Drawable remoteDrawable) {
            this.remoteDrawable = remoteDrawable;
            remoteDrawable.setBounds(0, 0, remoteDrawable.getIntrinsicWidth(), remoteDrawable.getIntrinsicHeight());
            setBounds(0, 0, remoteDrawable.getIntrinsicWidth(), remoteDrawable.getIntrinsicHeight());
        }

    }
}
