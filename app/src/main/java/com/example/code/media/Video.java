package com.example.code.media;

/**
 * 视频
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class Video implements IMedia{

    public String id;             //id
    public String name;         //视频名称
    public String desc;         //视频描述
    public String path;         //视频路径
    public long size;           //视频的大小
    public int width;           //视频的宽度
    public int height;          //视频的高度
    public String mimeType;     //视频的类型
    public long createTime;     //视频的创建时间
    public long modifyTime;     //视频的修改时间
    public String location;     //视频创建地点

    public long duration;       //视频长度

    @Override
    public String id() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public long getCreateTime() {
        return createTime;
    }

    @Override
    public long getModifyTime() {
        return modifyTime;
    }
}
