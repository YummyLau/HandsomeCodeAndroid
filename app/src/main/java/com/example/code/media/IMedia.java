package com.example.code.media;

/**
 * 多媒体接口
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface IMedia {

    String id();

    String getName();

    String getDesc();

    String getPath();

    String getMimeType();

    String getLocation();

    long getSize();

    int getWidth();

    int getHeight();

    long getCreateTime();

    long getModifyTime();
}
