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
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface YouTubeVideoApi {
    @GET ("get_video_info?video_id=FGjvkB7x6eo")
    Observable<ResponseBody> getVideoInfo();//@Query("video_id")String vid);
}
