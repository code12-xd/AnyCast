/**
 *  Created by code12, 2020-07-05.
 */
package com.code12.anycast.ViewModel;

import android.app.Application;

import com.code12.anybaseui.Model.DataLoaderFactory;
import com.code12.anybaseui.Model.IDataLoader;
import com.code12.anycast.Model.types.NormalVListInfo;
import com.code12.anycast.Model.types.RecommendBannerInfo;
import com.code12.anycast.Model.types.RecommendInfo;
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

public class RecommendViewModel extends BaseFragmentViewModel {
    public RecommendViewModel(@NonNull Application application) {
        super(application);
    }

    public List<RecommendInfo.ResultBean> getResultBeans() {
        return results;
    }

    public List<RecommendBannerInfo.DataBean> getDataBeans() {
        return recommendBanners;
    }

    @Override
    public <T> void loadData(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        RetrofitHelper.getBiliAppAPI()
                .getRecommendedBannerInfo()
//                .compose(bindToLifecycle())
                .map(RecommendBannerInfo::getData)
                .flatMap(new Function<List<RecommendBannerInfo.DataBean>, Observable<RecommendInfo>>() {
                    @Override
                    public Observable<RecommendInfo> apply(List<RecommendBannerInfo.DataBean> dataBeans) {
                        LogUtil.i("AAALOG - dataBeans beans: " + dataBeans.toString());
                        recommendBanners.addAll(dataBeans);
                        return RetrofitHelper.getBiliAppAPI().getRecommendedInfo();
                    }
                })
//                .compose(bindToLifecycle())
                .map(RecommendInfo::getResult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultBeans -> {
                    LogUtil.i("AAALOG - Result beans: " + resultBeans.toString());
                    results.addAll(resultBeans);
                    onNext.accept(null);
                }, onError);
    }

    @Override
    public void clear() {
        results.clear();
        recommendBanners.clear();
    }

    private List<RecommendInfo.ResultBean> results = new ArrayList<>();
    private List<RecommendBannerInfo.DataBean> recommendBanners = new ArrayList<>();
}
