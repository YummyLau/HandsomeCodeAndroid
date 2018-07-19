package com.example.code.keeplive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.code.R;
import com.example.code.base.BaseActivity;
import com.example.code.databinding.ActivityKeepaliveLayoutBinding;
import com.example.code.keeplive.notification.KeepLiveService;

/**
 * Android一般的进程优先级划分：
 * 1.前台进程 (Foreground process)
 * 2.可见进程 (Visible process)
 * 3.服务进程 (Service process)
 * 4.后台进程 (Background process)
 * 5.空进程 (Empty process)
 *
 * 	UNKNOWN_ADJ	            16	预留的最低级别，一般对于缓存的进程才有可能设置成这个级别
 * 	CACHED_APP_MAX_ADJ	    15	缓存进程，空进程，在内存不足的情况下就会优先被kill
 * 	CACHED_APP_MIN_ADJ	    9	缓存进程，也就是空进程
 * 	SERVICE_B_ADJ	        8	不活跃的进程
 * 	PREVIOUS_APP_ADJ	    7	切换进程
 * 	HOME_APP_ADJ	        6	与Home交互的进程
 * 	SERVICE_ADJ	            5	有Service的进程
 * 	HEAVY_WEIGHT_APP_ADJ	4	高权重进程
 * 	BACKUP_APP_ADJ	        3	正在备份的进程
 * 	PERCEPTIBLE_APP_ADJ	    2	可感知的进程，比如那种播放音乐
 * 	VISIBLE_APP_ADJ	        1	可见进程
 * 	FOREGROUND_APP_ADJ	    0	前台进程
 * 	PERSISTENT_SERVICE_ADJ	-11	重要进程
 * 	PERSISTENT_PROC_ADJ	    -12	核心进程
 * 	SYSTEM_ADJ	            -16	系统进程
 * 	NATIVE_ADJ	            -17	系统起的Native进程
 *
 * <p>
 * adb shell ps | grep packageName
 * adb shell cat proc/{PID}/oom_adj 数值越大优先级越低
 * <p>
 *
 * 方式：
 * 1. 监听锁屏和解锁，使用1pxactivity
 *
 * 2. 7.0以下可启动service绑定前台服务之后，再启动一个service绑定同一个前台服务后杀死
 *
 * 3. jobservice 5.0以上可以处理
 *
 * 4. 双service进程包活：一个死另一个拉，5.0杀死进程时
 *  Process.killProcessQuiet(app.pid);
 *  Process.killProcessGroup(app.info.uid, app.pid);
 *  主进程的进程组页一并杀死。
 *
 * Created by yummyLau on 2018/7/19.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class KeepLiveDemoActivity extends BaseActivity<ActivityKeepaliveLayoutBinding> {

    public static void start(Context context) {
        Intent intent = new Intent(context, KeepLiveDemoActivity.class);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.activity_keepalive_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.notificationKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(KeepLiveDemoActivity.this, KeepLiveService.class));
            }
        });
        binding.onePxKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(KeepLiveDemoActivity.this, com.example.code.keeplive.onepx.KeepLiveService.class));
            }
        });
    }
}
