package com.example.code.exoplayer.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.code.R;

/**
 * 手势层
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class PlayerGestureView extends FrameLayout {

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

    }
}
