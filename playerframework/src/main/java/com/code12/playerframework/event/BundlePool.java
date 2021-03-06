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
 * In order to improve memory performance,
 * the bundle entities passed in the framework
 * come from the bundle buffer pool.
 */
package com.code12.playerframework.event;

import android.os.Bundle;

import com.code12.playerframework.log.PLog;

import java.util.ArrayList;
import java.util.List;

public class BundlePool {
    private static final int POOL_SIZE = 3;
    private static List<Bundle> mPool;

    static {
        mPool = new ArrayList<>();
        for(int i=0;i<POOL_SIZE;i++)
            mPool.add(new Bundle());
    }

    public synchronized static Bundle obtain(){
        for(int i=0;i<POOL_SIZE;i++){
            if(mPool.get(i).isEmpty()){
                return mPool.get(i);
            }
        }
        PLog.w("BundlePool","<create new bundle object>");
        return new Bundle();
    }
}
