/**
 *  Created by code12, 2020-07-09.
 *  Access ali cloud normal video list.
 */
package com.code12.anycast.Model.network.api;

import com.code12.anycast.Model.types.NormalVListInfo;
import com.code12.anycast.Model.types.NormalVideoInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NormalVideoApi {
    @GET ("/")
    Observable<NormalVListInfo> getMediaList(@QueryMap Map<String ,String> map);

    @GET ("/")
    Observable<NormalVideoInfo> getVideoInfo(@QueryMap Map<String ,String> map);
}
