/**
 *  Created by code12, 2020-07-09.
 *  Ali cloud api's param generator.
 */
package com.code12.anycast.Model.network;

import com.code12.anycast.auxilliary.utils.LogUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/*
http://vod.cn-shanghai.aliyuncs.com/?Action=SearchMedia
    &SearchType=video
    &Fields=VideoId,Title,Status,CoverURL,CreationTime,ModificationTime
    &Match=Title='测试' and Status in ('Normal')
    &PageNo=1
    &PageSize=10
    &ScrollToken=3de9654f6169e31d7aa2987244d0d6c7
    &SortBy=CreationTime:Desc
    &Version=2017-03-21
    &Format=JSON
    &Signature=vpEEL0zFHfxXYzSFV0n7%2FZiFL9o%3D
    &SignatureMethod=Hmac-SHA1
    &SignatureNonce=9166ab59-f445-4005-911d-664c1570df0f
    &SignatureVersion=1.0
    &AccessKeyId=LTAI4GDjhNZQgXXfnPp2bpPM
    &Timestamp=2017-03-29T09%3A22%3A32Z
 */
public class AliRequestFactory {
    public static final String TAG = AliRequestFactory.class.getSimpleName();

    public static Map<String ,String> buildMediaListRequestParams() {
        Map<String, String> params = new HashMap<String, String>();
        buildPublicParams(params);
        //=============分割线下的是特有参数==================================
        params.put("Action", "SearchMedia");
//        params.put("PageNo", "1");
//        params.put("PageSize", "10");
        params.put("SearchType", "video");
        params.put("Fields", "VideoId,Title,CoverURL");//"Title,Status,CoverURL,CreationTime,ModificationTime"
//        params.put("Match", "Status in ('Normal')");
//        params.put("ScrollToken", "3de9654f6169e31d7aa2987244d0d6c7");
//        params.put("SortBy", "CreationTime:Desc");
        buildSignature(params);
        return params;
    }

    public static Map<String ,String> buildVideoRequestParams(String vid) {
        Map<String, String> params = new HashMap<String, String>();
        buildPublicParams(params);
        params.put("Action", "GetPlayInfo");
        params.put("VideoId", vid);
        buildSignature(params);
        return params;
    }

    private static void buildPublicParams(Map<String ,String> params) {
        //=============7大公共参数==================================
        params.put("Format", "JSON");
        params.put("Version", "2017-03-21");
        params.put("SignatureMethod", "HMAC-SHA1");
        params.put("SignatureVersion", "1.0");
        params.put("AccessKeyId", AliParamAuxilliary.getMyKey());
        params.put("Timestamp", AliParamAuxilliary.generateTimestamp());
        params.put("SignatureNonce", AliParamAuxilliary.generateRandom());
    }

    private static void buildSignature(Map<String ,String> params) {
        String cqs = AliParamAuxilliary.getCQS(AliParamAuxilliary.getAllParams(params, null));
        String tosign =
                "GET" + "&" + //HTTPMethod：发送请求的 HTTP 方法，例如 GET。
                        AliParamAuxilliary.percentEncode("/") + "&" + //percentEncode("/")：字符（/）UTF-8 编码得到的值，即 %2F。
                        AliParamAuxilliary.percentEncode(cqs); //您的规范化请求字符串。

        LogUtil.test(tosign);
        byte[] code = AliParamAuxilliary.hmacSHA1Signature(AliParamAuxilliary.getMyKeySecret(), tosign);
        try {
            params.put("Signature", AliParamAuxilliary.newStringByBase64(code));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
