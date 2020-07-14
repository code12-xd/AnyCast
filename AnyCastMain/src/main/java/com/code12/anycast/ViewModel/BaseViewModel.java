/**
 *  Created by code12, 2020-07-05.
 */
package com.code12.anycast.ViewModel;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.code12.anycast.View.auxiliary.ThemeChangeEvent;
import com.code12.anycast.auxilliary.utils.rx.AppRxSchedulers;
import com.code12.anycast.auxilliary.utils.rx.RxBus;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BaseViewModel extends AndroidViewModel implements IBaseViewModel {

    private CompositeDisposable mCompositeDisposable;
    private AppRxSchedulers mAppRxSchedulers;

    /**
     * Constructor with application instance and initialize {@link CompositeDisposable}
     *
     * @param application instance of application
     */
    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.mCompositeDisposable = new CompositeDisposable();
        this.mAppRxSchedulers = new AppRxSchedulers();
    }

    /**
     * Get instance of {@link CompositeDisposable}
     *
     * @return CompositeDisposable instance of {@link CompositeDisposable}
     */
    @NonNull
    public CompositeDisposable getCompositeDisposable() {
        return this.mCompositeDisposable;
    }

    /**
     * Add disposable to {@link CompositeDisposable}
     *
     * @param disposable instance of {@link Disposable}
     */
    public void addDisposable(@NonNull Disposable disposable) {
        this.mCompositeDisposable.add(disposable);
    }

    /**
     * Get instance of {@link AppRxSchedulers}
     *
     * @return AppRxSchedulers instance of {@link AppRxSchedulers}
     */
    @NonNull
    public AppRxSchedulers getAppRxSchedulers() {
        return this.mAppRxSchedulers;
    }

    /**
     * Get application context.
     *
     * @return Context application context.
     */
    @NonNull
    public Context getAppContext() {
        return getApplication().getApplicationContext();
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        // Clear everything here.
        if (this.mCompositeDisposable != null && !this.mCompositeDisposable.isDisposed()) {
            this.mCompositeDisposable.dispose();
        }
        if (this.mAppRxSchedulers != null) {
            this.mAppRxSchedulers = null;
        }
    }

    public void onInitThemeChange() {
        RxBus.getInstance().post(new ThemeChangeEvent(ThemeChangeEvent.INIT_CHANGE));
    }

    public void onGlobalThemeChange() {
        RxBus.getInstance().post(new ThemeChangeEvent(ThemeChangeEvent.GLOBLE_CHANGE));
    }

    public void onSpecificThemeChange(View view) {
        RxBus.getInstance().post(new ThemeChangeEvent(ThemeChangeEvent.SPECIFIC_CHANGE, view));
    }

    public void initThemeChangeSubscription() {
        mCompositeDisposable.add(RxBus.getInstance().toObserverable(ThemeChangeEvent.class).subscribe(this::onThemeChange));
    }

    public void onThemeChange(ThemeChangeEvent themeChangeEvent) {
        switch (themeChangeEvent.eventType) {
            case ThemeChangeEvent.INIT_CHANGE:
            case ThemeChangeEvent.GLOBLE_CHANGE:
                //TODO:mIRxBaseView.onGlobalThemeChange();
                break;
            case ThemeChangeEvent.SPECIFIC_CHANGE:
                //TODO:mIRxBaseView.onSpecificThemeChange(themeChangeEvent.view);
                break;
        }
    }

    public void onDestroyView() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void loadData() { }

    @Override
    public <T> void loadData(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {}

    @Override
    public Observable loadDataObservable() {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
            }
        };
    }

    @Override
    public boolean isLoading() {
        return true; //TODO: ?? simply return true now
    }

    @Override
    public void clear() {}

    @Override
    public void finishTask() { }
}
