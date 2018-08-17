package com.effective.android.net.okhttp.https;

import android.content.Context;
import android.util.Log;

import com.effective.android.net.okhttp.https.trustall.TrustAllHostnameVerifier;
import com.effective.android.net.okhttp.https.trustall.TrustAllManager;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;

/**
 * https相关处理
 * 如果尝试去访问自签名的网站，则会收到 {@link javax.net.ssl.SSLHandshakeException}
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class HttpsUtils {

    private static final String TAG = HttpsUtils.class.getSimpleName();

    public static void allowAllCertificates(OkHttpClient.Builder builder) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory(), new TrustAllManager());
            builder.hostnameVerifier(new TrustAllHostnameVerifier());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
        } catch (KeyManagementException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 获取  R.raw.xxxCert 进行加载
     *
     * @param context
     * @param builder
     * @param certificates
     */
    public static void allowCertificates(Context context, OkHttpClient.Builder builder, int[] certificates) {
        if (context == null || certificates == null || certificates.length == 0) {
            return;
        }
        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                //读取本地证书
                InputStream is = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(is));

                if (is != null) {
                    is.close();
                }
            }
            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory(), new TrustAllManager());
        } catch (KeyStoreException e) {
            Log.e(TAG, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (KeyManagementException e) {
            Log.e(TAG, e.getMessage());
        } catch (CertificateException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
