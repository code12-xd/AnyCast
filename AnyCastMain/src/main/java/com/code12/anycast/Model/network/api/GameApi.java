/**
 *  Created by code12, 2020-07-07.
 *  Access douyu game list.
 */
package com.code12.anycast.Model.network.api;

import com.code12.anycast.Model.types.GameInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GameApi
{
    @GET("api/RoomApi/live?offset=20&limit=20")
    Observable<GameInfo> getGameInfo();
}
