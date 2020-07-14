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

import com.code12.playerframework.assist.InterEvent;
import com.code12.playerframework.assist.InterKey;

public interface DataInter {

    interface Event extends InterEvent{

        int EVENT_CODE_REQUEST_BACK = -100;
        int EVENT_CODE_REQUEST_CLOSE = -101;

        int EVENT_CODE_REQUEST_TOGGLE_SCREEN = -104;

        int EVENT_CODE_ERROR_SHOW = -111;

    }

    interface Key extends InterKey{

        String KEY_IS_LANDSCAPE = "isLandscape";

        String KEY_DATA_SOURCE = "data_source";

        String KEY_ERROR_SHOW = "error_show";

        String KEY_COMPLETE_SHOW = "complete_show";
        String KEY_CONTROLLER_TOP_ENABLE = "controller_top_enable";
        String KEY_CONTROLLER_SCREEN_SWITCH_ENABLE = "screen_switch_enable";

        String KEY_TIMER_UPDATE_ENABLE = "timer_update_enable";

        String KEY_NETWORK_RESOURCE = "network_resource";

    }

    interface ReceiverKey{
        String KEY_LOADING_COVER = "loading_cover";
        String KEY_CONTROLLER_COVER = "controller_cover";
        String KEY_GESTURE_COVER = "gesture_cover";
        String KEY_COMPLETE_COVER = "complete_cover";
        String KEY_ERROR_COVER = "error_cover";
        String KEY_CLOSE_COVER = "close_cover";
    }

    interface PrivateEvent{
        int EVENT_CODE_UPDATE_SEEK = -201;
    }

}
