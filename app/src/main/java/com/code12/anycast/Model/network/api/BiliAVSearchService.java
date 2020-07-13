package com.code12.anycast.Model.network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BiliAVSearchService {
    /**
     * 通过av号获取html
     * https://www.bilibili.com/video/av711362669/
     * @return
     */
    @GET("video/av{av}")
    Observable<ResponseBody> getAVHtml(@Path("av") String av);
}
