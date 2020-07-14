/**
 * By code12, 2020-07-04.
 * ViewModel's base actions.
 */
package com.code12.anycast.ViewModel;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public interface IBaseViewModel {
    //TODO: add loading lifecycle observer, to listen the data loading process.
    void loadData();
    <T> void loadData(Consumer<? super T> onNext, Consumer<? super Throwable> onError);
    Observable loadDataObservable();
    boolean isLoading();
    void clear();
    void finishTask();
//    void FinishLoadData();
//    void onNodata();
//    void onNetDisConnected();
//    void onGlobalThemeChange();
//    void onSpecificThemeChange(View view);
}
