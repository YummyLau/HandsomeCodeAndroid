package com.example.code.media;

/**
 * 图片
 * Created by yummyLau on 2018/4/26.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class Image implements IMedia{

    public String id;             //id
    public String name;         //图片名称
    public String desc;         //图片描述
    public String path;         //图片路径
    public long size;           //图片的大小
    public int width;           //图片的宽度
    public int height;          //图片的高度
    public String mimeType;     //图片的类型
    public long createTime;     //图片的创建时间
    public long modifyTime;     //图片的修改时间
    public String location;     //图片创建地点

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
