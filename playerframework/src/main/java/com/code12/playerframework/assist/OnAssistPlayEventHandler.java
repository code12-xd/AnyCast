/**
 *  Created by code12, 2020-07-09.
 */
package com.code12.playerframework.assist;

import android.os.Bundle;

import com.code12.playerframework.source.MediaSource;
import com.code12.playerframework.event.EventKey;
import com.code12.playerframework.log.PLog;

public class OnAssistPlayEventHandler extends BaseEventAssistHandler<AssistPlay> {

    @Override
    public void requestPause(AssistPlay assistPlay, Bundle bundle) {
        if(assistPlay.isInPlaybackState()){
            assistPlay.pause();
        }else{
            assistPlay.stop();
            assistPlay.reset();
        }
    }

    @Override
    public void requestResume(AssistPlay assistPlay, Bundle bundle) {
        if(assistPlay.isInPlaybackState()){
            assistPlay.resume();
        }else{
            requestRetry(assistPlay, bundle);
        }
    }

    @Override
    public void requestSeek(AssistPlay assistPlay, Bundle bundle) {
        int pos = 0;
        if(bundle!=null){
            pos = bundle.getInt(EventKey.INT_DATA);
        }
        assistPlay.seekTo(pos);
    }

    @Override
    public void requestStop(AssistPlay assistPlay, Bundle bundle) {
        assistPlay.stop();
    }

    @Override
    public void requestReset(AssistPlay assist, Bundle bundle) {
        assist.reset();
    }

    @Override
    public void requestRetry(AssistPlay assistPlay, Bundle bundle) {
        int pos = 0;
        if(bundle!=null){
            pos = bundle.getInt(EventKey.INT_DATA);
        }
        assistPlay.rePlay(pos);
    }

    @Override
    public void requestReplay(AssistPlay assistPlay, Bundle bundle) {
        assistPlay.rePlay(0);
    }

    @Override
    public void requestPlayDataSource(AssistPlay assist, Bundle bundle) {
        if(bundle!=null){
            MediaSource data = (MediaSource) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
            if(data==null){
                PLog.e("OnAssistPlayEventHandler","requestPlayDataSource need legal data source");
                return;
            }
            assist.stop();
            assist.setDataSource(data);
            assist.play();
        }
    }
}
