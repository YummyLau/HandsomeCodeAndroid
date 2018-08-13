package com.effective.android.base.view.webview;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 掌握知识：
 * 1. 生命周期，需要再activity中 onPause调用 onPause及puaseTimers，再onResume中调用 onResume及resumeTimers。
 * 2. 前进后退网页
 * 3. 缓存管理：访问记录，表单数据等
 *
 * 注意事项：
 * 1. 销毁时虽然调用destory，但是webview依然绑定在activity，所以需要先加载空url，再从父容器移除之后再销毁。
 * 2. 如何避免内存泄漏？不在xml中定义，构建时使用getApplicationContext作为context参数
 * 3. 如何避免执行任意代码漏洞？js可以通过反射手段获取一个android对象之后跳用该对象的方法。4.2之后可以通过加 @JavascriptInterface 解决 ，之前可以通过可拦截prompt进行逻辑判断。
 * 4. 如何避免域控制不严谨？如果某个activity允许被其他应用，则可以让该activity加载恶意的file协议，获取该应用的内部私有数据。
 * 5. 5.0以上的webview默认不允许加载http与https混合的内容。通过版本判断打开mixedContentMode-->MIXED_CONTENT_ALWAYS_ALLOW
 *
 * Created by yummyLau on 2018/8/13.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class WebViewCompat extends WebView {

    public WebViewCompat(Context context) {
        super(context);
    }

    public WebViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebViewCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(21)
    public WebViewCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WebViewCompat(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

}
