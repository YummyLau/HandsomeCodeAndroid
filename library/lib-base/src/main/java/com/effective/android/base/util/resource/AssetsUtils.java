package com.effective.android.base.util.resource;

import android.content.Context;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

/**
 * Created by yummyLau on 2018/4/17.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class AssetsUtils {

    /**
     * 从assets中获取string文本
     * @param context
     * @param path
     * @return
     */
    public static final String getStringFormAsset(Context context, String path) {

        StringWriter writer = new StringWriter();
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(path)));
            char[] buffer = new char[4096];
            int count;
            while ((count = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return writer.toString();
    }

    /**
     * 从raw中读取string文本
     * @param context
     * @param rawId
     * @return
     */
    public final static String getStringFormRaw(Context context, @RawRes int rawId) {
        ByteArrayOutputStream baos = null;
        InputStream in = context.getResources().openRawResource(rawId);
        try {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
