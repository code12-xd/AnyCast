package com.code12.anycast;

import android.app.Application;
import android.content.Context;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.code12.anycast.View.auxiliary.ThemeHelper;
import com.code12.exowrapper.ExoMediaPlayer;
import com.code12.ijkwrapper.IjkPlayer;
import com.code12.playerframework.config.PlayerChooser;
import com.code12.playerframework.config.PlayerLibrary;
import com.code12.playerframework.decoder.DecoderPlan;
import com.code12.playerframework.record.PlayRecordManager;
import com.facebook.stetho.Stetho;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

public class AcApplication extends Application implements ThemeUtils.switchColor{
    public static AcApplication mInstance;

    public static final int PLAN_ID_IJK = 1;
    public static final int PLAN_ID_EXO = 2;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    public static AcApplication getInstance() {
        return mInstance;
    }


    private void init() {
        ThemeUtils.setSwitchColor(this);
        //TODO:?? LeakCanary.install(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        PlayerChooser.addDecoderPlan(new DecoderPlan(PLAN_ID_IJK, IjkPlayer.class.getName(), "IjkPlayer"));
        PlayerChooser.addDecoderPlan(new DecoderPlan(PLAN_ID_EXO, ExoMediaPlayer.class.getName(), "ExoPlayer"));
        PlayerChooser.setDefaultPlanId(PLAN_ID_EXO);

        //use default NetworkEventProducer.
        PlayerChooser.setUseDefaultNetworkEventProducer(true);

        PlayerChooser.playRecord(true);

        PlayRecordManager.setRecordConfig(
                new PlayRecordManager.RecordConfig.Builder()
                        .setMaxRecordCount(100).build());

        PlayerLibrary.attach(this);

        try {
            Class.forName("android.os.AsyncTask");
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return context.getResources().getColor(colorId);
        }
        String theme = getTheme(context);
        if (theme != null) {
            colorId = getThemeColorId(context, colorId, theme);
        }
        return context.getResources().getColor(colorId);
    }

    @Override
    public int replaceColor(Context context, @ColorInt int originColor) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return originColor;
        }
        String theme = getTheme(context);
        int colorId = -1;

        if (theme != null) {
            colorId = getThemeColor(context, originColor, theme);
        }
        return colorId != -1 ? getResources().getColor(colorId) : originColor;
    }

    private String getTheme(Context context) {
        if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_STORM) {
            return "blue";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_HOPE) {
            return "purple";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_WOOD) {
            return "green";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_LIGHT) {
            return "green_light";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_THUNDER) {
            return "yellow";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_SAND) {
            return "orange";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_FIREY) {
            return "red";
        }
        return null;
    }

    private @ColorRes int getThemeColorId(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.theme_color_primary_trans:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return colorId;
    }

    private @ColorRes int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xfffb7299:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case 0xffb85671:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case 0x99f0486c:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return -1;
    }
}
