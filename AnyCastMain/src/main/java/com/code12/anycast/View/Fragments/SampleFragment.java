/**
 *  Created by code12, 2020-07-14.
 */
package com.code12.anycast.View.Fragments;

import android.os.Bundle;
import android.view.View;

import com.code12.anycast.Model.types.GameInfo;
import com.code12.anycast.Model.types.SampleInfo;
import com.code12.anycast.R;
import com.code12.anycast.View.Views.CustomEmptyView;
import com.code12.anycast.View.adapters.GameAdapter;
import com.code12.anycast.View.adapters.SampleAdapter;
import com.code12.anycast.View.auxiliary.SnackbarUtil;
import com.code12.anycast.ViewModel.GameViewModel;
import com.code12.anycast.ViewModel.SampleViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SampleFragment extends BaseFragment
{
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CustomEmptyView mCustomEmptyView;
    private SampleAdapter mAdapter;

    public static SampleFragment newInstance()
    {
        return new SampleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We are not creating instance of ViewModel class here.
        mViewModel = ViewModelProviders.of(this).get(SampleViewModel.class);
    }

    @Override
    public int getLayoutResId()
    {
        return R.layout.fragment_live;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mRecyclerView = getView().findViewById(R.id.recycle);
        mSwipeRefreshLayout = getView().findViewById(R.id.swipe_refresh_layout);
        mCustomEmptyView = getView().findViewById(R.id.empty_layout);

        isPrepared = true;
        onLazyLoad();
    }

    @Override
    public void onLazyLoad() {
        if (!isPrepared || !isVisible)
            return;

        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }

    protected void initRecyclerView() {
        mAdapter = new SampleAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 2);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layout);
    }

    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_color_primary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }

    private void loadData() {
        mViewModel.loadDataObservable().subscribe(info -> {
            mAdapter.setSampleInfo((SampleInfo)info);
            finishTask();
        }, throwable -> {
            initEmptyView();
        });
    }

    private void initEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText(getResources().getText(R.string.warning_loading_fail));
        SnackbarUtil.showMessage(mRecyclerView, getResources().getText(R.string.warning_network_retry).toString());
    }

    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

//    @Override
    public void finishTask() {
        hideEmptyView();
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }
}
