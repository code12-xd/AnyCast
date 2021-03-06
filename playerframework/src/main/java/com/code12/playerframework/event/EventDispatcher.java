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
 * The event dispatcher of the framework is used to
 * distribute playback events, error events and receiver events.
 */
package com.code12.playerframework.event;

import android.os.Bundle;
import android.view.MotionEvent;

import com.code12.playerframework.log.DebugLog;
import com.code12.playerframework.player.OnTimerUpdateListener;
import com.code12.playerframework.receiver.IReceiver;
import com.code12.playerframework.receiver.IReceiverGroup;
import com.code12.playerframework.touch.OnTouchGestureListener;

public final class EventDispatcher implements IEventDispatcher{
    private IReceiverGroup mReceiverGroup;

    public EventDispatcher(IReceiverGroup receiverGroup){
        this.mReceiverGroup = receiverGroup;
    }

    @Override
    public void dispatchPlayEvent(final int eventCode, final Bundle bundle){
        DebugLog.onPlayEventLog(eventCode, bundle);
        switch (eventCode){
            case OnPlayerEventListener.PLAYER_EVENT_ON_TIMER_UPDATE:
                mReceiverGroup.forEach(new IReceiverGroup.OnLoopListener() {
                    @Override
                    public void onEach(IReceiver receiver) {
                        if(receiver instanceof OnTimerUpdateListener && bundle!=null)
                            ((OnTimerUpdateListener)receiver).onTimerUpdate(
                                    bundle.getInt(EventKey.INT_ARG1),
                                    bundle.getInt(EventKey.INT_ARG2),
                                    bundle.getInt(EventKey.INT_ARG3));
                        receiver.onPlayerEvent(eventCode, bundle);
                    }
                });
                break;
            default:
                mReceiverGroup.forEach(new IReceiverGroup.OnLoopListener() {
                    @Override
                    public void onEach(IReceiver receiver) {
                        receiver.onPlayerEvent(eventCode, bundle);
                    }
                });
                break;
        }
        recycleBundle(bundle);
    }

    @Override
    public void dispatchErrorEvent(final int eventCode, final Bundle bundle){
        DebugLog.onErrorEventLog(eventCode, bundle);
        mReceiverGroup.forEach(new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                receiver.onErrorEvent(eventCode, bundle);
            }
        });
        recycleBundle(bundle);
    }

    @Override
    public void dispatchReceiverEvent(final int eventCode, final Bundle bundle){
        dispatchReceiverEvent(eventCode, bundle, null);
    }

    @Override
    public void dispatchReceiverEvent(final int eventCode, final Bundle bundle, IReceiverGroup.OnReceiverFilter onReceiverFilter) {
        mReceiverGroup.forEach(onReceiverFilter, new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                receiver.onReceiverEvent(eventCode, bundle);
            }
        });
        recycleBundle(bundle);
    }

    @Override
    public void dispatchProducerEvent(final int eventCode, final Bundle bundle, IReceiverGroup.OnReceiverFilter onReceiverFilter) {
        mReceiverGroup.forEach(onReceiverFilter, new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                receiver.onProducerEvent(eventCode, bundle);
            }
        });
        recycleBundle(bundle);
    }

    @Override
    public void dispatchProducerData(final String key, final Object data, IReceiverGroup.OnReceiverFilter onReceiverFilter) {
        mReceiverGroup.forEach(onReceiverFilter, new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                receiver.onProducerData(key, data);
            }
        });
    }

    @Override
    public void dispatchTouchEventOnSingleTabUp(final MotionEvent event) {
        filterImplOnTouchEventListener(new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                ((OnTouchGestureListener)receiver).onSingleTapUp(event);
            }
        });
    }

    @Override
    public void dispatchTouchEventOnLongPress(final MotionEvent event) {
        filterImplOnTouchEventListener(new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                ((OnTouchGestureListener)receiver).onLongPress(event);
            }
        });
    }

    @Override
    public void dispatchTouchEventOnDoubleTabUp(final MotionEvent event) {
        filterImplOnTouchEventListener(new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                ((OnTouchGestureListener)receiver).onDoubleTap(event);
            }
        });
    }

    @Override
    public void dispatchTouchEventOnDown(final MotionEvent event) {
        filterImplOnTouchEventListener(new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                ((OnTouchGestureListener)receiver).onDown(event);
            }
        });
    }

    @Override
    public void dispatchTouchEventOnScroll(final MotionEvent e1, final MotionEvent e2,
                                           final float distanceX, final float distanceY) {
        filterImplOnTouchEventListener(new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                ((OnTouchGestureListener)receiver).onScroll(e1, e2, distanceX, distanceY);
            }
        });
    }

    @Override
    public void dispatchTouchEventOnEndGesture() {
        filterImplOnTouchEventListener(new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                ((OnTouchGestureListener)receiver).onEndGesture();
            }
        });
    }

    private void filterImplOnTouchEventListener(final IReceiverGroup.OnLoopListener onLoopListener){
        mReceiverGroup.forEach(new IReceiverGroup.OnReceiverFilter() {
            @Override
            public boolean filter(IReceiver receiver) {
                return receiver instanceof OnTouchGestureListener;
            }
        }, new IReceiverGroup.OnLoopListener() {
            @Override
            public void onEach(IReceiver receiver) {
                onLoopListener.onEach(receiver);
            }
        });
    }

    private void recycleBundle(Bundle bundle){
        if(bundle!=null)
            bundle.clear();
    }

}
