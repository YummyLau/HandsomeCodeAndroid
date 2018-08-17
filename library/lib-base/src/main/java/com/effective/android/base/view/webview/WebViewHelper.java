package com.effective.android.base.view.webview;

import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by yummyLau on 2018/8/13.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class WebViewHelper {

    public static void destoryWebView(WebView webView) {
        if (webView == null) {
            return;
        }
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        webView.clearHistory();
        if (webView.getParent() != null && webView.getParent() instanceof ViewGroup) {
            ((ViewGroup) webView.getParent()).removeView(webView);
        }
        webView.destroy();
    }

    public static boolean onKeyDown(int keyCode, KeyEvent event, WebView webView) {
        if (webView == null) {
            return false;
        }
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

}
