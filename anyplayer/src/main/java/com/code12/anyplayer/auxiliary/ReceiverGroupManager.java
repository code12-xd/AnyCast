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
 *  controller.
 */
package com.code12.anyplayer.auxiliary;

import android.content.Context;

import com.code12.anyplayer.cover.CompleteCover;
import com.code12.anyplayer.cover.ControllerCover;
import com.code12.anyplayer.cover.ErrorCover;
import com.code12.anyplayer.cover.GestureCover;
import com.code12.anyplayer.cover.LoadingCover;
import com.code12.playerframework.receiver.GroupValue;
import com.code12.playerframework.receiver.ReceiverGroup;

import static com.code12.anyplayer.auxiliary.DataInter.ReceiverKey.KEY_CONTROLLER_COVER;
import static com.code12.anyplayer.auxiliary.DataInter.ReceiverKey.KEY_GESTURE_COVER;

public class ReceiverGroupManager {

    private static ReceiverGroupManager i;

    private ReceiverGroupManager(){
    }

    public static ReceiverGroupManager get(){
        if(null==i){
            synchronized (ReceiverGroupManager.class){
                if(null==i){
                    i = new ReceiverGroupManager();
                }
            }
        }
        return i;
    }

    public ReceiverGroup getLittleReceiverGroup(Context context){
        return getLiteReceiverGroup(context, null);
    }

    public ReceiverGroup getLittleReceiverGroup(Context context, GroupValue groupValue){
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_COMPLETE_COVER, new CompleteCover(context));
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }

    public ReceiverGroup getLiteReceiverGroup(Context context){
        return getLiteReceiverGroup(context, null);
    }

    public ReceiverGroup getLiteReceiverGroup(Context context, GroupValue groupValue){
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(context));
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_COMPLETE_COVER, new CompleteCover(context));
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }

    public ReceiverGroup getReceiverGroup(Context context){
        return getReceiverGroup(context, null);
    }

    public ReceiverGroup getReceiverGroup(Context context, GroupValue groupValue){
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(context));
        receiverGroup.addReceiver(KEY_GESTURE_COVER, new GestureCover(context));
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_COMPLETE_COVER, new CompleteCover(context));
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }

}
