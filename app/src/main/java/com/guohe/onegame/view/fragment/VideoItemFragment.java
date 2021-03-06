package com.guohe.onegame.view.fragment;

import android.media.MediaPlayer;
import android.widget.ImageView;

import com.guohe.onegame.R;
import com.guohe.onegame.custome.FullScreenVideoView;

/**
 * Function:
 * Created by dubin on 2016/12/26.
 */

public class VideoItemFragment extends LazyLoadFragment
        implements MediaPlayer.OnPreparedListener {

    private boolean mHasPaused;
    private int mVideoPosition;
    private int videoRes;
    private int imgRes;
    private FullScreenVideoView mVideoView;
    private ImageView mvSlogan;


    public VideoItemFragment() {
        mHasPaused = false;
        mVideoPosition = 0;
    }

    @Override
    protected int setContentView() {
        return R.layout.video_viewpager_item;
    }

    @Override
    protected void lazyLoad() {
        if (getArguments() == null) {
            return;
        }
        videoRes = getArguments().getInt("videoRes");
        imgRes = getArguments().getInt("imgRes");

        mVideoView = findViewById(R.id.vvSplash);
        mvSlogan = findViewById(R.id.ivSlogan);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + videoRes);
        mvSlogan.setImageResource(imgRes);

    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
            if (mVideoView != null) {
                mVideoView.requestFocus();
                mVideoView.seekTo(0);
                mVideoView.start();
            }
            return;

    }


    @Override
    public void onResume() {
        super.onResume();
        if (mHasPaused) {
            if (mVideoView != null) {
                mVideoView.seekTo(mVideoPosition);
                mVideoView.resume();
            }
        }
        return;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoPosition = mVideoView.getCurrentPosition();
        }
        mHasPaused = true;
    }

    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
        return;
    }

    public void pause() {
        if ((mVideoView != null) && (mVideoView.canPause())) {
            mVideoView.setOnCompletionListener(null);
            mVideoView.pause();
        }
        return;

    }

    public void play() {
        if (mVideoView != null) {
            mVideoView.requestFocus();
            mVideoView.seekTo(0);
        } else {
            return;
        }
        mVideoView.start();
    }

    public void reLoadVideo() {
        if (mVideoView != null) {
            mVideoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + videoRes);
        }
    }

}
