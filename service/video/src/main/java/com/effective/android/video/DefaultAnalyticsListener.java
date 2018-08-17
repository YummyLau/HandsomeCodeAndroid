package com.effective.android.video;

import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.io.IOException;

/**
 * replace {@link DefaultPlayerEventListener}
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class DefaultAnalyticsListener implements AnalyticsListener {

    private static final String TAG = DefaultAnalyticsListener.class.getSimpleName();

    @Override
    public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_IDLE: {
                Log.d(TAG, "onPlayerStateChanged-playbackState： " + "STATE_IDLE  playWhenReady: " + playWhenReady);
                break;
            }
            case Player.STATE_BUFFERING: {
                Log.d(TAG, "onPlayerStateChanged-playbackState： " + "STATE_BUFFERING  playWhenReady: " + playWhenReady);
                break;
            }
            case Player.STATE_READY: {
                Log.d(TAG, "onPlayerStateChanged-playbackState： " + "STATE_READY  playWhenReady: " + playWhenReady);
                break;
            }
            case Player.STATE_ENDED: {
                Log.d(TAG, "onPlayerStateChanged-playbackState： " + "STATE_ENDED  playWhenReady: " + playWhenReady);
                break;
            }
        }
    }

    @Override
    public void onTimelineChanged(EventTime eventTime, int reason) {
        Log.d(TAG, "onTimelineChanged-周期总数： " + reason);
    }

    @Override
    public void onPositionDiscontinuity(EventTime eventTime, int reason) {
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
    public void onSeekStarted(EventTime eventTime) {
        Log.d(TAG, "onSeekStarted");
    }

    @Override
    public void onSeekProcessed(EventTime eventTime) {
        Log.d(TAG, "onSeekProcessed");
    }

    @Override
    public void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters) {
        Log.d(TAG, "onPlaybackParametersChanged-PlaybackParameters： "
                + "speed： " + playbackParameters.speed + "  pitch: " + playbackParameters.pitch);
    }

    @Override
    public void onRepeatModeChanged(EventTime eventTime, int repeatMode) {
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
    public void onShuffleModeChanged(EventTime eventTime, boolean shuffleModeEnabled) {
        Log.d(TAG, "onShuffleModeEnabledChanged-shuffleModeEnabled： " + shuffleModeEnabled);
    }

    @Override
    public void onLoadingChanged(EventTime eventTime, boolean isLoading) {
        Log.d(TAG, "onLoadingChanged-isLoading： " + isLoading);
    }

    @Override
    public void onPlayerError(EventTime eventTime, ExoPlaybackException error) {
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
    public void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        Log.d(TAG, "TrackGroupArray-trackGroups： " + trackGroups);
        Log.d(TAG, "TrackGroupArray-TrackSelectionArray： " + trackGroups);
    }

    @Override
    public void onLoadStarted(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG, "onLoadStarted");
    }

    @Override
    public void onLoadCompleted(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG, "onLoadCompleted");
    }

    @Override
    public void onLoadCanceled(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG, "onLoadCanceled");
    }

    @Override
    public void onLoadError(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
        Log.d(TAG, "onLoadError");
    }

    @Override
    public void onDownstreamFormatChanged(EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG, "onDownstreamFormatChanged");
    }

    @Override
    public void onUpstreamDiscarded(EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        Log.d(TAG, "onUpstreamDiscarded");
    }

    @Override
    public void onMediaPeriodCreated(EventTime eventTime) {
        Log.d(TAG, "onMediaPeriodCreated");
    }

    @Override
    public void onMediaPeriodReleased(EventTime eventTime) {
        Log.d(TAG, "onMediaPeriodReleased");
    }

    @Override
    public void onReadingStarted(EventTime eventTime) {
        Log.d(TAG, "onReadingStarted");
    }

    @Override
    public void onBandwidthEstimate(EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {
        Log.d(TAG, "onBandwidthEstimate： totalLoadTimeMs: " + totalLoadTimeMs + " totalBytesLoaded: " + totalBytesLoaded + " bitrateEstimate: " + bitrateEstimate);
    }

    @Override
    public void onViewportSizeChange(EventTime eventTime, int width, int height) {
        Log.d(TAG, "onViewportSizeChange： width: " + width + " height: " + height);
    }

    @Override
    public void onNetworkTypeChanged(EventTime eventTime, @Nullable NetworkInfo networkInfo) {
        Log.d(TAG, "onNetworkTypeChanged");
    }

    @Override
    public void onMetadata(EventTime eventTime, Metadata metadata) {
        Log.d(TAG, "onMetadata");
    }

    @Override
    public void onDecoderEnabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {
        Log.d(TAG, "onDecoderEnabled");
    }

    @Override
    public void onDecoderInitialized(EventTime eventTime, int trackType, String decoderName, long initializationDurationMs) {
        Log.d(TAG, "onDecoderInitialized");
    }

    @Override
    public void onDecoderInputFormatChanged(EventTime eventTime, int trackType, Format format) {
        Log.d(TAG, "onDecoderInputFormatChanged");
    }

    @Override
    public void onDecoderDisabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {
        Log.d(TAG, "onDecoderDisabled");
    }

    @Override
    public void onAudioSessionId(EventTime eventTime, int audioSessionId) {
        Log.d(TAG, "onAudioSessionId: audioSessionId: " + audioSessionId);
    }

    @Override
    public void onAudioUnderrun(EventTime eventTime, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        Log.d(TAG, "onAudioUnderrun: bufferSize: " + bufferSize + " bufferSizeMs: " + bufferSizeMs + " elapsedSinceLastFeedMs: " + elapsedSinceLastFeedMs);
    }

    @Override
    public void onDroppedVideoFrames(EventTime eventTime, int droppedFrames, long elapsedMs) {
        Log.d(TAG, "onDroppedVideoFrames: droppedFrames: " + droppedFrames + " elapsedMs: " + elapsedMs);
    }

    @Override
    public void onVideoSizeChanged(EventTime eventTime, int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        Log.d(TAG, "onDroppedVideoFrames: width: " + width + " height: " + height + " unappliedRotationDegrees: " + unappliedRotationDegrees + " pixelWidthHeightRatio: " + pixelWidthHeightRatio);
    }

    @Override
    public void onRenderedFirstFrame(EventTime eventTime, Surface surface) {
        Log.d(TAG, "onRenderedFirstFrame");
    }

    @Override
    public void onDrmKeysLoaded(EventTime eventTime) {
        Log.d(TAG, "onDrmKeysLoaded");
    }

    @Override
    public void onDrmSessionManagerError(EventTime eventTime, Exception error) {
        Log.d(TAG, "onDrmSessionManagerError: " + error.getMessage());
    }

    @Override
    public void onDrmKeysRestored(EventTime eventTime) {
        Log.d(TAG, "onDrmKeysRestored");
    }

    @Override
    public void onDrmKeysRemoved(EventTime eventTime) {
        Log.d(TAG, "onDrmKeysRemoved");
    }
}
