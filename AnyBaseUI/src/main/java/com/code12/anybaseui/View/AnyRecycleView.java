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
 *  Created by code12, 2020-07-13.
 *  List beans's container.
 */
package com.code12.anybaseui.View;

import android.content.Context;

import com.code12.anybaseui.Model.IBean;
import com.code12.anybaseui.View.adapters.AnyRecycleViewAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnyRecycleView<T extends IBean> extends RecyclerView implements IContainer {
    private AnyRecycleViewAdapter<T> mAdapter;

    public AnyRecycleView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context c) {
        mAdapter = new AnyRecycleViewAdapter<T>(c);
    }
}
