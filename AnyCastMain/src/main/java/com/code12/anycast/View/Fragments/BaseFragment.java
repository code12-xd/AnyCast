/**
 *  Created by code12, 2020-07-05.
 *  fragment's base op.
 */
package com.code12.anycast.View.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.code12.anycast.ViewModel.BaseFragmentViewModel;
import com.code12.anycast.View.auxiliary.ThemeChangeEvent;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public abstract class BaseFragment extends Fragment implements IBaseFragmentView
{
    private View mRootView;
    private FragmentActivity mContainerActivity;

    protected boolean isPrepared;
    protected boolean isVisible;

    protected BaseFragmentViewModel mViewModel;

    public abstract @LayoutRes int getLayoutResId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        mContainerActivity = getSupportActivity();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
//        bind = ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
        initThemeChangeObserver();
    }

    private void initThemeChangeObserver(){
        mViewModel.initThemeChangeSubscription();
        mViewModel.onGlobalThemeChange();
    }

    public abstract void finishCreateView(Bundle state);

    public void onThemeChange(ThemeChangeEvent themeChangeEvent){

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        bind.unbind();
        mViewModel.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContainerActivity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContainerActivity = null;
    }

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    public android.app.ActionBar getSupportActionBar() {
        return getSupportActivity().getActionBar();
    }

    public Context getApplicationContext() {
        return this.mContainerActivity == null ? (getActivity() == null ? null :
                getActivity().getApplicationContext()) : this.mContainerActivity.getApplicationContext();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible()
    {
        onLazyLoad();
    }

    @Override
    public void onInvisible() {

    }

    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {
        return (T) mRootView.findViewById(id);
    }

//    @Override
//    public void onSpecificThemeChange(View view) {
//
//    }
}
