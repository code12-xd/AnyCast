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
 *  Standard bean, datas should save in the map by key-valeu.
 */
package com.code12.anybaseui.Model;

import java.util.HashMap;
import java.util.Map;

public class Standard extends BaseBean {
    private Map<String, Object> mMember;

    public Standard() {
        mMember = new HashMap<String, Object>();
    }

    public Object get(String key) {
        return mMember.get(key);
    }

    @Override
    public IDataLoader getLoader() {
        return mLoader;
    }

    private static Loader mLoader = new Loader();
    private static class Loader extends DataLoader<Standard> {
    }
}
