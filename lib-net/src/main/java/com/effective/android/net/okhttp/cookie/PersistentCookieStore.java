package com.effective.android.net.okhttp.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import okhttp3.Cookie;
import okhttp3.HttpUrl;


/**
 * A persistent cookie store which implements the Apache HttpClient CookieStore interface.
 * Cookies are stored and will persist on the user's device between application sessions since they
 * are serialized and stored in SharedPreferences. Instances of this class are
 * designed to be used with AsyncHttpClient#setCookieStore, but can also be used with a
 * regular old apache HttpClient/HttpContext if you prefer.
 * <p>
 * 本地存储cookie
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class PersistentCookieStore {

    private static final String TAG = PersistentCookieStore.class.getSimpleName();
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private static final String HOST_PRE = "host_";

    /**
     * 以访问百度为例子，在Cookies中有
     * key ： http://e.baidu.com
     * value ： 可能存在多个Cookie
     * [
     * {
     * "Name" : "BAIDUID",
     * "Vaule" : "D9CCD88E8E439DAE5623C38A9F131128",
     * "Domain" : ".baidu.com",
     * "Path" " : "/",
     * "Expires/Max-Age" : "2019-07-19T09:40:47.337z",
     * "Size" : "44",
     * "Http" : "",
     * "Secure" : "",
     * "Same" " ""
     * },
     * {
     * ...
     * }
     * ]
     */
    private final HashMap<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    public PersistentCookieStore(Context context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new HashMap<>();

        //将持久化的cookies缓存到内存中 即map cookies
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {

            String key = entry.getKey();
            Object object = entry.getValue();

            //寻找 host_ 开头的项
            if (!TextUtils.isEmpty(key) && key.startsWith(HOST_PRE) && object instanceof String) {
                String value = (String) object;

                //索取所有cookies id
                String[] cookieId = TextUtils.split(value, ",");
                for (String name : cookieId) {
                    String encodedCookie = cookiePrefs.getString(name, null);
                    if (encodedCookie != null) {
                        Cookie decodedCookie = decodeCookie(encodedCookie);
                        if (decodedCookie != null) {
                            if (!cookies.containsKey(key)) {
                                cookies.put(key, new ConcurrentHashMap<>());
                            }
                            cookies.get(key).put(name, decodedCookie);
                        }
                    }
                }
            }
        }
    }

    /**
     * example
     * host_www.google.com
     * @param url
     * @return
     */
    private String getUrlHostID(HttpUrl url){
        return HOST_PRE + url.host();
    }

    /**
     * {
     * "Name" : "BAIDUID",
     * "Vaule" : "D9CCD88E8E439DAE5623C38A9F131128",
     * "Domain" : ".baidu.com",
     * "Path" " : "/",
     * "Expires/Max-Age" : "2019-07-19T09:40:47.337z",
     * "Size" : "44",
     * "Http" : "",
     * "Secure" : "",
     * "Same" " ""
     * }
     *
     * @param cookie
     * @return BAIDUID@.baidu.com
     */
    protected String getCookieId(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    /**
     * 一个url可能对应多个 cookie信息
     *
     * @param url    请求链接
     * @param cookie 对应其中的cookie
     */
    public void add(HttpUrl url, Cookie cookie) {
        String name = getCookieId(cookie);

        //将cookies缓存到内存中 如果缓存过期 就重置此cookie
        if (cookie.persistent()) {
            if (!cookies.containsKey(getUrlHostID(url))) {
                cookies.put(HOST_PRE + getUrlHostID(url), new ConcurrentHashMap<>());
            }
            cookies.get(getUrlHostID(url)).put(name, cookie);
        } else {
            if (cookies.containsKey(getUrlHostID(url))) {
                cookies.get(getUrlHostID(url)).remove(name);
            } else {
                return;
            }
        }

        //更新本地缓存
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        // 如 请求www.baidu.com，www.baidu.com 为 url.host()
        // 存在多个cookie，映射到 BAIDUID@.baidu.com ，BAIDUID@.baidu.com ， BAIDUID@.baidu.com ,...多个cookieToken
        prefsWriter.putString(getUrlHostID(url), TextUtils.join(",", cookies.get(getUrlHostID(url)).keySet()));
        //在单独存储对应的cookie
        prefsWriter.putString(name, encodeCookie(new SerializableOkHttpCookie(cookie)));
        prefsWriter.apply();
    }


    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(getUrlHostID(url))) {
            Collection<Cookie> cookies = this.cookies.get(getUrlHostID(url)).values();
            for (Cookie cookie : cookies) {
                if (isCookieExpired(cookie)) {
                    remove(url, cookie);
                } else {
                    ret.add(cookie);
                }
            }
        }
        return ret;
    }

    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    public boolean removeAll() {
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.apply();
        cookies.clear();
        return true;
    }

    public boolean remove(HttpUrl url, Cookie cookie) {
        String name = getCookieId(cookie);

        if (cookies.containsKey(getUrlHostID(url)) && cookies.get(getUrlHostID(url)).containsKey(name)) {
            cookies.get(getUrlHostID(url)).remove(name);

            SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            if (cookiePrefs.contains(name)) {
                prefsWriter.remove(name);
            }

            //更新url对应的cookies
            prefsWriter.putString(getUrlHostID(url), TextUtils.join(",", cookies.get(getUrlHostID(url)).keySet()));
            prefsWriter.apply();
            return true;
        } else {
            return false;
        }
    }

    public List<Cookie> getCookies() {
        ArrayList<Cookie> ret = new ArrayList<>();
        for (String key : cookies.keySet())
            ret.addAll(cookies.get(key).values());

        return ret;
    }

    /**
     * cookies 序列化成 string
     *
     * @param cookie 要序列化的cookie
     * @return 序列化之后的string
     */
    protected String encodeCookie(SerializableOkHttpCookie cookie) {
        if (cookie == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
            Log.d(TAG, "IOException in encodeCookie", e);
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    protected Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableOkHttpCookie) objectInputStream.readObject()).getCookie();
        } catch (IOException e) {
            Log.d(TAG, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "ClassNotFoundException in decodeCookie", e);
        }

        return cookie;
    }

    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
