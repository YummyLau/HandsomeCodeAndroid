package com.example.code.exoplayer.view;

import android.content.Context;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;

import com.example.code.R;
import com.example.code.exoplayer.DefaultPlayerEventListener;
import com.example.code.exoplayer.annotations.VideoStatus;
import com.example.code.exoplayer.interfaces.support.IControlAction;
import com.example.code.exoplayer.interfaces.support.IControlView;
import com.example.code.exoplayer.interfaces.support.IGestureAction;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.io.IOException;

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
        mPlayerControlView = root.findViewById(R.id.player_gesture_view);
    }

    public void attachPlayer(SimpleExoPlayer simpleExoPlayer) {
        mExoPlayer = simpleExoPlayer;
        mExoPlayerView.setPlayer(mExoPlayer);
        mExoPlayer.addListener(defaultPlayerEventListener);
        mExoPlayer.addTextOutput(defaultPlayerEventListener);
        mExoPlayer.addVideoListener(defaultPlayerEventListener);
        mExoPlayer.addMetadataOutput(new MetadataOutput() {
            @Override
            public void onMetadata(Metadata metadata) {

            }
        });
        mExoPlayer.addAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {

            }

            @Override
            public void onTimelineChanged(EventTime eventTime, int reason) {

            }

            @Override
            public void onPositionDiscontinuity(EventTime eventTime, int reason) {

            }

            @Override
            public void onSeekStarted(EventTime eventTime) {

            }

            @Override
            public void onSeekProcessed(EventTime eventTime) {

            }

            @Override
            public void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters) {

            }

            @Override
            public void onRepeatModeChanged(EventTime eventTime, int repeatMode) {

            }

            @Override
            public void onShuffleModeChanged(EventTime eventTime, boolean shuffleModeEnabled) {

            }

            @Override
            public void onLoadingChanged(EventTime eventTime, boolean isLoading) {

            }

            @Override
            public void onPlayerError(EventTime eventTime, ExoPlaybackException error) {

            }

            @Override
            public void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadStarted(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {

            }

            @Override
            public void onLoadCompleted(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {

            }

            @Override
            public void onLoadCanceled(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {

            }

            @Override
            public void onLoadError(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {

            }

            @Override
            public void onDownstreamFormatChanged(EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {

            }

            @Override
            public void onUpstreamDiscarded(EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {

            }

            @Override
            public void onMediaPeriodCreated(EventTime eventTime) {

            }

            @Override
            public void onMediaPeriodReleased(EventTime eventTime) {

            }

            @Override
            public void onReadingStarted(EventTime eventTime) {

            }

            @Override
            public void onBandwidthEstimate(EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {

            }

            @Override
            public void onViewportSizeChange(EventTime eventTime, int width, int height) {

            }

            @Override
            public void onNetworkTypeChanged(EventTime eventTime, @Nullable NetworkInfo networkInfo) {

            }

            @Override
            public void onMetadata(EventTime eventTime, Metadata metadata) {

            }

            @Override
            public void onDecoderEnabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {

            }

            @Override
            public void onDecoderInitialized(EventTime eventTime, int trackType, String decoderName, long initializationDurationMs) {

            }

            @Override
            public void onDecoderInputFormatChanged(EventTime eventTime, int trackType, Format format) {

            }

            @Override
            public void onDecoderDisabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {

            }

            @Override
            public void onAudioSessionId(EventTime eventTime, int audioSessionId) {

            }

            @Override
            public void onAudioUnderrun(EventTime eventTime, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {

            }

            @Override
            public void onDroppedVideoFrames(EventTime eventTime, int droppedFrames, long elapsedMs) {

            }

            @Override
            public void onVideoSizeChanged(EventTime eventTime, int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

            }

            @Override
            public void onRenderedFirstFrame(EventTime eventTime, Surface surface) {

            }

            @Override
            public void onDrmKeysLoaded(EventTime eventTime) {

            }

            @Override
            public void onDrmSessionManagerError(EventTime eventTime, Exception error) {

            }

            @Override
            public void onDrmKeysRestored(EventTime eventTime) {

            }

            @Override
            public void onDrmKeysRemoved(EventTime eventTime) {

            }
        });
        onVideoStatus(VideoStatus.PREPARE);
    }

    public void detachPlayer() {
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
            return false;
        }

        @Override
        public boolean onDoubleClick() {
            return false;
        }

        @Override
        public void seekToPosition(long position) {

        }

        @Override
        public long getDuration() {
            return 0;
        }

        @Override
        public long getCurrentPosition() {
            return 0;
        }
    };

    private DefaultPlayerEventListener defaultPlayerEventListener = new DefaultPlayerEventListener() {

        private boolean hasReady = false;

        @Override
        public void onLoadingChanged(boolean isLoading) {
            super.onLoadingChanged(isLoading);
            if (videoStatus != VideoStatus.NONE) {
                if (mControlView != null) {
                    mControlView.updateBufferPosition(mExoPlayer.getBufferedPosition());
                }
            }
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            super.onPlayerStateChanged(playWhenReady, playbackState);
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
        public void onPlayerError(ExoPlaybackException error) {
            super.onPlayerError(error);
            onVideoStatus(VideoStatus.ERROR);
        }
    };
}
