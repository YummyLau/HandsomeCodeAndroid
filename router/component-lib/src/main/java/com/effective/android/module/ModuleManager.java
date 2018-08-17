package com.effective.android.module;


import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一模块化入口
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2018/01/25.
 */

public class ModuleManager {

    private static final String TAG = ModuleManager.class.getSimpleName();

    private static Map<String, IModule> sServiceMap = new HashMap<>();

    public static <T extends IModule> void register(@NonNull Application application, @NonNull Class<T> module) {
        IModule registerService = null;
        try {
            Constructor constructor = module.getConstructor();
            registerService = (IModule) constructor.newInstance();
        } catch (Exception e) {
            Log.e(TAG, "bind fail!:" + e.getMessage());
        }
        Class IServiceImpl = ifClassImplementsIService(module.getInterfaces());
        if (IServiceImpl != null) {
            sServiceMap.put(IServiceImpl.getSimpleName(), registerService);
            registerService.createAsLibrary(application);
        } else {
            Log.e(TAG, "IModule is not module's grandfather!");
        }
    }


    public static Class ifClassImplementsIService(Class[] interfaces) {
        int length = interfaces.length;
        int i = 0;
        while (i < length) {
            Class result = ifClassImplementsIService(interfaces[i]);
            if (result != null) {
                return result;
            }
            if (ifClassHadImplementsInterface(interfaces[i])) {
                return ifClassImplementsIService(interfaces[i].getInterfaces());
            }
            i++;
        }
        return null;
    }

    public static boolean ifClassHadImplementsInterface(Class clazz) {
        boolean result = false;
        if (clazz != null && clazz.getInterfaces() != null && clazz.getInterfaces().length > 0) {
            result = true;
        }
        return result;
    }

    public static Class ifClassImplementsIService(Class interfaces) {
        Class[] classes = interfaces.getInterfaces();
        if (classes != null && classes.length > 0) {
            for (int i = 0; i < classes.length; i++) {
                if (classes[0].getSimpleName().equals(IModule.class.getSimpleName())) {
                    return interfaces;
                }
            }
        }
        return null;
    }


    @Nullable
    public static <T extends IModule> T getService(@NonNull Class<T> module) {
        IModule result = null;
        if (module != null) {
            result = sServiceMap.get(module.getSimpleName());
        }
        if (result == null) {
            Log.w(TAG, "has not module which match a modulePath key!");
        }
        return (T) result;
    }

    public static <T extends IModule> void unRegister(@NonNull Class<T> module) {
        if (module == null) {
            Log.w(TAG, "module class can't be null");
            return;
        }
        T toUnRegister = (T) sServiceMap.remove(module.getSimpleName());
        toUnRegister.release();
    }

}
