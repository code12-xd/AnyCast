package com.code12.playerframework.record;

/**
 * Created by Taurus on 2018/12/12.
 */
public interface PlayValueGetter {

    int getCurrentPosition();

    int getBufferPercentage();

    int getDuration();

    int getState();

}
