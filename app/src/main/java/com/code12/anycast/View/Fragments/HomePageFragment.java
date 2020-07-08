package com.code12.anycast.View.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.code12.anycast.R;
import com.code12.anycast.View.Activitys.MainActivity;
import com.code12.anycast.View.Views.CircleImageView;
import com.code12.anycast.View.adapters.HomePagerAdapter;
import com.code12.anycast.ViewModel.BaseFragmentViewModel;
import com.flyco.tablayout.SlidingTabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class HomePageFragment extends BaseFragment
{
//    @BindView(R.id.view_pager)
    ViewPager mViewPager;

//    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;

//    @BindView(R.id.toolbar)
    Toolbar mToolbar;

//    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    public static HomePageFragment newInstance()
    {
        return new HomePageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We are not creating instance of ViewModel class here.
        mViewModel = ViewModelProviders.of(this).get(BaseFragmentViewModel.class);
    }

    @Override
    public int getLayoutResId()
    {
        return R.layout.fragment_home;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mViewPager = getView().findViewById(R.id.view_pager);
        mSlidingTab = getView().findViewById(R.id.sliding_tabs);
//        mToolbar = getView().findViewById(R.id.toolbar);
        mSearchView = getView().findViewById(R.id.search_view);
        getView().findViewById(R.id.navigation_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer();
            }
        });

        setHasOptionsMenu(true);
        initToolBar();
        initSearchView();
        initViewPager();
    }

    private void initToolBar() {
//        mToolbar.setTitle("");
//        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
    }

    private void initSearchView() {
        //初始化SearchBar
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);
        //TODO:mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                TotalStationSearchActivity.launch(getActivity(), query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
    }


    private void initViewPager() {
        HomePagerAdapter mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(),
                getActivity().getApplicationContext());
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mHomeAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);

        // 设置SearchViewItemMenu
        MenuItem item = menu.findItem(R.id.id_action_search);
        mSearchView.setMenuItem(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.id_action_download:
                //离线缓存
//                startActivity(new Intent(getActivity(), OffLineDownloadActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    @OnClick(R.id.navigation_layout)
    void toggleDrawer()
    {
        Activity activity = getActivity();
        if (activity instanceof MainActivity)
            ((MainActivity) activity).toggleDrawer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    mSearchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isOpenSearchView()
    {
        return mSearchView.isSearchOpen();
    }

    public void closeSearchView() {
        mSearchView.closeSearch();
    }

    @Override
    public void onLazyLoad() { }

    @Override
    public void onInvisible() { }
}
