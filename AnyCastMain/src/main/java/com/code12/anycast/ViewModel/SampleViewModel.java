/**
 *  Created by code12, 2020-07-07.
 */
package com.code12.anycast.ViewModel;

import android.app.Application;

import com.code12.anycast.Model.Assets.AssetsLoader;
import com.code12.anycast.Model.types.SampleInfo;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SampleViewModel extends BaseFragmentViewModel {
    public SampleViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public <T> void loadData(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        loadDataObservable().subscribe((Consumer<? super SampleInfo>)onNext, onError);
    }

    @Override
    public Observable loadDataObservable() {
//        AssetsLoader loader = new AssetsLoader(new AssetsLoader.DataListener() {
//            @Override
//            public void onDataReady(List<AssetsLoader.SampleGroup> groups) {
//                SampleInfo info = SampleInfo.buildFrom(groups);
//                for (AssetsLoader.SampleGroup g:groups) {
//                    String name = g.title;
//                }
//            }
//        });
//        loader.readFromAsset();
        Observable<SampleInfo> section = Observable.create(subscriber -> {
            AssetsLoader loader = new AssetsLoader(new AssetsLoader.DataListener() {
                @Override
                public void onDataReady(List<AssetsLoader.SampleGroup> groups) {
                    subscriber.onNext(SampleInfo.buildFrom(groups));
                }
            });
            loader.readFromAsset(".exolist.json");
        });
        return section.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void clear() {
    }
}
