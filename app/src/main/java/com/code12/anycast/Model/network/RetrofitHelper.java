/**
 *  Created by code12, 2020-07-05.
 *  retrofit helper
 */
package com.code12.anycast.Model.network;

import com.code12.anycast.AcApplication;
import com.code12.anycast.Model.network.api.BiliAVVideoService;
import com.code12.anycast.Model.network.api.BiliAppService;
import com.code12.anycast.Model.network.api.GameApi;
import com.code12.anycast.Model.network.api.LiveService;
import com.code12.anycast.auxilliary.utils.CommonUtil;
import com.code12.anycast.auxilliary.utils.ConstantUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    public static OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    public static BiliAVVideoService getBiliAVVideoAPI() {
        return createApi(BiliAVVideoService.class,ConstantUtil.VIDEO_INTERFACE_URL);
    }

    public static LiveService getLiveAPI() {
        return createApi(LiveService.class, ConstantUtil.LIVE_BASE_URL);
    }

    public static GameApi getGameAPI() {
        return createApi(GameApi.class, ConstantUtil.GAME_BASE_URL);
    }

    public static BiliAppService getBiliAppAPI() {
        return createApi(BiliAppService.class, ConstantUtil.APP_BASE_URL);
    }

    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }

    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    Cache cache = new Cache(new File(AcApplication.getInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                          //  .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }

    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            int maxAge = 60 * 60;
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();

            if (CommonUtil.isNetworkAvailable(AcApplication.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(AcApplication.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}
