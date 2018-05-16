package com.example.code.list.annotations;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 尾部类型
 * <p>
 * FooterType.LOADING 加载中
 * FooterType.ERROR_NET 网络异常
 * FooterType.ERROR_BIZ 加载失败
 * FooterType.MORE 加载更多
 * <p>
 * Created by yummyLau on 2018/5/8.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@IntDef({FooterType.LOADING, FooterType.ERROR_NET, FooterType.ERROR_BIZ, FooterType.MORE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FooterType {
    int LOADING = 1;
    int ERROR_NET = 2;
    int ERROR_BIZ = 3;
    int MORE = 4;
}
