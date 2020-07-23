/**
 *  Created by code12, 2020-07-20.
 */
package com.code12.anycast.Model.types;

import android.net.Uri;

import com.code12.anybaseui.Model.BaseBean;
import com.code12.anybaseui.Model.DataLoader;
import com.code12.anybaseui.Model.IDataLoader;
import com.code12.anycast.Model.network.RetrofitHelper;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocalMediaInfo extends BaseBean {
    private List<MusicBean> result;

    public List<MusicBean> getResult() {
        return result;
    }

    public void setResult(List<MusicBean> result) {
        this.result = result;
    }

    public static class MusicBean {
        private int id;
        private String title;
        private String album;
        private String albumimage;
        private String artist;
        private int duration;
        private String data;
        private Uri uri;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() { return id; }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() { return title; }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getAlbum() { return album; }

        public void setAlbumimage(String albumimage) {
            this.albumimage = albumimage;
        }

        public String getAlbumimage() { return albumimage; }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getArtist() { return artist; }

        public void setUri(Uri uri) {
            this.uri = uri;
        }

        public Uri getUri() { return uri; }
    }
}
