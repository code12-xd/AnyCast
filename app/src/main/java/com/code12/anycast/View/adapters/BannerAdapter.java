package com.code12.anycast.View.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class BannerAdapter extends PagerAdapter {
    private List<ImageView> mList;
    private int pos;
    private ViewPagerOnItemClickListener mViewPagerOnItemClickListener;

    public void setmViewPagerOnItemClickListener(ViewPagerOnItemClickListener mViewPagerOnItemClickListener) {
        this.mViewPagerOnItemClickListener = mViewPagerOnItemClickListener;
    }

    public BannerAdapter(List<ImageView> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= mList.size();
        if (position < 0) {
            position = mList.size() + position;
        }
        ImageView v = mList.get(position);
        pos = position;
        v.setScaleType(ImageView.ScaleType.CENTER);
        ViewParent vp = v.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(v);
        }
        v.setOnClickListener(v1 -> {
            if (mViewPagerOnItemClickListener != null) {
                mViewPagerOnItemClickListener.onItemClick();
            }
        });

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) { }

    public interface ViewPagerOnItemClickListener {
        void onItemClick();
    }
}
