package com.code12.playerframework.record;

import com.code12.playerframework.source.MediaSource;

public interface RecordKeyProvider {

    String generatorKey(MediaSource dataSource);

}
