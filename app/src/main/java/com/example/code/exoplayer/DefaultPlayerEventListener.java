package com.example.code.exoplayer;

import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoListener;

import java.util.List;

/**
 * 覆盖原生exoplayer接口
 * Deprecated,user DefaultAnalyticsListener
 * Created by yummyLau on 2018/5/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@Deprecated
public class DefaultPlayerEventListener implements Player.EventListener, VideoListener, TextOutput, View.OnLayoutChangeListener {

    private static final String TAG = DefaultPlayerEventListener.class.getSimpleName();

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
        Log.d(TAG, "onTimelineChanged-周期总数： " + timeline);
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        Log.d(TAG, "TrackGroupArray-trackGroups： " + trackGroups);
        Log.d(TAG, "TrackGroupArray-TrackSelectionArray： " + trackGroups);
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.d(TAG, "onLoadingChanged-isLoading： " + isLoading);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_IDLE: {
                Log.d(TAG, "onLoadingChanged-playbackState： " + "STATE_IDLE  playWhenReady: " + playWhenReady);
                break;
            }
            case Player.STATE_BUFFERING: {
                Log.d(TAG, "onLoadingChanged-playbackState： " + "STATE_BUFFERING  playWhenReady: " + playWhenReady);
                break;
            }
            case Player.STATE_READY: {
                Log.d(TAG, "onLoadingChanged-playbackState： " + "STATE_READY  playWhenReady: " + playWhenReady);
                break;
            }
            case Player.STATE_ENDED: {
                Log.d(TAG, "onLoadingChanged-playbackState： " + "STATE_ENDED  playWhenReady: " + playWhenReady);
                break;
            }
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        switch (repeatMode) {
            case Player.REPEAT_MODE_ONE: {
                Log.d(TAG, "onRepeatModeChanged-repeatMode： " + "REPEAT_MODE_ONE");
                break;
            }
            case Player.REPEAT_MODE_OFF: {
                Log.d(TAG, "onLoadingChanged-repeatMode： " + "REPEAT_MODE_OFF");
                break;
            }
            case Player.REPEAT_MODE_ALL: {
                Log.d(TAG, "onLoadingChanged-repeatMode： " + "REPEAT_MODE_ALL");
                break;
            }
        }
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        Log.d(TAG, "onShuffleModeEnabledChanged-shuffleModeEnabled： " + shuffleModeEnabled);
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        switch (error.type) {
            case ExoPlaybackException.TYPE_SOURCE: {
                Log.d(TAG, "onPlayerError-error： " + "The error occurred loading data from a MediaSource");
                break;
            }
            case ExoPlaybackException.TYPE_RENDERER: {
                Log.d(TAG, "onPlayerError-error： " + "The error occurred in a Renderer");
                Log.d(TAG, "onPlayerError-error： " + "Renderer index is " + error.rendererIndex);
                break;
            }
            case ExoPlaybackException.TYPE_UNEXPECTED: {
                Log.d(TAG, "onPlayerError-error： " + "The error was an unexpected RuntimeException");
                break;
            }
        }
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        switch (reason) {
            case Player.DISCONTINUITY_REASON_PERIOD_TRANSITION: {
                Log.d(TAG, "onPositionDiscontinuity-reason： " + "DISCONTINUITY_REASON_PERIOD_TRANSITION");
                break;
            }
            case Player.DISCONTINUITY_REASON_SEEK: {
                Log.d(TAG, "onPositionDiscontinuity-reason： " + "DISCONTINUITY_REASON_SEEK");
                break;
            }
            case Player.DISCONTINUITY_REASON_SEEK_ADJUSTMENT: {
                Log.d(TAG, "onPositionDiscontinuity-reason： " + "DISCONTINUITY_REASON_SEEK_ADJUSTMENT");
                break;
            }
            case Player.DISCONTINUITY_REASON_AD_INSERTION: {
                Log.d(TAG, "onPositionDiscontinuity-reason： " + "DISCONTINUITY_REASON_AD_INSERTION");
                break;
            }
            case Player.DISCONTINUITY_REASON_INTERNAL: {
                Log.d(TAG, "onPositionDiscontinuity-reason： " + "DISCONTINUITY_REASON_INTERNAL");
                break;
            }
        }
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Log.d(TAG, "onPlaybackParametersChanged-PlaybackParameters： "
                + "speed： " + playbackParameters.speed + "  pitch: " + playbackParameters.pitch);
    }

    @Override
    public void onSeekProcessed() {
        Log.d(TAG, "onSeekProcessed");
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.d(TAG, "onLayoutChange: "
                + "left(" + left + ") "
                + "top(" + top + ") "
                + "right(" + right + ") "
                + "bottom(" + bottom + ") "
                + "oldLeft(" + oldLeft + ") "
                + "oldTop(" + oldTop + ") "
                + "oldRight(" + oldRight + ") "
                + "oldBottom(" + oldBottom + ") ");
    }

    @Override
    public void onCues(List<Cue> cues) {
        Log.d(TAG, "onCues");
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        Log.d(TAG, "onVideoSizeChanged: "
                + "width(" + width + ") "
                + "height(" + width + ") "
                + "unappliedRotationDegrees(" + unappliedRotationDegrees + ") "
                + "pixelWidthHeightRatio(" + pixelWidthHeightRatio + ") ");
    }

    @Override
    public void onRenderedFirstFrame() {
        Log.d(TAG, "onRenderedFirstFrame");
    }
}
