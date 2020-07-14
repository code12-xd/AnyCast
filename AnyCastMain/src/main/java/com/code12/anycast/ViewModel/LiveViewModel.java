/**
 *  Created by code12, 2020-07-05.
 */
package com.code12.anycast.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import com.code12.anybaseui.Model.DataLoaderFactory;
import com.code12.anybaseui.Model.IDataLoader;
import com.code12.anycast.Model.types.LiveAppIndexInfo;
import com.code12.anycast.Model.network.RetrofitHelper;

public class LiveViewModel extends BaseFragmentViewModel {
    public LiveViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public <T> void loadData(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        loadDataObservable().subscribe((Consumer<? super LiveAppIndexInfo>)onNext, onError);
    }

    @Override
    public Observable loadDataObservable() {
        IDataLoader loader = DataLoaderFactory.<LiveAppIndexInfo>createDataLoader(LiveAppIndexInfo.class);
        return loader.loadObservable();
    }
}
