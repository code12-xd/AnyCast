/**
 *  Created by code12, 2020-07-05.
 *  Main activity, app's entry.
 */
package com.code12.anycast.View.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
//import butterknife.BindView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.code12.anycast.R;
import com.code12.anycast.View.Fragments.HomePageFragment;
import com.code12.anycast.View.Views.CircleImageView;
import com.code12.anycast.auxilliary.utils.ConstantUtil;
import com.code12.anycast.auxilliary.utils.LogUtil;
import com.code12.anycast.auxilliary.utils.PreferenceUtil;
import com.code12.anycast.View.auxiliary.ToastUtil;
import com.code12.playerframework.config.PlayerChooser;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = MainActivity.class.getSimpleName();
//    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
//    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    private Fragment[] fragments;
    private HomePageFragment mHomePageFragment;
    private int currentTabIndex;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindViews() {
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void initViews(Bundle savedInstanceState) {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        //初始化Fragment
        initFragments();
        mNavigationView = findViewById(R.id.navigation_view);
        //初始化侧滑菜单
        initNavigationView();

        int c = Integer.valueOf(PreferenceUtil.getString(PreferenceUtil.KEY_PLAYER_CHOOSER, "2"));
        PlayerChooser.setDefaultPlanId(c);
    }

    @Override
    public void initToolbar() {
    }

    private void initFragments() {
        mHomePageFragment = HomePageFragment.newInstance();
        fragments = new Fragment[]{mHomePageFragment};
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mHomePageFragment)
                .show(mHomePageFragment).commit();
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
        CircleImageView mUserAvatarView = (CircleImageView) headerView.findViewById(R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mUserSign = (TextView) headerView.findViewById(R.id.user_other_info);
        ImageView mSwitchMode = (ImageView) headerView.findViewById(R.id.iv_head_switch_mode);
        mUserAvatarView.setImageResource(R.mipmap.ic_launcher);
        mUserName.setText(getResources().getText(R.string.test_username));
        mUserSign.setText(getResources().getText(R.string.test_username));
        mSwitchMode.setOnClickListener(v -> switchNightMode());

        boolean flag = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (flag) {
            mSwitchMode.setImageResource(R.mipmap.ic_switch_daily);
        } else {
            mSwitchMode.setImageResource(R.mipmap.ic_switch_night);
        }
    }

    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void switchNightMode() {
        boolean isNight = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, true);
        }
        LogUtil.d(TAG," isNight = " +isNight);
        recreate();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.item_theme:
                //TODO: ??
                return true;
            case R.id.item_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1))) {
                mDrawerLayout.closeDrawers();
            } else {
                if (mHomePageFragment != null && mHomePageFragment.isOpenSearchView()) {
                    mHomePageFragment.closeSearchView();
                } else {
                    exitApp();
                }
            }
        }
        return true;
    }

    private long exitTime;
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.ShortToast(R.string.app_exit_toast);
            exitTime = System.currentTimeMillis();
        } else {
//            PreferenceUtil.remove(ConstantUtil.SWITCH_MODE_KEY);
            finish();
        }
    }
}
