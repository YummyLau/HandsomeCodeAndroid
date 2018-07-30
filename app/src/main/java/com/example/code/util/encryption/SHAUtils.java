package com.example.code.util.encryption;

/**
 * Created by Administrator on 2018/7/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class SHAUtils {

    /**
     * SHA1加密
     *
     * @param data 明文字符串
     * @return 密文
     */
    public static String sha1(String data) {
        return sha1(data.getBytes());
    }

    /**
     * SHA1加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String sha1(byte[] data) {
        return EncryptUtils.bytes2HexString(encryptSHA1(data));
    }

    /**
     * SHA1加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA1(byte[] data) {
        return EncryptUtils.encryptAlgorithm(data, "SHA-1");
    }


    /**
     * SHA224加密
     *
     * @param data 明文字符串
     * @return 密文
     */
    public static String sha224(String data) {
        return sha224(data.getBytes());
    }

    /**
     * SHA224加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String sha224(byte[] data) {
        return EncryptUtils.bytes2HexString(encryptSHA224(data));
    }

    /**
     * SHA224加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA224(byte[] data) {
        return EncryptUtils.encryptAlgorithm(data, "SHA-224");
    }

    /**
     * SHA256加密
     *
     * @param data 明文字符串
     * @return 密文
     */
    public static String sha256(String data) {
        return sha256(data.getBytes());
    }

    /**
     * SHA256加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String sha256(byte[] data) {
        return EncryptUtils.bytes2HexString(encryptSHA256(data));
    }

    /**
     * SHA256加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA256(byte[] data) {
        return EncryptUtils.encryptAlgorithm(data, "SHA-256");
    }

    /**
     * SHA384加密
     *
     * @param data 明文字符串
     * @return 密文
     */
    public static String sha384(String data) {
        return sha384(data.getBytes());
    }

    /**
     * SHA384加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String sha384(byte[] data) {
        return EncryptUtils.bytes2HexString(encryptSHA384(data));
    }

    /**
     * SHA384加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA384(byte[] data) {
        return EncryptUtils.encryptAlgorithm(data, "SHA-384");
    }

    /**
     * SHA512加密
     *
     * @param data 明文字符串
     * @return 密文
     */
    public static String sha512(String data) {
        return sha512(data.getBytes());
    }

    /**
     * SHA512加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String sha512(byte[] data) {
        return EncryptUtils.bytes2HexString(encryptSHA512(data));
    }

    /**
     * SHA512加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA512(byte[] data) {
        return EncryptUtils.encryptAlgorithm(data, "SHA-512");
    }
}
