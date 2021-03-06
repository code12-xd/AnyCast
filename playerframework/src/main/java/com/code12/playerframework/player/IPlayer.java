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
 *  Created by code12, 2020-07-11.
 *  Player interface
 */
package com.code12.playerframework.player;

import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.code12.playerframework.source.MediaSource;
import com.code12.playerframework.event.OnErrorEventListener;
import com.code12.playerframework.event.OnPlayerEventListener;

public interface IPlayer {

    int STATE_END = -2;
    int STATE_ERROR = -1;
    int STATE_IDLE = 0;
    int STATE_INITIALIZED = 1;
    int STATE_PREPARED = 2;
    int STATE_STARTED = 3;
    int STATE_PAUSED = 4;
    int STATE_STOPPED = 5;
    int STATE_PLAYBACK_COMPLETE = 6;

    /**
     * with this method, you can send some params for player init or switch some setting.
     * such as some configuration option (use mediacodec or timeout or reconnect and so on) for decoder init.
     * @param code the code value custom yourself.
     * @param bundle deliver some data if you need.
     */
    void option(int code, Bundle bundle);

    void setDataSource(MediaSource dataSource);
    void setDisplay(SurfaceHolder surfaceHolder);
    void setSurface(Surface surface);
    void setVolume(float left, float right);
    void setSpeed(float speed);

    void setOnPlayerEventListener(OnPlayerEventListener onPlayerEventListener);
    void setOnErrorEventListener(OnErrorEventListener onErrorEventListener);

    void setOnBufferingListener(OnBufferingListener onBufferingListener);

    int getBufferPercentage();

    boolean isPlaying();
    int getCurrentPosition();
    int getDuration();
    int getAudioSessionId();
    int getVideoWidth();
    int getVideoHeight();
    int getState();

    void start();
    void start(int msc);
    void pause();
    void resume();
    void seekTo(int msc);
    void stop();
    void reset();
    void destroy();
}
