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
 *  Adapter which hold the bean as list to show.
 */
package com.code12.anybaseui.View.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.code12.anybaseui.Model.DataLoaderFactory;
import com.code12.anybaseui.Model.IBean;
import com.code12.anybaseui.Model.IDataLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnyRecycleViewAdapter<T extends IBean> extends RecyclerView.Adapter {
    private Context context;
    private List<T> mItems;

    public AnyRecycleViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }
}
