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
 *  Created by code12, 2020-07-15.
 */
package com.code12.playerframework.player;

import android.os.Bundle;

import com.code12.playerframework.event.BundlePool;
import com.code12.playerframework.event.EventKey;
import com.code12.playerframework.event.OnErrorEventListener;
import com.code12.playerframework.event.OnPlayerEventListener;

public abstract class BaseInternalPlayer implements IPlayer {

    private int mCurrentState = STATE_IDLE;

    private OnPlayerEventListener mOnPlayerEventListener;
    private OnErrorEventListener mOnErrorEventListener;
    private OnBufferingListener mOnBufferingListener;

    private int mBufferPercentage;

    @Override
    public void option(int code, Bundle bundle) {
        //not handle
    }

    @Override
    public final void setOnBufferingListener(OnBufferingListener onBufferingListener) {
        this.mOnBufferingListener = onBufferingListener;
    }

    @Override
    public final void setOnPlayerEventListener(OnPlayerEventListener onPlayerEventListener) {
        this.mOnPlayerEventListener = onPlayerEventListener;
    }

    @Override
    public final void setOnErrorEventListener(OnErrorEventListener onErrorEventListener) {
        this.mOnErrorEventListener = onErrorEventListener;
    }

    protected final void submitPlayerEvent(int eventCode, Bundle bundle){
        if(mOnPlayerEventListener!=null)
            mOnPlayerEventListener.onPlayerEvent(eventCode, bundle);
    }

    protected final void submitErrorEvent(int eventCode, Bundle bundle){
        if(mOnErrorEventListener!=null)
            mOnErrorEventListener.onErrorEvent(eventCode, bundle);
    }

    protected final void submitBufferingUpdate(int bufferPercentage, Bundle extra){
        mBufferPercentage = bufferPercentage;
        if(mOnBufferingListener!=null)
            mOnBufferingListener.onBufferingUpdate(bufferPercentage, extra);
    }

    protected final void updateStatus(int status){
        this.mCurrentState = status;
        Bundle bundle = BundlePool.obtain();
        bundle.putInt(EventKey.INT_DATA, status);
        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE, bundle);
    }

    @Override
    public int getBufferPercentage() {
        return mBufferPercentage;
    }

    @Override
    public final int getState() {
        return mCurrentState;
    }
}
