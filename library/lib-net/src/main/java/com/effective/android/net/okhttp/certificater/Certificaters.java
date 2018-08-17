package com.effective.android.net.okhttp.certificater;

import okhttp3.CertificatePinner;

/**
 * 证书
 * Created by yummyLau on 2018/8/01.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class Certificaters {

    public static CertificatePinner getCertificatePinner() {
        return new CertificatePinner.Builder().build();
    }
}
