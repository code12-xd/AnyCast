package com.code12.anycast.View.adapters;

import android.content.Context;

import com.code12.anycast.R;
import com.code12.anycast.View.Fragments.GameFragment;
import com.code12.anycast.View.Fragments.LiveFragment;
import com.code12.anycast.View.Fragments.RecommendFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private Fragment[] fragments;

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections);
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = LiveFragment.newInstance();
                    break;
                case 1:
                    fragments[position] = RecommendFragment.newInstance();
                    break;
                case 2:
                    fragments[position] = GameFragment.newInstance();
                    break;
                case 3:
                    fragments[position] = LiveFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
