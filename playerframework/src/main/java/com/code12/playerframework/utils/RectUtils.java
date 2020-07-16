package com.code12.playerframework.utils;

import android.graphics.Rect;

public class RectUtils {
    public static Rect getOvalRect(Rect rect){
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;

        int left;
        int top;
        int right;
        int bottom;
        int dW = width/2;
        int dH = height/2;
        if(width > height){
            left   = dW - dH;
            top    = 0;
            right  = dW + dH;
            bottom = dH * 2;
        }else{
            left = dH - dW;
            top = 0;
            right = dH + dW;
            bottom = dW * 2;
        }
        return new Rect(left, top, right, bottom);
    }
}
