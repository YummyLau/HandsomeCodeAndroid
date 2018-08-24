package com.effective.android.jswebview.tbs;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by yummyLau on 2018/7/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class X5WebChromeClient extends WebChromeClient {

    private static final String TAG = X5WebChromeClient.class.getSimpleName();
    private WebChromeClient webChromeClient;

    public X5WebChromeClient(WebChromeClient webChromeClient) {
        this.webChromeClient = webChromeClient;
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> valueCallback) {
        Log.d(TAG, "openFileChooser < 3.0");
    }

    // For Android  >= 3.0
    public void openFileChooser(ValueCallback valueCallback, String acceptType) {
        Log.d(TAG, "openFileChooser >= 3.0");
    }

    //For Android  >= 4.1
    public void openFileChooser(ValueCallback<Uri> valueCallback,
                                String acceptType, String capture) {
        Log.d(TAG, "openFileChooser >= 4.1");
    }

    // For Android >= 5.0
    @RequiresApi(21)
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        Log.d(TAG, "onShowFileChooser >= 5.0");
        if (webChromeClient != null) {
            return webChromeClient.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        } else {
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    }

    /**
     * 通知程序当前页面加载进度
     *
     * @param view
     * @param newProgress
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        Log.d(TAG, "onProgressChanged");
        if (webChromeClient != null) {
            webChromeClient.onProgressChanged(view, newProgress);
        } else {
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        Log.d(TAG, "onReceivedTitle");
        if (webChromeClient != null) {
            webChromeClient.onReceivedTitle(view, title);
        } else {
            super.onReceivedTitle(view, title);
        }
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        Log.d(TAG, "onReceivedIcon");
        if (webChromeClient != null) {
            webChromeClient.onReceivedIcon(view, icon);
        } else {
            super.onReceivedIcon(view, icon);
        }
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        Log.d(TAG, "onReceivedTouchIconUrl");
        if (webChromeClient != null) {
            webChromeClient.onReceivedTouchIconUrl(view, url, precomposed);
        } else {
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }
    }

    @Override
    public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
        Log.d(TAG, "onShowCustomView");
        if (webChromeClient != null) {
            webChromeClient.onShowCustomView(view, callback);
        } else {
            super.onShowCustomView(view, callback);
        }
    }

    @Override
    public void onHideCustomView() {
        Log.d(TAG, "onHideCustomView");
        if (webChromeClient != null) {
            webChromeClient.onHideCustomView();
        } else {
            super.onHideCustomView();
        }
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        Log.d(TAG, "onCreateWindow");
        if (webChromeClient != null) {
            return webChromeClient.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        } else {
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }
    }

    @Override
    public void onRequestFocus(WebView view) {
        Log.d(TAG, "onRequestFocus");
        if (webChromeClient != null) {
            webChromeClient.onRequestFocus(view);
        } else {
            super.onRequestFocus(view);
        }
    }

    @Override
    public void onCloseWindow(WebView window) {
        Log.d(TAG, "onCloseWindow");
        if (webChromeClient != null) {
            webChromeClient.onCloseWindow(window);
        } else {
            super.onCloseWindow(window);
        }
    }

    /**
     * 当网页调用alert()来弹出alert弹出框前回调，用以拦截alert()函数
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.d(TAG, "onJsAlert");
        if (webChromeClient != null) {
            return webChromeClient.onJsAlert(view, url, message, result);
        } else {
            return super.onJsAlert(view, url, message, result);
        }
    }

    /**
     * 当网页调用confirm()来弹出confirm弹出框前回调，用以拦截confirm()函数
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Log.d(TAG, "onJsConfirm");
        if (webChromeClient != null) {
            return webChromeClient.onJsConfirm(view, url, message, result);
        } else {
            return super.onJsConfirm(view, url, message, result);
        }
    }

    /**
     * 当网页调用prompt()来弹出prompt弹出框前回调，用以拦截prompt()函数
     *
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param result
     * @return
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Log.d(TAG, "onJsPrompt");
        if (webChromeClient != null) {
            return webChromeClient.onJsPrompt(view, url, message, defaultValue, result);
        } else {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        Log.d(TAG, "onJsBeforeUnload");
        if (webChromeClient != null) {
            return webChromeClient.onJsBeforeUnload(view, url, message, result);
        } else {
            return super.onJsBeforeUnload(view, url, message, result);
        }
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissionsCallback callback) {
        Log.d(TAG, "onGeolocationPermissionsShowPrompt");
        if (webChromeClient != null) {
            webChromeClient.onGeolocationPermissionsShowPrompt(origin, callback);
        } else {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        Log.d(TAG, "onGeolocationPermissionsHidePrompt");
        if (webChromeClient != null) {
            webChromeClient.onGeolocationPermissionsHidePrompt();
        } else {
            super.onGeolocationPermissionsHidePrompt();
        }
    }

    /**
     * 打印 console 信息
     *
     * @param consoleMessage
     * @return
     */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d(TAG, "onConsoleMessage");
        if (webChromeClient != null) {
            return webChromeClient.onConsoleMessage(consoleMessage);
        } else {
            return super.onConsoleMessage(consoleMessage);
        }
    }

    @Nullable
    @Override
    public Bitmap getDefaultVideoPoster() {
        Log.d(TAG, "getDefaultVideoPoster");
        if (webChromeClient != null) {
            return webChromeClient.getDefaultVideoPoster();
        } else {
            return super.getDefaultVideoPoster();
        }
    }

    @Nullable
    @Override
    public View getVideoLoadingProgressView() {
        Log.d(TAG, "getVideoLoadingProgressView");
        if (webChromeClient != null) {
            return webChromeClient.getVideoLoadingProgressView();
        } else {
            return super.getVideoLoadingProgressView();
        }
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        Log.d(TAG, "getVisitedHistory");
        if (webChromeClient != null) {
            webChromeClient.getVisitedHistory(callback);
        } else {
            super.getVisitedHistory(callback);
        }
    }
}
