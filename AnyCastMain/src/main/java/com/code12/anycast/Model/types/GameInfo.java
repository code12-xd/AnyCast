/**
 *  Created by code12, 2020-07-07.
 *  Access douyu game list.
 */
package com.code12.anycast.Model.types;

import com.code12.anybaseui.Model.BaseBean;
import com.code12.anybaseui.Model.DataLoader;
import com.code12.anybaseui.Model.IDataLoader;
import com.code12.anycast.Model.network.RetrofitHelper;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GameInfo extends BaseBean {
    private int error;
    @SerializedName("data")
    private List<ResultBean> result;

    public int getCode() {
        return error;
    }

    public void setCode(int code) {
        this.error = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    /**
     * code : 0
     * result : {"room_id":100,"room_src":"https://rpic.douyucdn.cn/asrpic/200707/100_1252.png/dy1",
     * "room_name":"【WEGLS4】9号娱乐赛","owner_uid":"3101","online":1646066,"hn":1646066,
     * "nickname":"斗鱼绝地赛事","url":"http://www.douyu.com/100"}
     */
    public static class ResultBean {
        @SerializedName("room_id")
        private String id;
        @SerializedName("room_src")
        private String src;
        @SerializedName("room_name")
        private String name;
        @SerializedName("owner_uid")
        private String uid;
        @SerializedName("online")
        private int onlinenum;
        private int hn;
        private String nickname;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {

            this.id = id;
        }

        public String getSrc() {

            return src;
        }

        public void setSrc(String src) {

            this.src = src;
        }

        public String getUid() {

            return uid;
        }

        public void setUid(String uid) {

            this.uid = uid;
        }

        public int getOnline() {
            return onlinenum;
        }

        public void setOnline(int onlinenum) {
            this.onlinenum = onlinenum;
        }

        public int getHn() {
            return hn;
        }

        public void setHn(int hn) {
            this.hn = hn;
        }

        public String getNickName() {
            return nickname;
        }

        public void setNickName(String nickname) {
            this.nickname = nickname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public IDataLoader getLoader() {
        return mLoader;
    }

    private static GameInfo.Loader mLoader = new GameInfo.Loader();

    // bean's data loader, must implement to do own load action
    private static class Loader extends DataLoader<NormalVListInfo> {
        public Loader() { }

        @Override
        public void load(Consumer onNext, Consumer onError) {
            loadObservable().subscribe((Consumer<? super GameInfo>)onNext, onError);
        }

        @Override
        public Observable loadObservable() {
            return RetrofitHelper.getGameAPI()
                    .getGameInfo()
                    //TODO:?? .compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
