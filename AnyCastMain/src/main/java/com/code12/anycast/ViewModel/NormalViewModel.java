/**
 *  Created by code12, 2020-07-09.
 */
package com.code12.anycast.ViewModel;

import android.app.Application;

import com.code12.anybaseui.Model.DataLoaderFactory;
import com.code12.anybaseui.Model.IDataLoader;
import com.code12.anycast.Model.network.AliRequestFactory;
import com.code12.anycast.Model.network.RetrofitHelper;
import com.code12.anycast.Model.types.GameInfo;
import com.code12.anycast.Model.types.NormalVListInfo;
import com.code12.anycast.auxilliary.utils.LogUtil;

import java.util.Map;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NormalViewModel extends BaseFragmentViewModel {
    public NormalViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public <T> void loadData(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        loadDataObservable().subscribe((Consumer<? super GameInfo>)onNext, onError);
    }

    @Override
    public Observable loadDataObservable() {
        IDataLoader loader = DataLoaderFactory.<NormalVListInfo>createDataLoader(NormalVListInfo.class);
        return loader.loadObservable();
    }

    public Observable getPlayUrlObservable(String vid) {
        Map<String ,String> params = AliRequestFactory.buildVideoRequestParams(vid);
        //test case: get the body to analyze
//        RetrofitHelper
//                .getNormalVAPI()
//                .getVideoInfo(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    body -> {
//                        String res = body.getResult().get(0).getVideoUrl();
//                        LogUtil.test("AAAAAA" + body.toString());
//                    },
//                    throwable -> {
//                        LogUtil.test(throwable.getMessage());
//                    });

        return RetrofitHelper.getNormalVAPI()
                .getVideoInfo(params)
                //TODO:?? .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void clear() {
    }
}
