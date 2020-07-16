package com.code12.playerframework.log;

import android.util.Log;

public class PLog {

    public static boolean LOG_OPEN = false;

    public static void d(String tag, String message){
        if(!LOG_OPEN)
            return;
        Log.d(tag,message);
    }

    public static void w(String tag, String message){
        if(!LOG_OPEN)
            return;
        Log.w(tag,message);
    }

    public static void e(String tag, String message){
        if(!LOG_OPEN)
            return;
        Log.e(tag,message);
    }

}
