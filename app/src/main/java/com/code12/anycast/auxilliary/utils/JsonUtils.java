/**
 *  Created by code12, 2020-07-05.
 */
package com.code12.anycast.auxilliary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class JsonUtils {
    @Nullable
    public static <T> T getDataFromFile(@NonNull Context appContext, @NonNull String fileName, Class<T> type) {
        String jsonData = parseFileToString(appContext, fileName);
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        return new Gson().fromJson(jsonData, type);
    }

    private static String parseFileToString(@NonNull Context appContext, @NonNull String filename) {
        try {
            InputStream stream = appContext.getAssets().open(filename);
            int size = stream.available();
            byte[] bytes = new byte[size];
            stream.read(bytes);
            stream.close();
            return new String(bytes);
        } catch (IOException e) {
            Log.i(JsonUtils.class.getSimpleName(), "IOException: " + e.getMessage());
        }
        return null;
    }
}
