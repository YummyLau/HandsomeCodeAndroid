package com.effective.android.base.crash;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.effective.android.base.R;
import com.effective.android.base.util.file.FilePathUtils;
import com.effective.android.base.util.system.AppUtils;
import com.effective.android.base.util.system.DeviceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * crash信息收集
 * Created by ${yummyLau} on 2018/4/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = CrashHandler.class.getSimpleName();
    private volatile static CrashHandler instance;

    private Context mContext;
    private String mCrashLogPath;
    private Thread.UncaughtExceptionHandler mDefaultHandler;                        //系统默认的UncaughtException处理类
    private Map<String, String> mInfoMap = new HashMap<>();                         //用来存储设备信息和异常信息
    private DateFormat mDateFormat;


    private CrashHandler() {
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 获取系统默认的UncaughtException处理器
     * 设置该CrashHandler为程序的默认处理器
     *
     * @param context 上下文
     */
    public void init(Context context, String crashLogPath) {
        mContext = context;
        mCrashLogPath = crashLogPath;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     * 如果用户没有处理则默认系统处理异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : " + e.getMessage());
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理
     * 1. 收集crash日志到服务器
     * 2. toast提示客户端
     * 3. 保存crash日志
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Toast.makeText(mContext, R.string.crash_for_tip, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }.start();

        saveCrashInfo2File(mContext, ex);
        return true;
    }

    private void addLineLog(StringBuilder builder, String key, String value) {
        if (builder != null) {
            builder.append(key);
            builder.append(" : ");
            builder.append(value);
            builder.append("\r\n");
        }
    }

    private void addLineLog(StringBuilder builder, String value) {
        if (builder != null) {
            builder.append(value);
            builder.append("\r\n");
        }
    }


    /**
     * 保存错误信息到文件中
     *
     * @param context
     * @param throwable
     */
    private void saveCrashInfo2File(Context context, Throwable throwable) {

        StringBuilder stringBuilder = new StringBuilder();

        long timestamp = System.currentTimeMillis();
        String time = mDateFormat.format(new Date());

        addLineLog(stringBuilder, "CRASH_TIME", time + "(" + timestamp + ")");

        AppUtils.AppInfo appInfo = AppUtils.getAppInfo(context);
        if (appInfo != null) {
            addLineLog(stringBuilder, "VERSION_CODE", String.valueOf(appInfo.getVersionCode()));
            addLineLog(stringBuilder, "VERSION_NAME", appInfo.getVersionName());
            addLineLog(stringBuilder, "PACKAGE_NAME", appInfo.getPackageName());
        }
        addLineLog(stringBuilder, "OS_VERSION", DeviceUtils.getOS());

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                addLineLog(stringBuilder, field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occurred when collect crash info");
            }
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        addLineLog(stringBuilder, writer.toString());


        try {
            String fileName = "crash-" + time + "-" + timestamp + ".txt";
            if (TextUtils.isEmpty(mCrashLogPath)) {
                mCrashLogPath = FilePathUtils.getExternalFilesPath(context, "crash");
            }
            //如果没有这个目录，则创建这个目录
            File crashCacheDir = new File(mCrashLogPath);
            if (!crashCacheDir.exists()) {
                crashCacheDir.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(mCrashLogPath + "/" + fileName);
            fos.write(stringBuilder.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "an error occurred while writing file...");
        }
    }
}

