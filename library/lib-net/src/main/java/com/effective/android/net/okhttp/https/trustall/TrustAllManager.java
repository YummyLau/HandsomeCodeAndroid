package com.effective.android.net.okhttp.https.trustall;

import android.annotation.SuppressLint;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;


/**
 * 一个不验证证书链的证书信任管理器
 * Created by yummyLau on 2018/8/01.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class TrustAllManager implements X509TrustManager {

    @SuppressLint("TrustAllX509TrustManager")
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    @SuppressLint("TrustAllX509TrustManager")
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        //直接忽略服务端证书，则攻击者可以直接拦截请求然后替换证书
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
