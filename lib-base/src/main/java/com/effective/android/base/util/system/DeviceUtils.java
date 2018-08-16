package com.effective.android.base.util.system;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * 设备相关的信息处理
 * Created by yummyLau on 2018/4/17.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class DeviceUtils {

    /**
     * 获取系统
     * @return
     */
    public static final String getOS() {
        return Build.VERSION.RELEASE;
    }

    public static final String getPhoneModelWithManufacturer() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    public static final String getPhoneMode() {
        return Build.MODEL;
    }

    /**
     * 获取CPU名称
     * @return
     */
    public static String getCpuName() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fr, br);
        }
        return null;
    }

    /**
     * 实时获取CPU当前频率（单位KHZ）
     * @return
     */
    public static int getCpuFreq() {
        int result = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = Integer.parseInt(text.trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fr, br);
        }
        return result;
    }

    /**
     * 获取指定CPU最小频率（单位KHZ）
     * @return
     */
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 实时CPU最大频率（单位KHZ）
     * @return
     */
    public static int getMaxCpuFreq() {
        int result = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = Integer.parseInt(text.trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fr, br);
        }

        return result;
    }

    /**
     * 获取CPU核数
     * @return
     */
    public static int getCPUCores() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            // Gingerbread doesn't support giving a single application access to both cores, but a
            // handful of devices (Atrix 4G and Droid X2 for example) were released with a dual-core
            // chipset and Gingerbread; that can let an app in the background run without impacting
            // the foreground application. But for our purposes, it makes them single core.
            return 1;
        }
        int cores = 0;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();
            //regex is slow, so checking char by char.
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };

    private static void close(FileReader fr, BufferedReader br) {
        if (fr != null) {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否是小米
     * @return
     */
    public static boolean isMiui(){
        return Build.MANUFACTURER.contains("Xiaomi");
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 android.permission.ACCESS_WIFI_STATE</p>
     *
     * @param context 上下文
     * @return MAC地址
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress = info.getMacAddress().replace(":", "");
        return macAddress == null ? "" : macAddress;
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 android.permission.ACCESS_WIFI_STATE</p>
     *
     * @return MAC地址
     */
    public static String getMacAddress() {
        String macAddress = null;
        LineNumberReader reader = null;
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            reader = new LineNumberReader(ir);
            macAddress = reader.readLine().replace(":", "");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return macAddress == null ? "" : macAddress;
    }

    /**
     * 获取设备厂商，如Xiaomi
     *
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号，如MI2SC
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }
}
