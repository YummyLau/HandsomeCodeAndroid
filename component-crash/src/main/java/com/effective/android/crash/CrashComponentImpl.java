package com.effective.android.crash;

import android.app.Application;
import android.content.Context;

import com.effective.android.base.util.file.FilePathUtils;
import com.effective.android.base.util.file.SdcardUtils;
import com.effective.android.component.crash.ICrashComponent;

/**
 * crash组件注册管理
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2018/01/25.
 */
public class CrashComponentImpl implements ICrashComponent {

    private Application application;

    @Override
    public String getCrashLogPath() {
        if (SdcardUtils.isSDCardEnable()) {
            return FilePathUtils.getExternalFilesPath(application, "crash");
        } else {
            return FilePathUtils.getAppFilesPath(application);
        }
    }

    @Override
    public void createAsLibrary(Application application) {
        this.application = application;
        CrashHandler.getInstance().init(application, getCrashLogPath());
    }

    @Override
    public void release() {
        application = null;
    }

}
