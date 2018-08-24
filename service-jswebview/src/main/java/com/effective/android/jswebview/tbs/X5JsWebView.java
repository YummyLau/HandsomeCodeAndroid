package com.effective.android.jswebview.tbs;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;

import com.effective.android.jswebview.interfaces.OnFinishCallback;
import com.effective.android.jswebview.interfaces.OnScrollChangeCallback;
import com.effective.android.jswebview.jsbridge.BridgeWebView;
import com.effective.android.jswebview.jsbridge.BridgeWebViewClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;


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
    private OnFinishCallback mOnFinishCallback;
    private boolean isError;
    private boolean start;
    private X5JsWebViewClient x5JsWebViewClient;
    private String currentUrl;

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
        if (x5JsWebViewClient == null) {
            x5JsWebViewClient = new X5JsWebViewClient(this) {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.d("x5JsWebViewClient", "shouldOverrideUrlLoading");
                    start = false;
                    currentUrl = url;
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    Log.d("x5JsWebViewClient", "shouldOverrideUrlLoading");
                    start = true;
                    currentUrl = url;
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.d("x5JsWebViewClient", "onPageFinished");
                    if (start) {
                        if (isError) {
                            isError = false;
                            if (mOnFinishCallback != null) {
                                mOnFinishCallback.onFail(url);
                            }
                        } else {
                            isError = false;
                            if (mOnFinishCallback != null) {
                                mOnFinishCallback.onSuccess(url);
                            }
                        }
                        start = false;
                        if (!getSettings().getLoadsImagesAutomatically()) {
                            getSettings().setLoadsImagesAutomatically(true);
                        }
                    }
                    super.onPageFinished(view, url);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Log.d("x5JsWebViewClient", "onReceivedError");
                    isError = true;
                    super.onReceivedError(view, errorCode, description, failingUrl);

                }
            };
        }
        return x5JsWebViewClient;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangeCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public void setOnFinishCallback(OnFinishCallback mOnFinishCallback) {
        this.mOnFinishCallback = mOnFinishCallback;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l, t, oldl, oldt);
        }
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        super.setWebChromeClient(new X5WebChromeClient(client) {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d("X5WebChromeClient", "progress : " + newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
    }

}
