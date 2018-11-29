package com.effective.android.jswebview.jsbridge;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;

import com.tencent.smtt.export.external.interfaces.ClientCertRequest;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 如果要自定义WebViewClient必须要集成此类
 * Created by yummyLau on 2018/7/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class BridgeWebViewClient extends WebViewClient {

    private boolean loadStart;
    private boolean loadError;
    private String currentUrl;
    private OnFinishCallback mOnFinishCallback;
    protected BridgeWebView webView;
    private static final String TAG = BridgeWebViewClient.class.getSimpleName();

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    public WebViewClient proxy;

    public void setProxy(WebViewClient proxy) {
        this.proxy = proxy;
    }

    public void setOnFinishCallback(OnFinishCallback mOnFinishCallback) {
        this.mOnFinishCallback = mOnFinishCallback;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(TAG, "shouldOverrideUrlLoading");
        loadStart = false;
        currentUrl = url;
        try {
            currentUrl = URLDecoder.decode(currentUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (currentUrl.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(currentUrl);
            return true;
        } else if (currentUrl.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            if (proxy != null) {
                return proxy.shouldOverrideUrlLoading(view, url);
            } else {
                return super.shouldOverrideUrlLoading(view, currentUrl);
            }
        }
    }


    // 增加shouldOverrideUrlLoading在api》=24时
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.d(TAG, "shouldOverrideUrlLoading api24");
        loadStart = false;
        currentUrl = request.getUrl().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                currentUrl = URLDecoder.decode(currentUrl, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            if (currentUrl.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
                webView.handlerReturnData(currentUrl);
                return true;
            } else if (currentUrl.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
                webView.flushMessageQueue();
                return true;
            } else {
                if (proxy != null) {
                    return proxy.shouldOverrideUrlLoading(view, request);
                } else {
                    return super.shouldOverrideUrlLoading(view, request);
                }
            }
        } else {
            if (proxy != null) {
                return proxy.shouldOverrideUrlLoading(view, request);
            } else {
                return super.shouldOverrideUrlLoading(view, request);
            }
        }
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (loadStart) {
            if (loadError) {
                loadError = false;
                if (mOnFinishCallback != null) {
                    mOnFinishCallback.onFail(url);
                }
            } else {
                loadError = false;
                if (mOnFinishCallback != null) {
                    mOnFinishCallback.onSuccess(url);
                }
            }
            loadStart = false;
            if (!webView.getSettings().getLoadsImagesAutomatically()) {
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
        }

        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }

        if (proxy != null) {
            proxy.onPageFinished(view, url);
        }
    }

    /**
     * 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次
     */
    @Override
    public void onLoadResource(WebView webView, String s) {
        Log.d(TAG, "onLoadResource");
        if (proxy != null) {
            proxy.onLoadResource(webView, s);
        } else {
            super.onLoadResource(webView, s);
        }
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        Log.d(TAG, "onPageStarted");
        loadStart = true;
        currentUrl = s;
        if (proxy != null) {
            proxy.onPageStarted(webView, s, bitmap);
        } else {
            super.onPageStarted(webView, s, bitmap);
        }
    }

    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        Log.d(TAG, "onReceivedError");
        loadError = true;
        if (proxy != null) {
            proxy.onReceivedError(webView, i, s, s1);
        } else {
            super.onReceivedError(webView, i, s, s1);
        }
    }

    @Override
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        Log.d(TAG, "onReceivedError");
        if (proxy != null) {
            proxy.onReceivedError(webView, webResourceRequest, webResourceError);
        } else {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
        }
    }

    @Override
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        Log.d(TAG, "onReceivedHttpError API 23");
        if (proxy != null) {
            proxy.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        } else {
            super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        }
    }

    /**
     * 在每一次请求资源时，都会通过这个函数来回调，在非UI线程中执行的
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
        Log.d(TAG, "shouldInterceptRequest");
        if (proxy != null) {
            return proxy.shouldInterceptRequest(webView, s);
        } else {
            return super.shouldInterceptRequest(webView, s);
        }
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        Log.d(TAG, "shouldInterceptRequest");
        if (proxy != null) {
            return proxy.shouldInterceptRequest(webView, webResourceRequest);
        } else {
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest, Bundle bundle) {
        Log.d(TAG, "shouldInterceptRequest");
        if (proxy != null) {
            return proxy.shouldInterceptRequest(webView, webResourceRequest, bundle);
        } else {
            return super.shouldInterceptRequest(webView, webResourceRequest, bundle);
        }
    }


    /**
     * 更新访问历史
     */
    @Override
    public void doUpdateVisitedHistory(WebView webView, String s, boolean b) {
        Log.d(TAG, "doUpdateVisitedHistory");
        if (proxy != null) {
            proxy.doUpdateVisitedHistory(webView, s, b);
        } else {
            super.doUpdateVisitedHistory(webView, s, b);
        }
    }

    /**
     * 是否重发POST请求数据，默认不重发
     */
    @Override
    public void onFormResubmission(WebView webView, android.os.Message message, android.os.Message message1) {
        Log.d(TAG, "onFormResubmission");
        if (proxy != null) {
            proxy.onFormResubmission(webView, message, message1);
        } else {
            super.onFormResubmission(webView, message, message1);
        }
    }

    /**
     * 通知主程序：WebView接收HTTP认证请求，主程序可以使用HttpAuthHandler为请求设置WebView响应。默认取消请求
     */
    @Override
    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String s, String s1) {
        Log.d(TAG, "onReceivedHttpAuthRequest");
        if (proxy != null) {
            proxy.onReceivedHttpAuthRequest(webView, httpAuthHandler, s, s1);
        } else {
            super.onReceivedHttpAuthRequest(webView, httpAuthHandler, s, s1);
        }
    }

    /**
     * 接收到https错误时，会回调此函数，在其中可以做错误处理
     */
    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        Log.d(TAG, "onReceivedSslError");
        if (proxy != null) {
            proxy.onReceivedSslError(webView, sslErrorHandler, sslError);
        } else {
            sslErrorHandler.proceed();
//            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }
    }

    /**
     * 通知主程序处理SSL客户端认证请求。如果需要提供密钥，主程序负责显示UI界面。
     * 有三个响应方法：proceed(), cancel() 和 ignore()。
     * 如果调用proceed()和cancel()，webview将会记住response，
     * 对相同的host和port地址不再调用onReceivedClientCertRequest方法。
     * 如果调用ignore()方法，webview则不会记住response。该方法在UI线程中执行，
     * 在回调期间，连接被挂起。默认cancel()，即无客户端认证
     */
    @RequiresApi(21)
    @Override
    public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
        Log.d(TAG, "onReceivedClientCertRequest");
        if (proxy != null) {
            proxy.onReceivedClientCertRequest(webView, clientCertRequest);
        } else {
            super.onReceivedClientCertRequest(webView, clientCertRequest);
        }
    }

    /**
     * WebView发生改变时调用
     */
    @Override
    public void onScaleChanged(WebView webView, float v, float v1) {
        Log.d(TAG, "onScaleChanged");
        if (proxy != null) {
            proxy.onScaleChanged(webView, v, v1);
        } else {
            super.onScaleChanged(webView, v, v1);
        }
    }

    /**
     * 通知主程序输入事件不是由WebView调用。是否让主程序处理WebView未处理的Input Event。
     * 除了系统按键，WebView总是消耗掉输入事件或shouldOverrideKeyEvent返回true。
     * 该方法由event 分发异步调用。注意：如果事件为MotionEvent，则事件的生命周期只存在方法调用过程中，
     * 如果WebViewClient想要使用这个Event，则需要复制Event对象。
     */
    @Override
    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        Log.d(TAG, "onUnhandledKeyEvent");
        if (proxy != null) {
            proxy.onUnhandledKeyEvent(webView, keyEvent);
        } else {
            super.onUnhandledKeyEvent(webView, keyEvent);
        }
    }

    /**
     * 重写此方法才能够处理在浏览器中的按键事件。
     * 是否让主程序同步处理Key Event事件，如过滤菜单快捷键的Key Event事件。
     * 如果返回true，WebView不会处理Key Event，
     * 如果返回false，Key Event总是由WebView处理。默认：false
     * @return
     */
    @Override
    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        Log.d(TAG, "shouldOverrideKeyEvent");
        if (proxy != null) {
            return proxy.shouldOverrideKeyEvent(webView, keyEvent);
        } else {
            return super.shouldOverrideKeyEvent(webView, keyEvent);
        }
    }

    @Override
    public void onTooManyRedirects(WebView webView, android.os.Message message, android.os.Message message1) {
        Log.d(TAG, "onTooManyRedirects");
        if (proxy != null) {
            proxy.onTooManyRedirects(webView, message, message1);
        } else {
            super.onTooManyRedirects(webView, message, message1);
        }
    }

    /**
     * 通知主程序执行了自动登录请求
     */
    @Override
    public void onReceivedLoginRequest(WebView webView, String s, String s1, String s2) {
        Log.d(TAG, "onReceivedLoginRequest");
        if (proxy != null) {
            proxy.onReceivedLoginRequest(webView, s, s1, s2);
        } else {
            super.onReceivedLoginRequest(webView, s, s1, s2);
        }
    }

    @Override
    public void onDetectedBlankScreen(String s, int i) {
        if (proxy != null) {
            proxy.onDetectedBlankScreen(s, i);
        } else {
            super.onDetectedBlankScreen(s, i);
        }
    }
}