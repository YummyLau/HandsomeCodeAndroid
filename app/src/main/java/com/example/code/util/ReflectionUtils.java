package com.example.code.util;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Created by yummyLau on 17-4-24
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ReflectionUtils {

    private static final String TAG = ReflectionUtils.class.getSimpleName();

    public static Object invokeMethod(Object obj, String methodName, Object... params) {
        if (obj == null || TextUtils.isEmpty(methodName)) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        try {
            Class<?>[] paramTypes = null;
            if (params != null && params.length > 0) {
                paramTypes = new Class[params.length];
                for (int i = 0; i < params.length; ++i) {
                    paramTypes[i] = params[i].getClass();
                }
            }
            Method method = clazz.getMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(obj, params);
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "#invokeMethod " + methodName + " not found in " + obj.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null || TextUtils.isEmpty(fieldName)) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        while (clazz != Object.class) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
        }
        Log.e(TAG, "#getFieldValue " + fieldName + " not found in " + obj.getClass().getName());
        return null;
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) {
        if (obj == null || TextUtils.isEmpty(fieldName)) {
            return;
        }

        Class<?> clazz = obj.getClass();
        while (clazz != Object.class) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, value);
                return;
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
        }
        Log.e(TAG, "#setFieldValue " + fieldName + " not found in " + obj.getClass().getName());
    }
}
