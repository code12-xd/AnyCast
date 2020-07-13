package com.code12.playerframework.record;

import com.code12.playerframework.entity.DataSource;

/**
 * Created by Taurus on 2018/12/12.
 */
public interface RecordKeyProvider {

    String generatorKey(DataSource dataSource);

}
