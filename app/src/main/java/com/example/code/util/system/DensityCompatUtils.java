package com.example.code.util.system;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * 适配所有机型
 * http://www.jcodecraeer.com/plus/view.php?aid=10448
 * <p>
 * PPI 每英寸上有多少个像素
 * DPI 每英寸上有多少个点
 * Created by yummyLau on 2018/4/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class DensityCompatUtils {

    private static float sNonCompatDensity;
    private static float sNonCompatScaledDensity;
    private static final int DESIGN_SOURCE_WIDTH_DP = 360;          //设计稿基准dp

    public static void registerDensityCompat(final @NonNull Application application) {
        if (application == null) {
            throw new IllegalArgumentException("application can't be null!");
        }

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                hookDensityForCompat(application, activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                //nothing to do
            }

            @Override
            public void onActivityResumed(Activity activity) {
                //nothing to do
            }

            @Override
            public void onActivityPaused(Activity activity) {
                //nothing to do
            }

            @Override
            public void onActivityStopped(Activity activity) {
                //nothing to do
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                //nothing to do
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                //nothing to do
            }
        });
    }


    private static void hookDensityForCompat(@NonNull Application application, @NonNull Activity activity) {

        if (application == null) {
            throw new IllegalArgumentException("application can't be null!");
        }

        if (activity == null) {
            throw new IllegalArgumentException("activity can't be null!");
        }

        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNonCompatDensity == 0) {
            sNonCompatDensity = appDisplayMetrics.density;
            sNonCompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });

        }

        final float targetDensity = appDisplayMetrics.widthPixels / DESIGN_SOURCE_WIDTH_DP;
        final float targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
