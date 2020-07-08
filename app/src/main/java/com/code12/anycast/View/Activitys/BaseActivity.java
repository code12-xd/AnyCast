/**
 *  Created by code12, 2020-07-05.
 *  Base activity, handle container's base ops.
 */
package com.code12.anycast.View.Activitys;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.code12.anycast.R;
import com.code12.anycast.ViewModel.BaseFragmentViewModel;
import com.code12.anycast.ViewModel.BaseViewModel;
import com.code12.anycast.auxilliary.utils.LogUtil;
import com.code12.anycast.View.auxiliary.ThemeHelper;

public abstract class BaseActivity extends AppCompatActivity  {
//    private Unbinder bind;
    private BaseViewModel mBaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        bind = ButterKnife.bind(this);
        bindViews();
        initViewModel();
        initViews(savedInstanceState);
        initToolbar();
        initTheme();
    }

    protected abstract void bindViews();
    protected abstract int getLayoutId();
    protected abstract void initViews(Bundle savedInstanceState);
    protected abstract void initToolbar();
    protected void initTheme() {
        mBaseViewModel.initThemeChangeSubscription();
        mBaseViewModel.onInitThemeChange();
    }

    protected void initViewModel() {
        mBaseViewModel = ViewModelProviders.of(this).get(BaseFragmentViewModel.class);
        //TODO:?? new BaseViewModel(this);
    }

    protected void initStatusBar(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initStatusBar();
    }

    //@Override
    public void onConfirm(int currentTheme) {
        LogUtil.d("onConfirm = " + currentTheme);

        if (ThemeHelper.getTheme(BaseActivity.this) != currentTheme) {
            ThemeHelper.setTheme(BaseActivity.this, currentTheme);
            ThemeUtils.refreshUI(BaseActivity.this, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            //for global setting, just do once
                            if (Build.VERSION.SDK_INT >= 21) {
                                final BaseActivity context = BaseActivity.this;
                                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                            }
                            mBaseViewModel.onGlobalThemeChange();
                        }

                        @Override
                        public void refreshSpecificView(View view) {
                            //TODO: will do this for each traversal
                            // post主题切换的消息
                            mBaseViewModel.onSpecificThemeChange(view);
                        }
                    }
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        bind.unbind();
        mBaseViewModel.onDestroyView();
    }
}
