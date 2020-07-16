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
package com.code12.playerframework.style;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.code12.playerframework.utils.RectUtils;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ViewOvalRectOutlineProvider extends ViewOutlineProvider {

    private Rect mRect;

    public ViewOvalRectOutlineProvider(Rect rect){
        this.mRect = rect;
    }

    @Override
    public void getOutline(final View view, final Outline outline) {
        Rect selfRect;
        if(mRect!=null){
            selfRect = mRect;
        }else{
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);
            selfRect = RectUtils.getOvalRect(rect);
        }
        outline.setOval(selfRect);
    }

}
