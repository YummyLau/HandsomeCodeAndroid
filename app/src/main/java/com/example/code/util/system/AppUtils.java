package com.example.code.util.system;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.exoplayer2.C;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理app业务的utils
 * Created by yummyLau on 2018/4/17.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();
    public static final int INVALID_VERSION_CODE = -1;
    public static final String INVALID_VERSION_NAME = "invalid_version_name";
    public static final String INVALID_APP_NAME = "unknown_name";

    /**
     * 封装App信息的Bean类
     */
    public static class AppInfo {
        private String name;                    //名称
        private Drawable icon;                  //图标
        private String packageName;             //包名
        private String versionName;             //版本号
        private int versionCode;                //版本Code
        private boolean isInstalledAtSdcard;    //是否安装在SD卡
        private boolean isUserApplication;      //是否是用户程序

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getVersionName() {
            return versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public boolean isInstalledAtSdcard() {
            return isInstalledAtSdcard;
        }

        public boolean isUserApplication() {
            return isUserApplication;
        }

        private AppInfo(PackageManager packageManager, PackageInfo packageInfo) {
            ApplicationInfo ai = packageInfo.applicationInfo;
            name = ai.loadLabel(packageManager).toString();
            icon = ai.loadIcon(packageManager);
            packageName = packageInfo.packageName;
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
            isInstalledAtSdcard = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != ApplicationInfo.FLAG_SYSTEM;
            isUserApplication = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != ApplicationInfo.FLAG_SYSTEM;
        }
    }

    /**
     * 获取包信息
     *
     * @param context
     * @return
     */
    public static final PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     * 获取app信息
     *
     * @param context
     * @return
     */
    public static AppInfo getAppInfo(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        PackageManager packageManager = context.getPackageManager();
        return packageInfo != null ? new AppInfo(packageManager, packageInfo) : null;
    }

    /**
     * 获取手机上所有已安装app的信息
     *
     * @param context
     * @return
     */
    public static List<AppInfo> getAllAppsInfo(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : installedPackages) {
            if (packageInfo != null) {
                list.add(new AppInfo(packageManager, packageInfo));
            }
        }
        return list;
    }

    /**
     * 判断是否安装某个包
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            if (TextUtils.isEmpty(packageName)) {
                return false;
            }
            return context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES) != null;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            return false;
        }
    }

    /**
     * 根据包名打开app
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean openAppByPackageName(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 打开指定包名的App应用信息界面
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void openAppInfoPage(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(intent);
    }


    /**
     * 安装apk文件
     *
     * @param context
     * @param filepath
     */
    public static void installApk(Context context, String filepath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(filepath);
        if (file != null) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } else {
            Log.e(TAG, "apk file no found!");
        }
    }

    /**
     * 卸载指定包名的App
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public void uninstallApp(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
