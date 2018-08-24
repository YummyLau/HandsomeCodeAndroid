package com.effective.android.jswebview.jsbridge;


public interface WebViewJavascriptBridge {
	void send(String data);
	void send(String data, CallBackFunction responseCallback);
}
