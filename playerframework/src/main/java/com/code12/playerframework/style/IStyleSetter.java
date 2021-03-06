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

import android.graphics.Rect;

public interface IStyleSetter {

    void setRoundRectShape(float radius);

    void setRoundRectShape(Rect rect, float radius);

    void setOvalRectShape();

    void setOvalRectShape(Rect rect);

    void clearShapeStyle();

    void setElevationShadow(float elevation);

    /**
     * must setting a color when set shadow, not transparent.
     * @param backgroundColor
     * @param elevation
     */
    void setElevationShadow(int backgroundColor, float elevation);

}
