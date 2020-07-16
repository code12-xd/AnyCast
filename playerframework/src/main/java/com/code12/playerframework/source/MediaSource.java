/*
 * Copyright (C) 2020 code12
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *  Created by code12, 2020-07-15.
 *  Source class to resolve the media data.
 */
package com.code12.playerframework.source;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.text.TextUtils;

import com.code12.playerframework.provider.IDataProvider;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class MediaSource implements Serializable {
    private String tag;
    private String sid;

    /**
     * Usually it's a video url.
     */
    private String data;
    private String title;
    private long id;
    private Uri uri;
    private HashMap<String, String> extra;

    private TimedTextSource timedTextSource;

    private String assetsPath;
    private int rawId = -1;

    private int startPos;
    private boolean isLive;

    public MediaSource() {
    }

    public MediaSource(String data) {
        this.data = data;
    }

    public MediaSource(String tag, String data) {
        this.tag = tag;
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public HashMap<String, String> getExtra() {
        return extra;
    }

    public void setExtra(HashMap<String, String> extra) {
        this.extra = extra;
    }

    public TimedTextSource getTimedTextSource() {
        return timedTextSource;
    }

    public void setTimedTextSource(TimedTextSource timedTextSource) {
        this.timedTextSource = timedTextSource;
    }

    public String getAssetsPath() {
        return assetsPath;
    }

    public void setAssetsPath(String assetsPath) {
        this.assetsPath = assetsPath;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    public int getRawId() {
        return rawId;
    }

    public void setRawId(int rawId) {
        this.rawId = rawId;
    }

    public static Uri buildRawPath(String packageName, int rawId){
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + rawId);
    }

    public static Uri buildAssetsUri(String assetsPath){
        return Uri.parse("file:///android_asset/" + assetsPath);
    }

    public static AssetFileDescriptor getAssetsFileDescriptor(Context context, String assetsPath){
        try {
            if(TextUtils.isEmpty(assetsPath))
                return null;
            return context.getAssets().openFd(assetsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
