package com.code12.anycast.Model.network.api;

import com.code12.anycast.Model.types.LiveAppIndexInfo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LiveService {
    @GET("AppIndex/home?_device=android&_hwid=51e96f5f2f54d5f9&_ulv=10000&access_key=563d6046f06289cbdcb472601ce5a761&appkey=c1b107428d337928&build=410000&platform=android&scale=xxhdpi&sign=fbdcfe141853f7e2c84c4d401f6a8758")
    Observable<LiveAppIndexInfo> getLiveAppIndex();

    @GET("api/playurl?player=1&quality=0")
    Observable<ResponseBody> getLiveUrl(@Query("cid") String cid, @Query("appkey") String appkey, @Query("ts") String ts, @Query("sign") String sign);

}
