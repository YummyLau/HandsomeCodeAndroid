package com.effective.android.base.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.effective.android.base.R;
import com.effective.android.base.fragment.BaseFragment;
import com.effective.android.base.util.resource.ColorUtils;
import com.effective.android.base.util.system.StatusBarUtils;
import com.effective.android.skin.SkinUtils;

import java.util.List;

import skin.support.widget.SkinCompatSupportable;

/**
 * 状态栏着色activity
 * Created by yummyLau on 2018/5/21.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class ColorStatusBarActivity extends AppCompatActivity implements SkinCompatSupportable {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleStatusBar();
    }

    @CallSuper
    @Override
    public void applySkin() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof BaseFragment) {
                    ((BaseFragment) fragment).applySkin();
                }
            }
        }
    }

    /**
     * 处理状态栏
     */
    protected void handleStatusBar() {
        StatusBarUtils.transparent(this);
        if (StatusBarUtils.meetLightColor(SkinUtils.getColor(this, getThemeColor()))) {
            StatusBarUtils.setStatusBarLightMode(this);
        } else {
            StatusBarUtils.setStatusBarDarkMode(this);
        }
    }


    /**
     * 默认主题颜色
     *
     * @return
     */
    @ColorRes
    protected int getThemeColor() {
        return R.color.colorPrimary;
    }
}
