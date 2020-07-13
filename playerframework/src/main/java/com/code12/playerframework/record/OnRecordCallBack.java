package com.code12.playerframework.record;

import com.code12.playerframework.entity.DataSource;

/**
 * Created by Taurus on 2018/12/12.
 *
 * if you want to custom save record, you can set it.
 *
 */
public interface OnRecordCallBack {

    int onSaveRecord(DataSource dataSource, int record);

    int onGetRecord(DataSource dataSource);

    int onResetRecord(DataSource dataSource);

    int onRemoveRecord(DataSource dataSource);

    void onClearRecord();

}
