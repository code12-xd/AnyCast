/**
 *  Created by code12, 2020-07-05.
 *  Common utils, related with network and storage.
 */
package com.code12.anycast.auxilliary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class CommonUtil {
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    public static boolean isWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    public static long getPhoneTotalSize() {
        if (!checkSdCard()) {
            File path = Environment.getDataDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long blockCountLong = mStatFs.getBlockCountLong();
            return blockSizeLong * blockCountLong;
        } else
            return getSDcardTotalSize();
    }

    public static long getPhoneAvailableSize() {
        if (!checkSdCard()) {
            File path = Environment.getDataDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long availableBlocksLong = mStatFs.getAvailableBlocksLong();
            return blockSizeLong * availableBlocksLong;
        } else
            return getSDcardAvailableSize();
    }

    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    private static boolean checkSdCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private static long getSDcardTotalSize() {
        if (checkSdCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long blockCountLong = mStatFs.getBlockCountLong();

            return blockSizeLong * blockCountLong;
        } else {
            return 0;
        }
    }

    private static long getSDcardAvailableSize() {
        if (checkSdCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long availableBlocksLong = mStatFs.getAvailableBlocksLong();
            return blockSizeLong * availableBlocksLong;
        } else
            return 0;
    }
}
