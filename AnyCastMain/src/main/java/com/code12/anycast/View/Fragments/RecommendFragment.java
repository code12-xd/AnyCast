/**
 *  Created by code12, 2020-07-05.
 *  Recommend items show container.
 */
package com.code12.anycast.View.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.code12.anycast.R;
import com.code12.anycast.View.Views.CustomEmptyView;
import com.code12.anycast.View.adapters.SectionedRecyclerViewAdapter;
import com.code12.anycast.View.section.HomeRecommendedSection;
import com.code12.anycast.ViewModel.RecommendViewModel;
import com.code12.anycast.View.section.HomeRecommendBannerSection;
import com.code12.anycast.Model.types.BannerEntity;
import com.code12.anycast.Model.types.RecommendInfo;
import com.code12.anycast.auxilliary.utils.ConstantUtil;
import com.code12.anycast.View.auxiliary.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.Observable;

public class RecommendFragment extends BaseFragment
{
//    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
//    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    private List<BannerEntity> banners = new ArrayList<>();
    private boolean mIsRefreshing = false;
    private SectionedRecyclerViewAdapter mSectionedAdapter;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We are not creating instance of ViewModel class here.
        mViewModel = ViewModelProviders.of(this).get(RecommendViewModel.class);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mSwipeRefreshLayout = getView().findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = getView().findViewById(R.id.recycle);
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
        mSectionedAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSectionedAdapter);
        setRecycleNoScroll();
    }

    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_color_primary);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            mIsRefreshing = true;
            loadData();
        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            clearData();
            loadData();
        });
    }

    //@Override
    public void loadData() {
        mViewModel.loadData(resultBeans -> {
            finishTask();
        }, throwable -> {
            initEmptyView();
        });
    }

    private void convertBanner() {
        Observable.fromIterable(((RecommendViewModel)mViewModel).getDataBeans())
//                .compose(bindToLifecycle())
                .forEach(dataBean -> banners.add(new BannerEntity(dataBean.getValue(),
                        dataBean.getTitle(), dataBean.getImage())));
    }

    public void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        hideEmptyView();
        convertBanner();
        mSectionedAdapter.addSection(new HomeRecommendBannerSection(banners));

        List<RecommendInfo.ResultBean> results = ((RecommendViewModel)mViewModel).getResultBeans();
        int size = results.size();
        for (int i = 0; i < size; i++) {
            String type = results.get(i).getType();
            if (!TextUtils.isEmpty(type))
                switch (type)
                {
                    case ConstantUtil.TYPE_TOPIC:
                        //话题
//                        mSectionedAdapter.addSection(new HomeRecommendTopicSection(getActivity(),
//                                results.get(i).getBody().get(0).getCover(),
//                                results.get(i).getBody().get(0).getTitle(),
//                                results.get(i).getBody().get(0).getParam()));
                        break;
                    case ConstantUtil.TYPE_ACTIVITY_CENTER:
//                        //活动中心
//                        mSectionedAdapter.addSection(new HomeRecommendActivityCenterSection(
//                                getActivity(),
//                                results.get(i).getBody()));
                        break;
                    default:
                        mSectionedAdapter.addSection(new HomeRecommendedSection(
                                getActivity(),
                                results.get(i).getHead().getTitle(),
                                results.get(i).getType(),
                                results.get(1).getHead().getCount(),
                                results.get(i).getBody()));
                        break;
                }

//            String style = results.get(i).getHead().getStyle();
//            if (style.equals(ConstantUtil.STYLE_PIC))
//            {
//                mSectionedAdapter.addSection(new HomeRecommendPicSection(getActivity(),
//                        results.get(i).getBody().get(0).getCover(),
//                        results.get(i).getBody().get(0).getParam()));
//            }
        }
        mSectionedAdapter.notifyDataSetChanged();
    }

    public void initEmptyView() {
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

    private void clearData() {
        banners.clear();
        mViewModel.clear();
        mIsRefreshing = true;
        mSectionedAdapter.removeAllSections();
    }

    private void setRecycleNoScroll() {
        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }

    //TODO:??
//    @Override
//    public void onGlobalThemeChange() {
//        super.onGlobalThemeChange();
//        mSwipeRefreshLayout.setColorSchemeColors(ThemeUtils.getColorById(getActivity(),R.color.theme_color_primary));
//    }
}
