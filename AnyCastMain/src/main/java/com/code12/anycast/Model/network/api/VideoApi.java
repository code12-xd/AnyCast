/**
 *  Created by code12, 2020-07-08.
 */
package com.code12.anycast.Model.network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface VideoApi {
    @GET ("/")
    Observable<ResponseBody> getVideoUrlContent();
}
