/**
 *  Created by code12, 2020-07-09.
 */
package com.code12.playerframework.assist;

import android.os.Bundle;

import com.code12.playerframework.entity.DataSource;
import com.code12.playerframework.event.EventKey;
import com.code12.playerframework.log.PLog;
import com.code12.playerframework.player.IPlayer;
import com.code12.playerframework.widget.BaseVideoView;

public class OnVideoViewEventHandler extends BaseEventAssistHandler<BaseVideoView> {

    @Override
    public void requestPause(BaseVideoView videoView, Bundle bundle) {
        if(isInPlaybackState(videoView)){
            videoView.pause();
        }else{
            videoView.stop();
        }
    }

    @Override
    public void requestResume(BaseVideoView videoView, Bundle bundle) {
        if(isInPlaybackState(videoView)){
            videoView.resume();
        }else{
            requestRetry(videoView, bundle);
        }
    }

    @Override
    public void requestSeek(BaseVideoView videoView, Bundle bundle) {
        int pos = 0;
        if(bundle!=null){
            pos = bundle.getInt(EventKey.INT_DATA);
        }
        videoView.seekTo(pos);
    }

    @Override
    public void requestStop(BaseVideoView videoView, Bundle bundle) {
        videoView.stop();
    }

    @Override
    public void requestReset(BaseVideoView videoView, Bundle bundle) {
        videoView.stop();
    }

    @Override
    public void requestRetry(BaseVideoView videoView, Bundle bundle) {
        int pos = 0;
        if(bundle!=null){
            pos = bundle.getInt(EventKey.INT_DATA);
        }
        videoView.rePlay(pos);
    }

    @Override
    public void requestReplay(BaseVideoView videoView, Bundle bundle) {
        videoView.rePlay(0);
    }

    @Override
    public void requestPlayDataSource(BaseVideoView assist, Bundle bundle) {
        if(bundle!=null){
            DataSource data = (DataSource) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
            if(data==null){
                PLog.e("OnVideoViewEventHandler","requestPlayDataSource need legal data source");
                return;
            }
            assist.stop();
            assist.setDataSource(data);
            assist.start();
        }
    }

    private boolean isInPlaybackState(BaseVideoView videoView) {
        int state = videoView.getState();
        return state!= IPlayer.STATE_END
                && state!= IPlayer.STATE_ERROR
                && state!= IPlayer.STATE_IDLE
                && state!= IPlayer.STATE_INITIALIZED
                && state!= IPlayer.STATE_STOPPED;
    }

}
