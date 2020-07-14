/*
 * Copyright (C) 2020 code12
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *  Created by code12, 2020-07-14.
 *  loading controller.
 */
package com.code12.anyplayer.cover;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.code12.anyplayer.R;
import com.code12.playerframework.event.OnPlayerEventListener;
import com.code12.playerframework.player.IPlayer;
import com.code12.playerframework.receiver.BaseCover;
import com.code12.playerframework.receiver.PlayerStateGetter;

public class LoadingCover extends BaseCover {

    public LoadingCover(Context context) {
        super(context);
    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        PlayerStateGetter playerStateGetter = getPlayerStateGetter();
        if(playerStateGetter!=null && isInPlaybackState(playerStateGetter)){
            setLoadingState(playerStateGetter.isBuffering());
        }
    }

    private boolean isInPlaybackState(PlayerStateGetter playerStateGetter){
        int state = playerStateGetter.getState();
        return state!= IPlayer.STATE_END
                && state!= IPlayer.STATE_ERROR
                && state!= IPlayer.STATE_IDLE
                && state!= IPlayer.STATE_INITIALIZED
                && state!= IPlayer.STATE_STOPPED;
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode){
            case OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
            case OnPlayerEventListener.PLAYER_EVENT_ON_PROVIDER_DATA_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_TO:
                setLoadingState(true);
                break;

            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_END:
            case OnPlayerEventListener.PLAYER_EVENT_ON_STOP:
            case OnPlayerEventListener.PLAYER_EVENT_ON_PROVIDER_DATA_ERROR:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_COMPLETE:
                setLoadingState(false);
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        setLoadingState(false);
    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    private void setLoadingState(boolean show){
        setCoverVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_loading_cover, null);
    }

    @Override
    public int getCoverLevel() {
        return levelMedium(1);
    }
}
