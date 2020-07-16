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
package com.code12.playerframework.record;

import com.code12.playerframework.source.MediaSource;

public class RecordInvoker {

    private OnRecordCallBack mCallBack;

    private RecordCache mRecordCache;

    public RecordInvoker(PlayRecordManager.RecordConfig config){
        this.mCallBack = config.getOnRecordCallBack();
        mRecordCache = new RecordCache(config.getMaxRecordCount());
    }

    public int saveRecord(MediaSource dataSource, int record) {
        if(mCallBack!=null){
            return mCallBack.onSaveRecord(dataSource, record);
        }
        return mRecordCache.putRecord(getKey(dataSource), record);
    }

    public int getRecord(MediaSource dataSource) {
        if(mCallBack!=null){
            return mCallBack.onGetRecord(dataSource);
        }
        return mRecordCache.getRecord(getKey(dataSource));
    }

    public int resetRecord(MediaSource dataSource) {
        if(mCallBack!=null){
            return mCallBack.onResetRecord(dataSource);
        }
        return mRecordCache.putRecord(getKey(dataSource), 0);
    }

    public int removeRecord(MediaSource dataSource) {
        if(mCallBack!=null){
            return mCallBack.onRemoveRecord(dataSource);
        }
        return mRecordCache.removeRecord(getKey(dataSource));
    }

    public void clearRecord() {
        if(mCallBack!=null){
            mCallBack.onClearRecord();
            return;
        }
        mRecordCache.clearRecord();
    }

    String getKey(MediaSource dataSource){
        return PlayRecordManager.getKey(dataSource);
    }

}
