package com.effective.android.base.util;

/**
 * Created by Administrator on 2018/4/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class CharUtils {

    public static boolean isLetter(char c) {
        return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));
    }

    public static boolean isHexLetter(char c) {
        return ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F'));
    }

    public static boolean isDigit(char c) {
        return (c >= '0') && (c <= '9');
    }

    public static boolean isLetterOrDigit(char c) {
        return isLetter(c) || isDigit(c);
    }

    public static boolean isHexDigit(char c) {
        return isHexLetter(c) || isDigit(c);
    }

    public static boolean isWhitespace(char ch) {
        return (ch == '\u0020') || (ch == '\r') || (ch == '\n') || (ch == '\u0009') || (ch == '\u000c') || (ch == '\u200b');
    }
}
