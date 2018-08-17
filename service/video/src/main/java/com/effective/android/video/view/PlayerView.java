package com.effective.android.video.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.FrameLayout;


import com.effective.android.video.DefaultAnalyticsListener;
import com.effective.android.video.R;
import com.effective.android.video.annotations.VideoStatus;
import com.effective.android.video.interfaces.support.IControlAction;
import com.effective.android.video.interfaces.support.IControlView;
import com.effective.android.video.interfaces.support.IGestureAction;
import com.google.android.exoplayer2.ExoPlaybackException;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;


/**
 * 播放器View层
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class PlayerView extends FrameLayout {

    private com.google.android.exoplayer2.ui.PlayerView mExoPlayerView;
    private PlayerGestureView mPlayerGestureView;
    private PlayerControlView mPlayerControlView;

    private IControlView mControlView;

    private SimpleExoPlayer mExoPlayer;
    private Context mContext;

    @VideoStatus
    private int videoStatus = VideoStatus.BUFFERING;

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
        mPlayerGestureView = root.findViewById(R.id.player_control_view);
        mPlayerGestureView.setGestureAction(defaultGestureAction);
        mPlayerControlView = root.findViewById(R.id.player_gesture_view);
        mPlayerControlView.setControlAction(defaultControlAction);
    }

    public void attachPlayer(SimpleExoPlayer simpleExoPlayer) {
        mExoPlayer = simpleExoPlayer;
        mExoPlayerView.setPlayer(mExoPlayer);
        mExoPlayer.addAnalyticsListener(defaultAnalyticsListener);
        onVideoStatus(VideoStatus.PREPARE);
    }

    public void detachPlayer() {
        mExoPlayer.removeAnalyticsListener(defaultAnalyticsListener);
        mExoPlayer = null;
        mExoPlayerView.setPlayer(null);
        onVideoStatus(VideoStatus.NONE);
    }

    private void onVideoStatus(@VideoStatus int videoStatus) {
        switch (videoStatus) {
            case VideoStatus.NONE: {
                break;
            }
        }
    }

    private boolean isPlaying() {
        return videoStatus != VideoStatus.NONE && mExoPlayer.getPlayWhenReady();
    }

    private IControlAction defaultControlAction = new IControlAction() {
        @Override
        public void onBack() {

        }

        @Override
        public void onVolume() {

        }

        @Override
        public void onPlay() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onReplay() {

        }

        @Override
        public boolean onFullScreen() {
            return false;
        }
    };

    private IGestureAction defaultGestureAction = new IGestureAction() {
        @Override
        public boolean onLongClick() {
            return false;
        }

        @Override
        public boolean onSingleClick() {
            if (videoStatus != VideoStatus.NONE) {
                if (mControlView != null) {
                    mControlView.onControlVisibleChange();
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onDoubleClick() {
            if (videoStatus != VideoStatus.NONE) {
                if (isPlaying()) {
                    mExoPlayer.setPlayWhenReady(true);
                    if (mControlView != null) {
                        mControlView.onPlay();
                    }
                } else {
                    mExoPlayer.setPlayWhenReady(false);
                    if (mControlView != null) {
                        mControlView.onPause();
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public void seekToPosition(long position) {
            if (videoStatus != VideoStatus.NONE) {
                mExoPlayer.seekTo(position);
            }
        }

        @Override
        public long getDuration() {
            if (videoStatus != VideoStatus.NONE) {
                mExoPlayer.getDuration();
            }
            return 0;
        }

        @Override
        public long getCurrentPosition() {
            if (videoStatus != VideoStatus.NONE) {
                mExoPlayer.getCurrentPosition();
            }
            return 0;
        }
    };

    private DefaultAnalyticsListener defaultAnalyticsListener = new DefaultAnalyticsListener() {
        private boolean hasReady = false;


        @Override
        public void onLoadingChanged(EventTime eventTime, boolean isLoading) {
            super.onLoadingChanged(eventTime, isLoading);
            if (videoStatus != VideoStatus.NONE) {
                if (mControlView != null) {
                    mControlView.updateBufferPosition(mExoPlayer.getBufferedPosition());
                }
            }
        }

        @Override
        public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
            super.onPlayerStateChanged(eventTime, playWhenReady, playbackState);
            switch (playbackState) {
                case Player.STATE_BUFFERING: {
                    onVideoStatus(VideoStatus.BUFFERING);
                    break;
                }
                case Player.STATE_READY: {
                    if (!hasReady && playWhenReady) {
                        hasReady = true;
                        if (mControlView != null) {
                            mControlView.setupDuration(mExoPlayer.getDuration());
                        }
                    }
                    if (playWhenReady) {
                        onVideoStatus(VideoStatus.PLAYING);
                    } else {
                        onVideoStatus(VideoStatus.PAUSE);
                    }
                    break;
                }
                case Player.STATE_ENDED: {
                    onVideoStatus(VideoStatus.FINISHED);
                    break;
                }
            }
        }

        @Override
        public void onPlayerError(EventTime eventTime, ExoPlaybackException error) {
            super.onPlayerError(eventTime, error);
            onVideoStatus(VideoStatus.ERROR);
        }
    };
}
