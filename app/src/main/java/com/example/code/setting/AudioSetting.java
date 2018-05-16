package com.example.code.setting;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 音量管理
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class AudioSetting {

    @IntDef({AudioType.MUSIC, AudioType.CALL, AudioType.SYSTEM,
            AudioType.RING, AudioType.ALARM, AudioType.NOTIFICATION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioType {
        int MUSIC = AudioManager.STREAM_MUSIC;
        int CALL = AudioManager.STREAM_VOICE_CALL;
        int SYSTEM = AudioManager.STREAM_SYSTEM;
        int RING = AudioManager.STREAM_RING;
        int ALARM = AudioManager.STREAM_ALARM;
        int NOTIFICATION = AudioManager.STREAM_NOTIFICATION;
    }

    public static int getMaxVolume(Context context, @AudioType int type) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getStreamMaxVolume(type);
    }

    public static int getCurrentVolume(Context context, @AudioType int type) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getStreamVolume(type);
    }

    public static void setCurrentVolume(Context context, @AudioType int type, int index, int flags) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(type, index, flags);
    }

    /**
     * @param context
     * @param type
     * @param direction ADJUST_LOWER 降低
     *                  ADJUST_RAISE 升高
     *                  ADJUST_SAME 保持不变
     * @param flags     FLAG_PLAY_SOUND 调整音量时播放声音
     *                  FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个
     *                  0 表示什么也没有
     */
    public static void adjustStreamVolume(Context context, @AudioType int type, int direction, int flags) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(type, direction, flags);
    }
}
