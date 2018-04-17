package com.example.code.util.resource;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

/**
 * Created by yummyLau on 2018/4/17.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class AssetsUtils {

    public static final String file2String(Context context, String path) {

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
}
