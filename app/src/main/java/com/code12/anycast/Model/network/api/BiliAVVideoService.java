package com.code12.anycast.Model.network.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface BiliAVVideoService {
    @GET("playurl")
    Observable<ResponseBody> getBiliAVVideoHtml(@QueryMap Map<String, String> map);
}
