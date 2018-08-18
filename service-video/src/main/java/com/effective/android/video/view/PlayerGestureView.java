package com.effective.android.video.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.effective.android.video.annotations.GestureAction;
import com.effective.android.video.interfaces.support.IGestureAction;


/**
 * 手势层
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class PlayerGestureView extends FrameLayout implements View.OnTouchListener {

    private View volumeLayout;
    private ImageView volumeImg;
    private SeekBar volumeProgress;

    private View brightLayout;
    private ImageView brightImg;
    private SeekBar brightProgress;

    private View progressLayout;
    private TextView progressTime;
    private SeekBar progress;

    private Context mContext;
    private GestureDetector mGestureDetector;
    private IGestureAction mIGestureAction;

    private static final int MODIFY_LARGE_UNIT = 1000;

    private boolean startModifyProgress;
    private long currentModifyProgress;
    private long offsetModifyProgress;
    private int currentModifyVolume;

    @GestureAction
    private int action = GestureAction.MODIFY_NONE;


    public PlayerGestureView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerGestureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public PlayerGestureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(attrs, defStyleAttr);
    }


    private void initView(AttributeSet attrs, int defStyleAttr) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.exoplayer_player_gesture_view_layout, this, true);

        volumeLayout = root.findViewById(R.id.volume_layout);
        volumeImg = root.findViewById(R.id.volume_img);
        volumeProgress = root.findViewById(R.id.volume_progress);

        brightLayout = root.findViewById(R.id.bright_layout);
        brightImg = root.findViewById(R.id.bright_img);
        brightProgress = root.findViewById(R.id.bright_progress);

        progressLayout = root.findViewById(R.id.progress_layout);
        progressTime = root.findViewById(R.id.progress_time);
        progress = root.findViewById(R.id.progress);

        setLongClickable(true);
        setOnTouchListener(this);
        mGestureDetector = new GestureDetector(mContext, new GestureListener());
    }

    public void setGestureAction(IGestureAction IGestureAction) {
        mIGestureAction = IGestureAction;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private boolean consumeFirstDown = false;

        @Override
        public boolean onDown(MotionEvent e) {
            consumeFirstDown = true;
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (mIGestureAction != null) {
                if (mIGestureAction.onLongClick()) {
                    return;
                }
            }
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (mIGestureAction != null) {
                return mIGestureAction.onDoubleClick();
            }
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mIGestureAction != null) {
                return mIGestureAction.onSingleClick();
            }
            return super.onSingleTapConfirmed(e);
        }

        /**
         * 理解onScroll 在滑动过程中是离散调用的
         * 一次长滑动，多次distanceX 和 多次distanceY 相加会得到 最有一次e2和e1的对比结果
         * 规则：
         * 1. 横向的距离变化大则调整进度，纵向的变化大则调整音量亮度
         * 2. 音量和亮度的手势范围各占播放器一半
         *
         * @param e1        开始滑动坐标点
         * @param e2        结束滑动坐标点
         * @param distanceX 每次离散调用时的x滑动距离 distanceX>0 向左滑， distanceX<0 向右滑
         * @param distanceY 每次离散调用的的y滑动距离，distanceY>0 向上滑，distanceY<0 向下滑
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float startX = e1.getRawX();
            float startY = e1.getRawY();
            float endX = e2.getRawX();
            float endY = e2.getRawY();

            int areaWidth = getMeasuredWidth();
            int areaHeight = getMeasuredHeight();

            if (consumeFirstDown) {
                if (Math.abs(distanceX) >= Math.abs(distanceY)) {       //进度调整
                    action = GestureAction.MODIFY_PROGRESS;
                } else {
                    if (startX > areaWidth * 1 / 2) {                  // 音量
                        action = GestureAction.MODIFY_VOLUME;
                    } else if (startX < areaWidth * 1 / 2) {           // 亮度
                        action = GestureAction.MODIFY_BRIGHT;
                    }
                }
            }

            switch (action) {
                case GestureAction.MODIFY_PROGRESS: {
                    if (Math.abs(distanceX) > Math.abs(distanceY)) {

                        if (mIGestureAction == null) {
                            return false;
                        }

                        long duration = mIGestureAction.getDuration();
                        long currentPosition = mIGestureAction.getCurrentPosition();
                        if (duration == 0) {
                            return false;
                        }
                        float unitTimePx = duration * 1.0f / areaWidth;                             //计算单位像素滑动视频多长
                        offsetModifyProgress = (long) (-distanceX * unitTimePx);

                        if (!startModifyProgress) {
                            currentModifyProgress = currentPosition + offsetModifyProgress;
                            startModifyProgress = true;
                        } else {
                            currentModifyProgress += offsetModifyProgress;
                        }

                        if (currentModifyProgress > duration) {
                            currentModifyProgress = duration;
                        }

                        if (currentModifyProgress < 0) {
                            currentModifyProgress = 0;
                        }

                        progressTime.setText(time2Str(currentModifyProgress) + " / " + time2Str(duration));
                        progress.setProgress((int) (currentModifyProgress * 100 / duration));
                    }
                    break;
                }


                case GestureAction.MODIFY_VOLUME: {
                    if (Math.abs(distanceY) > Math.abs(distanceX)) {

                        int maxVolume = AudioSettingUtils.getMaxVolume(getContext(), AudioSettingUtils.AudioType.MUSIC);
                        int currentVolume = AudioSettingUtils.getCurrentVolume(getContext(), AudioSettingUtils.AudioType.MUSIC);

                        int largeMaxVolume = maxVolume * MODIFY_LARGE_UNIT;
                        int largeCurrentVolume = currentVolume * MODIFY_LARGE_UNIT + currentModifyVolume;
                        float unitVolumePx = largeMaxVolume * 1.0f / areaHeight;

                        largeCurrentVolume += unitVolumePx * distanceY;

                        if (largeCurrentVolume > largeMaxVolume) {
                            largeCurrentVolume = largeMaxVolume;
                        }
                        if (largeCurrentVolume < 0) {
                            largeCurrentVolume = 0;
                        }

                        currentModifyVolume = largeCurrentVolume % MODIFY_LARGE_UNIT;
                        int realVolume = largeCurrentVolume / MODIFY_LARGE_UNIT;

                        volumeProgress.setProgress(largeCurrentVolume * 100 / largeMaxVolume);
                        volumeImg.setImageResource(volumeProgress.getProgress() == 0 ?
                                R.drawable.exoplayer_control_disable_volume_icon : R.drawable.exoplayer_control_volume_icon);
                        AudioSettingUtils.setCurrentVolume(getContext(), AudioSettingUtils.AudioType.MUSIC, realVolume, 0);
                    }
                    break;
                }

                case GestureAction.MODIFY_BRIGHT: {
                    if (Math.abs(distanceY) > Math.abs(distanceX)) {

                        //去除系统默认设置
                        Activity activity = ActivityUtils.scanForActivity(getContext());
                        if (activity == null) {
                            return false;
                        }
                        float brightness = BrightnessSettingUtils.getWindowBrightness(activity);
                        if (brightness < 0) {
                            brightness = BrightnessSettingUtils.getScreenBrightness(activity) * 1.0f / 255;
                        }

                        brightness = brightness + distanceY * 1.0f / areaHeight;
                        if (brightness > 1.0f) {
                            brightness = 1.0f;
                        } else if (brightness < 0.01f) {
                            brightness = 0.00f;
                        }

                        brightProgress.setProgress((int) (brightness * 100));
                        BrightnessSettingUtils.setWindowBrightness(activity, brightness);
                    }
                    break;
                }
            }

            return true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //处理up事件
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                if (action == GestureAction.MODIFY_PROGRESS) {
                    mIGestureAction.seekToPosition(currentModifyProgress);
                }
                action = GestureAction.MODIFY_NONE;
                currentModifyProgress = 0;
                offsetModifyProgress = 0;
                currentModifyVolume = 0;
                startModifyProgress = false;
                volumeLayout.setVisibility(View.GONE);
                brightLayout.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                break;
            }
        }
        return mGestureDetector.onTouchEvent(event);
    }

    private String time2Str(long time) {
        if (time < 0) {
            return "00:00";
        }
        int second = (int) time / 1000;
        int hour = second / 3600;
        int min = (second % 3600) / 60;
        second = (second % 60);
        final String hourStr = hour < 10 ? "0" + String.valueOf(hour) : String.valueOf(hour);
        final String minStr = min < 10 ? "0" + String.valueOf(min) : String.valueOf(min);
        final String secondStr = second < 10 ? "0" + String.valueOf(second) : String.valueOf(second);
        String result;
        if (hour != 0) {
            result = hourStr + ":" + minStr + ":" + secondStr;
        } else if (min != 0) {
            result = minStr + ":" + secondStr;
        } else {
            result = "00:" + secondStr;
        }
        return result;
    }
}
