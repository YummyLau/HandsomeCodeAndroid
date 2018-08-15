package com.effective.android.crash;

import android.app.Application;

import com.effective.android.base.util.file.SdcardUtils;
import com.effective.android.component.crash.ICrashComponent;

/**
 * crash组件注册管理
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2018/01/25.
 */
public class CrashComponentImpl implements ICrashComponent {

    @Override
    public String getCrashLogPath() {
        return SdcardUtils.getRootDirectoryPath();
    }

    @Override
    public void createAsLibrary(Application application) {
        CrashHandler.getInstance().init(application);
    }

    @Override
    public void release() {

    }
}
