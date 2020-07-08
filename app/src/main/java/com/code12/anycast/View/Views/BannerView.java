package com.code12.anycast.View.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bilibili.magicasakura.widgets.TintView;
import com.code12.anycast.R;
import com.code12.anycast.View.adapters.BannerAdapter;
import com.code12.anycast.Model.types.BannerEntity;
import com.code12.anycast.auxilliary.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.viewpager.widget.ViewPager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class BannerView extends RelativeLayout implements BannerAdapter.ViewPagerOnItemClickListener {
//    @BindView(R.id.layout_banner_viewpager)
    ViewPager viewPager;
//    @BindView(R.id.layout_banner_points_group)
    LinearLayout points;
    private CompositeDisposable compositeSubscription;
    private int delayTime = 10;
    private List<ImageView> imageViewList;
    private List<BannerEntity> bannerList;
    private int selectRes = R.drawable.shape_dots_select;
    private int unSelcetRes = R.drawable.shape_dots_default;
    private int currrentPos;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_banner, this, true);
        //ButterKnife.bind(this);
        viewPager = findViewById(R.id.layout_banner_viewpager);
        points = findViewById(R.id.layout_banner_points_group);

        imageViewList = new ArrayList<>();
    }

    public BannerView delayTime(int time) {
        this.delayTime = time;
        return this;
    }

    public void setPointsRes(int selectRes, int unselcetRes) {
        this.selectRes = selectRes;
        this.unSelcetRes = unselcetRes;
    }

    public void build(List<BannerEntity> list) {
        destory();
        if (list.size() == 0) {
            this.setVisibility(GONE);
            return;
        }

        bannerList = new ArrayList<>();
        bannerList.addAll(list);
        final int pointSize;
        pointSize = bannerList.size();

        if (pointSize == 2) {
            bannerList.addAll(list);
        }
        if (points.getChildCount() != 0) {
            points.removeAllViewsInLayout();
        }

        for (int i = 0; i < pointSize; i++) {
            TintView dot = new TintView(getContext());
            dot.setBackgroundResource(unSelcetRes);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DisplayUtil.dp2px(getContext(), 5),
                    DisplayUtil.dp2px(getContext(), 5));
            params.leftMargin = 10;
            dot.setLayoutParams(params);
            dot.setEnabled(false);
            points.addView(dot);
        }

        points.getChildAt(0).setBackgroundResource(selectRes);

        for (int i = 0; i < bannerList.size(); i++) {
            ImageView mImageView = new ImageView(getContext());

            Glide.with(getContext())
                    .load(bannerList.get(i).img)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.bili_default_image_tv)
                    .dontAnimate()
                    .into(mImageView);
            imageViewList.add(mImageView);
        }

        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                pos = pos % pointSize;
                currrentPos = pos;
                for (int i = 0; i < points.getChildCount(); i++) {
                    points.getChildAt(i).setBackgroundResource(unSelcetRes);
                }
                points.getChildAt(pos).setBackgroundResource(selectRes);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isStopScroll)
                        {
                            startScroll();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        stopScroll();
                        compositeSubscription.clear();
                        break;
                }
            }
        });

        BannerAdapter bannerAdapter = new BannerAdapter(imageViewList);
        viewPager.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
        bannerAdapter.setmViewPagerOnItemClickListener(this);

        startScroll();
    }

    private boolean isStopScroll = false;

    private void startScroll() {
        compositeSubscription = new CompositeDisposable();
        isStopScroll = false;
        Disposable subscription = Observable.timer(delayTime, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

                    if (isStopScroll)
                        return;

                    isStopScroll = true;
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                });
        compositeSubscription.add(subscription);
    }

    private void stopScroll() {
        isStopScroll = true;
    }

    public void destory() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }

    @Override
    public void onItemClick() {
//        BrowserActivity.launch((Activity) getContext(),
//                bannerList.get(currrentPos).link,
//                bannerList.get(currrentPos).title);
    }
}
