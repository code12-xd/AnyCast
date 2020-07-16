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
 *  To adapt the real player framework.
 */
package com.code12.playerframework.config;

import com.code12.playerframework.player.BaseInternalPlayer;

import java.lang.reflect.Constructor;

public class PlayerLoader {

    public static BaseInternalPlayer loadInternalPlayer(int decoderPlanId){
        BaseInternalPlayer internalPlayer = null;
        try {
            Object decoderInstance = getDecoderInstance(decoderPlanId);
            if(decoderInstance instanceof BaseInternalPlayer){
                internalPlayer = (BaseInternalPlayer) decoderInstance;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return internalPlayer;
    }

    public static Object getDecoderInstance(int planId){
        Object instance = null;
        try{
            Class clz = getSDKClass(PlayerChooser.getPlan(planId).getClassPath());
            if(clz!=null){
                Constructor constructor = getConstructor(clz);
                if(constructor!=null){
                    instance = constructor.newInstance();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return instance;
    }

    private static Constructor getConstructor(Class clz){
        Constructor result = null;
        try{
            result = clz.getConstructor();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private static Class getSDKClass(String classPath){
        Class result = null;
        try {
            result = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
