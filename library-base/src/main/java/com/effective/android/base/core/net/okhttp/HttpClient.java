package com.effective.android.base.core.net.okhttp;


import android.app.Application;

import com.effective.android.base.core.net.okhttp.cookie.OkHttpCookieJar;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 * <p>
 * okhttp3采用构造者模式来实现的
 * <p>
 * Request.Builder 请求构造者
 * url(String url)：请求的url
 * post()：默认是Get方式
 * post(RequestBody body)：Post带参数
 * build()：构造请求
 * <p>
 * 请求参数有三种：
 * RequestBody：普通的请求参数
 * FormBody.Builder：以表单的方式传递键值对的请求参数
 * MultipartBody.Builder：以表单的方式上传文件的请求参数
 * <p>
 * 执行方法：
 * Call
 * enqueue(Callback callback)：异步请求
 * execute()：同步请求
 */
public class HttpClient {

    private static final String TAG = HttpClient.class.getSimpleName();

    private static Application sApplication;
    private static boolean sDebug;
    private static volatile OkHttpClient sClient;

    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;

    /**
     * 获取全局application
     */
    public static void init(Application application, boolean debug) {
        sApplication = application;
        sDebug = debug;
        if (sDebug) {
            Stetho.initializeWithDefaults(application);
        }
    }

    /**
     * 上传下载需要注意下读写设置
     *
     * @return
     */
    public static OkHttpClient getInstance() {
        if (sApplication == null) {
            throw new RuntimeException(TAG + "#getInstance" + "sApplication can't be null ! please invoke init at Application#onCreate");
        }
        if (sClient == null) {
            synchronized (HttpClient.class) {
                if (sClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();

                    if (sDebug) {
                        //log
                        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
                        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(logInterceptor);

                        //stetho
                        builder.addNetworkInterceptor(new StethoInterceptor());
                    }

                    //证书
//                    HttpsUtils.allowAllCertificates(builder);

                    //cookie
                    builder.cookieJar(new OkHttpCookieJar(sApplication));

                    sClient = builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return sClient;
    }

}
