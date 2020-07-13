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
 *  Created by code12, 2020-07-12.
 *  Base interface for data loading, the public viewmodel's actions.
 */
package com.code12.anybaseui.Model;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public interface IDataLoader<T> {
    void load();
    void load(Consumer<? super T> onNext, Consumer<? super Throwable> onError);
    Observable loadObservable();
    boolean isLoading();
    void clear();
    void stop();
}
