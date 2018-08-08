package com.effective.android.base.util.serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 序列化公爵
 * Created by yummyLau on 17-4-24
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class SerializableUtil {

    /**
     * 从inpustStream流中读取读取对象
     *
     * @param is
     * @param <T>
     * @return
     */
    public static <T> T load(InputStream is) {
        try {
            return (T) new ObjectInputStream(is).readObject();
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
        return null;
    }

    public static <T> boolean save(OutputStream os, T t) {
        try {
            new ObjectOutputStream(os).writeObject(t);
            os.flush();
            return true;
        } catch (Throwable tr) {
            tr.printStackTrace();
        }

        return false;
    }

    public static <T> T load(String path) {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            return load(is);
        } catch (Throwable tr) {
            tr.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable tr) {
                    tr.printStackTrace();
                }
            }
        }
        return null;
    }

    public static <T> boolean save(String path, T t) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            return save(os, t);
        } catch (Throwable tr) {
            tr.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Throwable tr) {
                    tr.printStackTrace();
                }
            }
        }

        return false;
    }
}
