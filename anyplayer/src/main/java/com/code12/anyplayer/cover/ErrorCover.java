/*
 * Copyright (C) 2020 code12
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *  Created by code12, 2020-07-14.
 *  Error controller.
 */
package com.code12.anyplayer.cover;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.code12.anyplayer.R;
import com.code12.anyplayer.auxiliary.DataInter;
import com.code12.playerframework.config.PConst;
import com.code12.playerframework.event.BundlePool;
import com.code12.playerframework.event.EventKey;
import com.code12.playerframework.event.OnPlayerEventListener;
import com.code12.playerframework.receiver.BaseCover;
import com.code12.playerframework.utils.NetworkUtils;

public class ErrorCover extends BaseCover {

    final int STATUS_ERROR = -1;
    final int STATUS_UNDEFINE = 0;
    final int STATUS_MOBILE = 1;
    final int STATUS_NETWORK_ERROR = 2;

    int mStatus = STATUS_UNDEFINE;

    TextView mInfo;
    TextView mRetry;

    private boolean mErrorShow;
    private int mCurrPosition;

    public ErrorCover(Context context) {
        super(context);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
        mInfo = getView().findViewById(R.id.tv_error_info);
        mRetry = getView().findViewById(R.id.tv_retry);
        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleStatus();
            }
        });
    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        handleStatusUI(NetworkUtils.getNetworkState(getContext()));
    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();
    }

    private void handleStatus(){
        Bundle bundle = BundlePool.obtain();
        bundle.putInt(EventKey.INT_DATA, mCurrPosition);
        switch (mStatus){
            case STATUS_ERROR:
                setErrorState(false);
                requestRetry(bundle);
                break;
            case STATUS_MOBILE:
                //TODO:?? App.ignoreMobile = true;
                setErrorState(false);
                requestResume(bundle);
                break;
            case STATUS_NETWORK_ERROR:
                setErrorState(false);
                requestRetry(bundle);
                break;
        }
    }

    @Override
    public void onProducerData(String key, Object data) {
        super.onProducerData(key, data);
        if(DataInter.Key.KEY_NETWORK_STATE.equals(key)){
            int networkState = (int) data;
            if(networkState== PConst.NETWORK_STATE_WIFI && mErrorShow){
                Bundle bundle = BundlePool.obtain();
                bundle.putInt(EventKey.INT_DATA, mCurrPosition);
                requestRetry(bundle);
            }
            handleStatusUI(networkState);
        }
    }

    private void handleStatusUI(int networkState) {
        if(!getGroupValue().getBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, true))
            return;
        if(networkState < 0){
            mStatus = STATUS_NETWORK_ERROR;
            setErrorInfo("无网络！");
            setHandleInfo("重试");
            setErrorState(true);
        }else{
            if(networkState== PConst.NETWORK_STATE_WIFI){
                if(mErrorShow){
                    setErrorState(false);
                }
            }else{
                //TODO: ??
//                if(App.ignoreMobile)
//                    return;
                mStatus = STATUS_MOBILE;
                setErrorInfo("您正在使用移动网络！");
                setHandleInfo("继续");
                setErrorState(true);
            }
        }
    }

    private void setErrorInfo(String text){
        mInfo.setText(text);
    }

    private void setHandleInfo(String text){
        mRetry.setText(text);
    }

    private void setErrorState(boolean state){
        mErrorShow = state;
        setCoverVisibility(state?View.VISIBLE:View.GONE);
        if(!state){
            mStatus = STATUS_UNDEFINE;
        }else{
            notifyReceiverEvent(DataInter.Event.EVENT_CODE_ERROR_SHOW, null);
        }
        getGroupValue().putBoolean(DataInter.Key.KEY_ERROR_SHOW, state);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode){
            case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
                mCurrPosition = 0;
                handleStatusUI(NetworkUtils.getNetworkState(getContext()));
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_TIMER_UPDATE:
                mCurrPosition = bundle.getInt(EventKey.INT_ARG1);
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        mStatus = STATUS_ERROR;
        if(!mErrorShow){
            setErrorInfo("出错了！");
            setHandleInfo("重试");
            setErrorState(true);
        }
    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_error_cover, null);
    }

    @Override
    public int getCoverLevel() {
        return levelHigh(0);
    }
}
