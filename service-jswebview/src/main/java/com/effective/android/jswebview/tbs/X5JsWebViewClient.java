package com.effective.android.jswebview.tbs;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;

import com.effective.android.jswebview.jsbridge.BridgeWebViewClient;
import com.tencent.smtt.export.external.interfaces.ClientCertRequest;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by yummyLau on 2018/7/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class X5JsWebViewClient extends BridgeWebViewClient {

    private static final String TAG = X5JsWebViewClient.class.getSimpleName();

    public X5JsWebViewClient(X5JsWebView webView) {
        super(webView);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @RequiresApi(24)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.d(TAG, "shouldOverrideUrlLoading");
        return super.shouldOverrideUrlLoading(view, request);
    }


    @RequiresApi(23)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Log.d(TAG, "onReceivedError -- API 23");
        super.onReceivedError(view, request, error);
    }


    @RequiresApi(23)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        Log.d(TAG, "onReceivedHttpError API 23");
        super.onReceivedHttpError(view, request, errorResponse);
    }


    /**
     * 接收到https错误时，会回调此函数，在其中可以做错误处理
     *
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Log.d(TAG, "onReceivedSslError");
//        super.onReceivedSslError(view, handler, error);
        handler.proceed();
//        继续加载页面
    }

    /**
     * 在每一次请求资源时，都会通过这个函数来回调，在非UI线程中执行的
     *
     * @param view
     * @param request
     * @return
     */
    @RequiresApi(21)
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Log.d(TAG, "shouldInterceptRequest API 21");
        return super.shouldInterceptRequest(view, request);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        Log.d(TAG, "shouldInterceptRequest");
        return super.shouldInterceptRequest(view, url);
    }

    /**
     * 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次
     *
     * @param view
     * @param url
     */
    @Override
    public void onLoadResource(WebView view, String url) {
        Log.d(TAG, "onLoadResource");
        super.onLoadResource(view, url);
    }

    /**
     * WebView发生改变时调用
     *
     * @param view
     * @param oldScale
     * @param newScale
     */
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        Log.d(TAG, "onScaleChanged");
        super.onScaleChanged(view, oldScale, newScale);
    }

    /**
     * 重写此方法才能够处理在浏览器中的按键事件。
     * 是否让主程序同步处理Key Event事件，如过滤菜单快捷键的Key Event事件。
     * 如果返回true，WebView不会处理Key Event，
     * 如果返回false，Key Event总是由WebView处理。默认：false
     *
     * @param view
     * @param event
     * @return
     */
    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        Log.d(TAG, "shouldOverrideKeyEvent");
        return super.shouldOverrideKeyEvent(view, event);
    }

    /**
     * 是否重发POST请求数据，默认不重发
     *
     * @param view
     * @param dontResend
     * @param resend
     */
    @Override
    public void onFormResubmission(WebView view, android.os.Message dontResend, android.os.Message resend) {
        Log.d(TAG, "onFormResubmission");
        super.onFormResubmission(view, dontResend, resend);
    }

    /**
     * 更新访问历史
     *
     * @param view
     * @param url
     * @param isReload
     */
    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        Log.d(TAG, "doUpdateVisitedHistory");
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    /**
     * 通知主程序输入事件不是由WebView调用。是否让主程序处理WebView未处理的Input Event。
     * 除了系统按键，WebView总是消耗掉输入事件或shouldOverrideKeyEvent返回true。
     * 该方法由event 分发异步调用。注意：如果事件为MotionEvent，则事件的生命周期只存在方法调用过程中，
     * 如果WebViewClient想要使用这个Event，则需要复制Event对象。
     *
     * @param view
     * @param event
     */
    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        Log.d(TAG, "onUnhandledKeyEvent");
        super.onUnhandledKeyEvent(view, event);
    }

    /**
     * 通知主程序执行了自动登录请求
     *
     * @param view
     * @param realm
     * @param account
     * @param args
     */
    @Override
    public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
        Log.d(TAG, "onReceivedLoginRequest");
        super.onReceivedLoginRequest(view, realm, account, args);
    }

    /**
     * 通知主程序：WebView接收HTTP认证请求，主程序可以使用HttpAuthHandler为请求设置WebView响应。默认取消请求
     *
     * @param view
     * @param handler
     * @param host
     * @param realm
     */
    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        Log.d(TAG, "onReceivedHttpAuthRequest");
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    /**
     * 通知主程序处理SSL客户端认证请求。如果需要提供密钥，主程序负责显示UI界面。
     * 有三个响应方法：proceed(), cancel() 和 ignore()。
     * 如果调用proceed()和cancel()，webview将会记住response，
     * 对相同的host和port地址不再调用onReceivedClientCertRequest方法。
     * 如果调用ignore()方法，webview则不会记住response。该方法在UI线程中执行，
     * 在回调期间，连接被挂起。默认cancel()，即无客户端认证
     *
     * @param view
     * @param request
     */
    @RequiresApi(21)
    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        Log.d(TAG, "onReceivedClientCertRequest");
        super.onReceivedClientCertRequest(view, request);
    }


    @Override
    public void onTooManyRedirects(WebView view, android.os.Message cancelMsg, android.os.Message continueMsg) {
        Log.d(TAG, "onTooManyRedirects");
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
    }
}
