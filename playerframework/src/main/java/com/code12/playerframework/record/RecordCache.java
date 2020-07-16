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

import android.util.LruCache;

/**
 * play position  memory record, use LruCache.
 */
public class RecordCache {

    private LruCache<String, Integer> mLruCache;

    public RecordCache(int maxCacheCount){
        mLruCache = new LruCache<String,Integer>(maxCacheCount * 4){
            @Override
            protected int sizeOf(String key, Integer value) {
                return 4;
            }
        };
    }

    public int putRecord(String key, int record){
        Integer put = mLruCache.put(key, record);
        return put!=null?put:-1;
    }

    public int removeRecord(String key){
        Integer remove = mLruCache.remove(key);
        return remove!=null?remove:-1;
    }

    public int getRecord(String key){
        Integer integer = mLruCache.get(key);
        return integer!=null?integer:0;
    }

    public void clearRecord(){
        mLruCache.evictAll();
    }


}
