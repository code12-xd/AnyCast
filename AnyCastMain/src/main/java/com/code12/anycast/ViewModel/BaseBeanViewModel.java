/**
 *  Created by code12, 2020-07-08.
 */
package com.code12.anycast.ViewModel;

import android.app.Application;

import com.code12.anycast.auxilliary.utils.LogUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;

public class BaseBeanViewModel extends BaseViewModel {
    public BaseBeanViewModel(@NonNull Application application){
        super(application);
    }
    public void parseUrl(String url) {}
    public void parseUrlById(String id) {}

    protected String parseVideoUrl(ResponseBody responseBody) {
        String xml = null;
        try {
            xml = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.d("xml", xml);
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        assert document != null;
        Element rootElement = document.getRootElement();
        Element durlElement = rootElement.element("durl");
        Element urlElement = durlElement.element("url");
        String url = urlElement.getText();
        return url;
    }
}