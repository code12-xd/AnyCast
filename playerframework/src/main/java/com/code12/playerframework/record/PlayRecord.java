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
import com.code12.playerframework.log.PLog;

class PlayRecord {

    private final String TAG = "PlayRecord";
    private static PlayRecord i;

    private RecordInvoker mRecordInvoker;

    private PlayRecord(){
        mRecordInvoker = new RecordInvoker(PlayRecordManager.getConfig());
    }

    public static PlayRecord get(){
        if(null==i){
            synchronized (PlayRecord.class){
                if(null==i){
                    i = new PlayRecord();
                }
            }
        }
        return i;
    }

    public int record(MediaSource data, int record){
        if(data==null)
            return -1;
        int saveRecord = mRecordInvoker.saveRecord(data, record);
        PLog.d(TAG,"<<Save>> : record = " + record);
        return saveRecord;
    }

    public int reset(MediaSource data){
        if(data==null)
            return -1;
        return mRecordInvoker.resetRecord(data);
    }

    public int removeRecord(MediaSource data){
        if(data==null)
            return -1;
        return mRecordInvoker.removeRecord(data);
    }

    public int getRecord(MediaSource data){
        if(data==null)
            return 0;
        int record = mRecordInvoker.getRecord(data);
        PLog.d(TAG,"<<Get>> : record = " + record);
        return record;
    }

    public void clearRecord(){
        mRecordInvoker.clearRecord();
    }

    public void destroy(){
        clearRecord();
        i = null;
    }

}
