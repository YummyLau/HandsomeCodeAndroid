package com.effective.android.video.view;


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

import com.effective.android.video.R;
import com.effective.android.video.interfaces.support.IControlAction;


/**
 * 控制层View
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class PlayerControlView extends FrameLayout implements View.OnClickListener {

    private Context mContext;

    private View coverLayout;
    private ImageView cover;

    private View topLayout;
    private ImageView back;
    private ImageView volume;

    private View bottomLayout;
    private ImageView play;
    private TextView currentPosition;
    private TextView duration;
    private ImageView controlFullscreen;
    private SeekBar progress;

    private View midLayout;
    private ImageView status;
    private TextView statusTip;

    private IControlAction mIControlAction;


    public PlayerControlView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(attrs, defStyleAttr);
        initListener();
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.exoplayer_player_control_view_layout, this, true);

        coverLayout = root.findViewById(R.id.cover_layout);
        cover = root.findViewById(R.id.cover);

        topLayout = root.findViewById(R.id.top_layout);
        back = root.findViewById(R.id.top_back);
        volume = root.findViewById(R.id.top_volume);

        bottomLayout = root.findViewById(R.id.bottom_layout);
        play = root.findViewById(R.id.bottom_play);
        currentPosition = root.findViewById(R.id.bottom_current_position);
        duration = root.findViewById(R.id.bottom_duration);
        controlFullscreen = root.findViewById(R.id.bottom_control_fullscreen);
        progress = root.findViewById(R.id.bottom_progress);

        midLayout = root.findViewById(R.id.mid_layout);
        status = root.findViewById(R.id.mid_status);
        statusTip = root.findViewById(R.id.mid_status_tip);
    }

    public void setControlAction(IControlAction IControlAction) {
        mIControlAction = IControlAction;
    }

    private void initListener() {
        back.setOnClickListener(this);
        volume.setOnClickListener(this);
        play.setOnClickListener(this);
        controlFullscreen.setOnClickListener(this);
        status.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back: {
                break;
            }
            case R.id.top_volume: {
                break;
            }
            case R.id.bottom_play: {
                break;
            }
            case R.id.bottom_control_fullscreen: {
                break;
            }
            case R.id.mid_status: {
                break;
            }
        }
    }
}
