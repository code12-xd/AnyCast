package com.code12.anycast.View.Activitys;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.code12.anycast.R;
import com.code12.anycast.View.auxiliary.DataInter;
import com.code12.anycast.auxilliary.utils.DisplayUtil;
import com.code12.playerframework.assist.InterEvent;
import com.code12.playerframework.assist.OnVideoViewEventHandler;
import com.code12.playerframework.entity.DataSource;
import com.code12.playerframework.player.IPlayer;
import com.code12.playerframework.widget.BaseVideoView;

public class VideoPlayActivity extends BaseActivity {
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_URL = "url";

    private BaseVideoView mVideoView;
    private String mTitle;
    private String mUrl;

    private boolean userPause;
    private boolean hasStart;
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void bindViews() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVideoView = findViewById(R.id.videoview);
        updateVideo(false);
        mVideoView.setEventHandler(onVideoViewEventHandler);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        int state = mVideoView.getState();
        if(state == IPlayer.STATE_PLAYBACK_COMPLETE)
            return;
        if(mVideoView.isInPlaybackState()){
            mVideoView.pause();
        }else{
            mVideoView.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mUrl = getIntent().getStringExtra(EXTRA_URL);

        int state = mVideoView.getState();
        if(state == IPlayer.STATE_PLAYBACK_COMPLETE)
            return;
        if(mVideoView.isInPlaybackState()){
            if(!userPause)
                mVideoView.resume();
        }else{
            mVideoView.rePlay(0);
        }
        initPlay();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            isLandscape = true;
            updateVideo(true);
        }else{
            isLandscape = false;
            updateVideo(false);
        }
//        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandscape);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    private OnVideoViewEventHandler onVideoViewEventHandler = new OnVideoViewEventHandler(){
        @Override
        public void onAssistHandle(BaseVideoView assist, int eventCode, Bundle bundle) {
            super.onAssistHandle(assist, eventCode, bundle);
            switch (eventCode){
                case InterEvent.CODE_REQUEST_PAUSE:
                    userPause = true;
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                    if(isLandscape){
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }else{
                        finish();
                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                    setRequestedOrientation(isLandscape ?
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case DataInter.Event.EVENT_CODE_ERROR_SHOW:
                    mVideoView.stop();
                    break;
            }
        }

        @Override
        public void requestRetry(BaseVideoView videoView, Bundle bundle) {
            if(DisplayUtil.isTopActivity(VideoPlayActivity.this)){
                super.requestRetry(videoView, bundle);
            }
        }
    };

    private void replay(){
        mVideoView.setDataSource(new DataSource(mUrl));
        mVideoView.start();
    }

    private void updateVideo(boolean landscape){
        int margin = DisplayUtil.dp2px(this,2);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
        if(landscape){
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.setMargins(0, 0, 0, 0);
        }else{
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.setMargins(0, 0, 0, 0);
//            layoutParams.width = DisplayUtil.getScreenWidth(this) - (margin*2);
//            layoutParams.height = layoutParams.width * 3/4;
//            layoutParams.setMargins(margin, margin, margin, margin);
        }
        mVideoView.setLayoutParams(layoutParams);
    }

    private void initPlay(){
        if(!hasStart && mUrl != null) {
            DataSource dataSource = new DataSource(mUrl);
            dataSource.setTitle(mTitle);
            mVideoView.setDataSource(dataSource);
            mVideoView.start();
            hasStart = true;
        }
    }
}
