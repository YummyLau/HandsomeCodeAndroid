package com.effective.android.jswebview.tbs;

import android.content.Context;
import android.util.AttributeSet;

import com.effective.android.jswebview.interfaces.OnScrollChangeCallback;
import com.effective.android.jswebview.jsbridge.BridgeWebView;
import com.effective.android.jswebview.jsbridge.BridgeWebViewClient;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * 1. addJavascriptInterface 4.2以下存在漏洞
 * 2. WebView 密码明文存储漏洞 /data/data/com.package.name/databases/webview.db
 * 1. webView耗电的问题，我们之前发现的一个情况是，webView切换到后台时，如果当前页面有JS代码仍在不时的run, 就会导致比较严重的耗电，所以必须确保切换到后台后暂停JS执行，同时切回来的时候恢复它
 * 2. 闪屏问题，和硬件加速有关
 * 3. 选择文件的兼容问题 https://www.jianshu.com/p/48e688ce801f
 * <p>
 * 解决问题：通过是否是用户点击来判断是否处理重定向问题
 * Created by yummyLau on 2018/7/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public final class X5JsWebView extends BridgeWebView {

    private OnScrollChangeCallback mOnScrollChangedCallback;
    private BridgeWebViewClient bridgeWebViewClient;

    public X5JsWebView(Context context) {
        super(context);
    }

    public X5JsWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public X5JsWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected BridgeWebViewClient generateBridgeWebViewClient() {
        if (bridgeWebViewClient == null) {
            bridgeWebViewClient = new BridgeWebViewClient(this);
        }
        return bridgeWebViewClient;
    }

    public void setWebViewClientProxy(WebViewClient webViewClient) {
        generateBridgeWebViewClient().setProxy(webViewClient);
    }

    @Deprecated
    @Override
    public void setWebViewClient(WebViewClient webViewClient) {
        super.setWebViewClient(webViewClient);
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangeCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public void setOnFinishCallback(OnFinishCallback mOnFinishCallback) {
        generateBridgeWebViewClient().setOnFinishCallback(mOnFinishCallback);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l, t, oldl, oldt);
        }
    }

    @Override
    public void postUrl(String s, byte[] bytes) {
        if (X5WebUtils.isTrustUrl(s)) {
            super.postUrl(s, bytes);
        } else {
            super.postUrl(s, null);
        }
    }
}
