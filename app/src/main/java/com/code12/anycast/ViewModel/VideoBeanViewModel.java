/**
 *  Created by code12, 2020-07-08.
 */
package com.code12.anycast.ViewModel;

import android.app.Application;

import com.code12.anycast.Model.network.RetrofitHelper;
import com.code12.anycast.Model.types.GameInfo;
import com.code12.anycast.View.auxiliary.ToastUtil;
import com.code12.anycast.auxilliary.utils.ConstantUtil;
import com.code12.anycast.auxilliary.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class VideoBeanViewModel extends BaseBeanViewModel {
    public VideoBeanViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void parseUrl(String url) {
        url += "/"; //TODO: ??
        RetrofitHelper.getVideoAPI(url)
                .getVideoUrlContent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseBody -> parseVideoUrl(responseBody))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //TODO: ?? compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.ShortToast("error");
                    }
                    @Override
                    public void onNext(String url) {
                        LogUtil.test(url);
                        //TODO:?? MediaPlayerActivity.configPlayer((Activity)context).setTitle(livesBean.getTitle()).setFullScreenOnly(true).playLive(url);
                    }
                });
    }

    @Override
    public void parseUrlById(String id) {
        mUrl = ConstantUtil.RANK_BASE_URL + "video/av" + id;
        RetrofitHelper.getBiliAVSearchAPI()
                .getAVHtml(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(body -> {
                    htmlParser(body);
                        });
    }

    private void htmlParser(ResponseBody responseBody) {
        VideoInfo vinfo = new VideoInfo();
        String html = "";
        try {
            html = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = Jsoup.parse(html);
        Element title = document.getElementsByTag("title").first();
        // 视频名称
        vinfo.videoName = title.text();
        // 截取视频信息
        Pattern pattern = Pattern.compile("(?<=<script>window.__playinfo__=).*?(?=</script>)");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            try {
                vinfo.videoInfo = new JSONObject(matcher.group());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("未匹配到视频信息，退出程序！");
            return;
        }
        getVideoInfo(vinfo);
    }

    /** 解析视频和音频的具体信息 */
    private void getVideoInfo(VideoInfo vinfo){
        // 获取视频的基本信息
        JSONObject videoInfo = vinfo.videoInfo;
        try {
            JSONArray videoInfoArr = videoInfo.getJSONObject("data").getJSONObject("dash").getJSONArray("video");
            vinfo.videoBaseUrl = (String)videoInfoArr.getJSONObject(0).get("baseUrl");
            vinfo.videoBaseRange = (String)videoInfoArr.getJSONObject(0).getJSONObject("SegmentBase").get("Initialization");
//            HttpResponse videoRes = HttpRequest.get(vinfo.videoBaseUrl)
//                    .header("Referer", mUrl)
//                    .header("Range", "bytes=" + vinfo.videoBaseRange)
//                    .header("User-Agent", USER_AGENT)
//                    .timeout(2000)
//                    .execute();
//            vinfo.videoSize = videoRes.header("Content-Range").split("/")[1];

            // 获取音频基本信息
            JSONArray audioInfoArr = videoInfo.getJSONObject("data").getJSONObject("dash").getJSONArray("audio");
            vinfo.audioBaseUrl = (String)audioInfoArr.getJSONObject(0).get("baseUrl");
            vinfo.audioBaseRange = (String)audioInfoArr.getJSONObject(0).getJSONObject("SegmentBase").get("Initialization");
//            HttpResponse audioRes = HttpRequest.get(VIDEO_INFO.audioBaseUrl)
//                    .header("Referer", VIDEO_URL)
//                    .header("Range", "bytes=" + VIDEO_INFO.audioBaseRange)
//                    .header("User-Agent", USER_AGENT)
//                    .timeout(2000)
//                    .execute();
//            vinfo.audioSize = audioRes.header("Content-Range").split("/")[1];
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class VideoInfo {
        public VideoInfo() {}
        public String videoName;
        public JSONObject videoInfo;
        public String videoBaseUrl;
        public String audioBaseUrl;
        public String videoBaseRange;
        public String audioBaseRange;
        public String videoSize;
        public String audioSize;
    }

    private String mUrl;
    private static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36";
}
