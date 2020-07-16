package com.code12.playerframework.utils;

import android.content.Context;

public class PUtils {

    public static int getStatusBarHeight(Context context){
        int height = getStatusBarHeightMethod1(context);
        if(height<=0){
            height = getStatusBarHeightMethod2(context);
        }
        return height;
    }

    private static int getStatusBarHeightMethod1(Context context){
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private static int getStatusBarHeightMethod2(Context context){
        return (int) Math.ceil(20 * context.getResources().getDisplayMetrics().density);
    }
}
