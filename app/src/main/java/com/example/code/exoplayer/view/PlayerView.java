package com.example.code.exoplayer.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.code.R;
import com.google.android.exoplayer2.ExoPlayer;

/**
 * 播放器View层
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class PlayerView extends FrameLayout {

    private PlayerView mExoPlayerView;
    private ExoPlayer mExoPlayer;
    private Context mContext;

    public PlayerView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.exoplayer_player_view_layout, this, true);
        mExoPlayerView = root.findViewById(R.id.player_view);
    }
}
