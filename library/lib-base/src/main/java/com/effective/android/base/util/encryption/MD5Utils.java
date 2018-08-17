package com.effective.android.base.util.encryption;

import java.io.InputStream;
import java.security.MessageDigest;

/**
 * MD5转化
 * Created by yummyLau on 17-4-24
 * Email: yummyl.lau@gmail.com
 *  blog: yummylau.com
 */

public class MD5Utils {

    private final static String TAG = MD5Utils.class.getSimpleName();
    private static final String MD5 = "MD5";


    /**
     * MD5加密
     *
     * @param data 明文字符串
     * @return 密文
     */
    public static String md5(String data) {
        return md5(data.getBytes());
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String md5(byte[] data) {
        return EncryptUtils.bytes2HexString(encryptMD5(data));
    }

    /**
     * 根据输入流获得文件MD5摘要
     *
     * @param inputStream inputstream
     * @return string
     */
    public static String md5(InputStream inputStream) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = inputStream.read(buffer)) > 0) {
                mdTemp.update(buffer, 0, numRead);
            }
            return EncryptUtils.bytes2HexString(mdTemp.digest());
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @param salt 盐字节数组
     * @return 密文
     */
    public static String md5(byte[] data, byte[] salt) {
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return EncryptUtils.bytes2HexString(encryptMD5(dataSalt));
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptMD5(byte[] data) {
        return EncryptUtils.encryptAlgorithm(data, "MD5");
    }
}
