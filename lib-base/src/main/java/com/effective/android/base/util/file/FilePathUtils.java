package com.effective.android.base.util.file;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

/**
 * 文件目录管理
 * getCacheDir 和  getFilesDir 分别对应运行时手机数据保存使用
 * getExternalCacheDir 和  getExternalFilesDir 一般用户主动保存的数据，该数据能保证写在的时候自动被删除，所以一般数据不要自己在sd卡上建立目录，遵循规范。
 * getExternalCacheDir 对应应用详情中的 "清除缓存"
 * getExternalFilesDir 对应应用详情中的 "清除数据"
 * <p>
 * Created by yummyLau on 2018/8/14.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class FilePathUtils {

    /**
     * @return /data
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * @return /cache
     */
    public static String getCachePath() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    /**
     * @return /mnt/sdcard
     */
    public static String getSdcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * @param publicDirectory test
     * @return /mnt/sdcard/test
     */
    public static String getSdcardPath(String publicDirectory) {
        if (TextUtils.isEmpty(publicDirectory)) {
            return getSdcardPath();
        }
        return Environment.getExternalStoragePublicDirectory(publicDirectory).getAbsolutePath();
    }

    /**
     * @return /system
     */
    public static String getSystemPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * @param context
     * @return /data/app/{package}.apk
     */
    public static String getPackagePath(Context context) {
        return context.getPackageResourcePath();
    }

    /**
     * @param context
     * @return /data/data/{package}/cache
     */
    public static String getAppCachePath(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * @param context
     * @return /data/data/{package}/files
     */
    public static String getAppFilesPath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * @param context
     * @param directory
     * @return /data/data/xxx/databases/{directory}
     */
    public static String getDatabasePath(Context context, String directory) {
        return context.getDatabasePath(directory).getAbsolutePath();
    }

    /**
     * @param context
     * @return /mnt/sdcard/Android/data/{package}/cache
     */
    public static String getExternalCachePath(Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * @param context
     * @return /mnt/sdcard/Android/data/{package}/files
     */
    public static String getExternalFilesPath(Context context) {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * @param context
     * @return /mnt/sdcard/Android/data/{package}/files/{directory}
     */
    public static String getExternalFilesPath(Context context, String directory) {
        return context.getExternalFilesDir(directory).getAbsolutePath();
    }
}
