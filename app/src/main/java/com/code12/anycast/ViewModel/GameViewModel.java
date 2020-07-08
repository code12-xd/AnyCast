/**
 *  Created by code12, 2020-07-07.
 */
package com.code12.anycast.ViewModel;

import android.app.Application;

import com.code12.anycast.Model.types.GameInfo;
import com.code12.anycast.Model.network.RetrofitHelper;
import com.code12.anycast.auxilliary.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GameViewModel extends BaseFragmentViewModel {
    public GameViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public <T> void loadData(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        loadDataObservable().subscribe((Consumer<? super GameInfo>)onNext, onError);
    }

    @Override
    public Observable loadDataObservable() {
        return RetrofitHelper.getGameAPI()
                .getGameInfo()
                //TODO:?? .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void clear() {
    }
}
