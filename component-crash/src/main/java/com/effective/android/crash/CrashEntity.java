package com.effective.android.crash;

import com.google.gson.annotations.SerializedName;

/**
 * crash收集信息
 * Created by yummylau on 2018/8/9.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class CrashEntity {

    @SerializedName("id")
    public String id;



    @SerializedName("crashType")        //1表示奔溃，0表示被try主动上传
    public int crashType;

    @SerializedName("exceptionName")    //异常名称
    public int exceptionName;

    @SerializedName("exceptionStack")   //异常堆栈
    public int exceptionStack;

    @SerializedName("crashTime")        //异常时间
    public int crashTime;



    @SerializedName("appVersion")       //app版本号
    public int appVersion;

    @SerializedName("osVersion")        //系统版本号
    public int osVersion;

    @SerializedName("deviceModel")      //手机型号
    public int deviceModel;

    @SerializedName("deviceId")         //设备号
    public int deviceId;

    @SerializedName("networkType")      //网络类型
    public int networkType;

    @SerializedName("channelId")        //渠道号
    public int channelId;

    @SerializedName("clientType")       //客户端类型
    public int clientType;

    @SerializedName("memoryInfo")       //内存信息
    public int memoryInfo;
}
