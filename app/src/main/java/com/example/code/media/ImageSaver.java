package com.example.code.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.code.media.interfaces.IMedia;

import java.io.File;


/**
 * 多媒体接口
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ImageSaver {

    private static final String TAG = ImageSaver.class.getSimpleName();

    public static void saveMedia(@NonNull Context context, @NonNull IMedia media) {
        if (context == null || media == null) {
            return;
        }
        if (isImage(media)) {
            saveImage(context, media);
            return;
        }

        if (isVideo(media)) {
            saveVideo(context, media);
            return;
        }
    }


    private static boolean isImage(IMedia media) {
        return media != null && !TextUtils.isEmpty(media.getMimeType()) && media.getMimeType().contains("image");
    }

    private static boolean isVideo(IMedia media) {
        return media != null && !TextUtils.isEmpty(media.getMimeType()) && media.getMimeType().contains("video");
    }

    private static void saveImage(@NonNull Context context, @NonNull IMedia media) {
        if (context == null || media == null) {
            return;
        }
        if (TextUtils.isEmpty(media.getPath())) {
            Log.e(TAG, "#saveImage media'path is empty!");
            return;
        }
//
//        try {
//            RequestManager requestManager = Glide.with(context);
//            requestManager.load(media.getPath()).downloadOnly(new SimpleTarget<File>(){
//                @Override
//                public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
//
//                }
//            });
//        }catch (Exception e){
//
//        }
//
//        Glide.with(context).load(media.getPath()).downloadOnly(new SimpleTarget<File>() {
//            @Override
//            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
//                Utils.saveToPhoto(context, resource, type);
//            }
//        });

    }


    private static void saveVideo(@NonNull Context context, @NonNull IMedia media) {

    }
}
