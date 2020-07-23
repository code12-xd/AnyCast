package com.code12.anycast.Model.types;

import com.code12.anycast.Model.Assets.AssetsLoader;
import com.code12.anycast.Model.Assets.Sample;

import java.util.ArrayList;
import java.util.List;

public class SampleInfo {
    private List<ResultBean> result;

    public static SampleInfo buildFrom(List<AssetsLoader.SampleGroup> groups) {
        SampleInfo self = new SampleInfo();
        for (AssetsLoader.SampleGroup group:groups) {
            for (Sample sample:group.samples) {
                ResultBean bean = new ResultBean();
                bean.name = sample.name;
                bean.url = sample.getUrl();
                if (bean.url != null)
                    self.result.add(bean);
            }
        }
        return self;
    }

    public List<ResultBean> getResult() { return result; }

    private SampleInfo() {
        result = new ArrayList<>();
    }

    public static class ResultBean {
        private String name;
        private String url;

        public String getName() { return name; }
        public String getUrl() { return url; }
    }
}
