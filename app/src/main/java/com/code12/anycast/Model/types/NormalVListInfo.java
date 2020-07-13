/**
 *  Created by code12, 2020-07-09.
 *  Access ali cloud normal videos list.
 */
package com.code12.anycast.Model.types;

import com.code12.anybaseui.Model.BaseBean;
import com.code12.anybaseui.Model.DataLoader;
import com.code12.anybaseui.Model.IBean;
import com.code12.anybaseui.Model.IDataLoader;
import com.code12.anycast.Model.network.AliRequestFactory;
import com.code12.anycast.Model.network.RetrofitHelper;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//{"MediaList":
//    [{"AttachedMedia":{},
//        "MediaId":"dc785d00a0fb4edebeec50520c4068bc",
//        "Video":{"VideoId":"dc785d00a0fb4edebeec50520c4068bc",
//                "Title":"VID_20200709_133243.mp4",
//                "CoverURL":"http://outin-d0f241d8c24911ea8caf00163e1c35d5.oss-cn-shanghai.aliyuncs.com/dc785d00a0fb4edebeec50520c4068bc/snapshots/96065d99a7c54bfeb0851359e7386e11-00001.jpg?Expires=1594348445&OSSAccessKeyId=LTAIVVfYx6D0HeL2&Signature=ejHaqWOnRBp5LJpcQhmggX%2Fo8y8%3D"},
//        "CreationTime":"2020-07-10T01:18:54Z",
//        "MediaType":"video",
//        "Audio":{},
//        "Image":{}}],
//    "RequestId":"B71597D9-254F-45E1-9FD0-35BA13A355CF",
//    "ScrollToken":"2716cd7937a85fb9e4127b9a12d22cb4",
//    "Total":1}
public class NormalVListInfo extends BaseBean {
    @SerializedName("MediaList")
    private List<MediaBean> result;
    @SerializedName("RequestId")
    private String rid;
    @SerializedName("ScrollToken")
    private String token;
    @SerializedName("Total")
    private int num;

    public List<MediaBean> getResult() {
        return result;
    }

    public void setResult(List<MediaBean> result) {
        this.result = result;
    }

//    {"AttachedMedia":{},
//        "MediaId":"dc785d00a0fb4edebeec50520c4068bc",
//        "Video":{"VideoId":"dc785d00a0fb4edebeec50520c4068bc",
//                "Title":"VID_20200709_133243.mp4",
//                "CoverURL":"http://outin-d0f241d8c24911ea8caf00163e1c35d5.oss-cn-shanghai.aliyuncs.com/dc785d00a0fb4edebeec50520c4068bc/snapshots/96065d99a7c54bfeb0851359e7386e11-00001.jpg?Expires=1594348445&OSSAccessKeyId=LTAIVVfYx6D0HeL2&Signature=ejHaqWOnRBp5LJpcQhmggX%2Fo8y8%3D"},
//        "CreationTime":"2020-07-10T01:18:54Z",
//        "MediaType":"video",
//        "Audio":{},
//        "Image":{}}
    public static class MediaBean {
        @SerializedName("AttachedMedia")
        private Object attached;
        @SerializedName("MediaId")
        private String id;
        @SerializedName("Video")
        private VideoBean video;
        @SerializedName("CreationTime")
        private String time;
        @SerializedName("MediaType")
        private String type;
        @SerializedName("Audio")
        private Object audio;
        @SerializedName("Image")
        private Object image;

        public VideoBean getVideo() {
            return video;
        }
    }

//           "VideoId":"dc785d00a0fb4edebeec50520c4068bc",
//           "Title":"VID_20200709_133243.mp4",
//           "CoverURL":"http://outin-d0f241d8c24911ea8caf001
    public static class VideoBean {
        @SerializedName("VideoId")
        private String vid;
        @SerializedName("Title")
        private String title;
        @SerializedName("CoverURL")
        private String coverurl;

        public String getVid() { return vid; }
        public String getTitle() { return title; }
        public String getCoverurl() { return coverurl; }
    }

    @Override
    public IDataLoader getLoader() {
        return mLoader;
    }

    private static Loader mLoader = new Loader();

    // bean's data loader, must implement to do own load action
    private static class Loader extends DataLoader<NormalVListInfo> {
        public Loader() { }

        @Override
        public void load(Consumer onNext, Consumer onError) {
            loadObservable().subscribe((Consumer<? super GameInfo>)onNext, onError);
        }

        @Override
        public Observable loadObservable() {
            Map<String ,String> params = AliRequestFactory.buildMediaListRequestParams();
            return RetrofitHelper.getNormalVAPI()
                    .getMediaList(params)
                    //TODO:?? .compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
