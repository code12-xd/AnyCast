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
 *  Close controller.
 */
package com.code12.anyplayer.cover;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.code12.anyplayer.R;
import com.code12.anyplayer.auxiliary.DataInter;
import com.code12.playerframework.receiver.BaseCover;

public class CloseCover extends BaseCover {
    ImageView mCloseIcon;

    public CloseCover(Context context) {
        super(context);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
        mCloseIcon = getView().findViewById(R.id.iv_close);
        mCloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewClick(view);
            }
        });
    }

    public void onViewClick(View view){
        notifyReceiverEvent(DataInter.Event.EVENT_CODE_REQUEST_CLOSE, null);
    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();
    }

    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_close_cover, null);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public int getCoverLevel() {
        return levelMedium(10);
    }
}
