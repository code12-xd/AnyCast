/**
 *  Created by code12, 2020-07-10.
 *  Access ali cloud normal video's info.
 */
package com.code12.anycast.Model.types;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//{"VideoBase":
//        {"Status":"Normal",
//        "VideoId":"dc785d00a0fb4edebeec50520c4068bc",
//        "TranscodeMode":"NoTranscode",
//        "CreationTime":"2020-07-10T01:18:54Z",
//        "Title":"VID_20200709_133243.mp4",
//        "MediaType":"video",
//        "CoverURL":"http://outin-d0f241d8c24911ea8caf00163e1c35d5.oss-cn-shanghai.aliyuncs.com/dc785d00a0fb4edebeec50520c4068bc/snapshots/96065d99a7c54bfeb0851359e7386e11-00001.jpg?Expires=1594354107&OSSAccessKeyId=LTAIVVfYx6D0HeL2&Signature=97IdjNytwhAjWp1cYJQD9aK1w0c%3D",
//        "Duration":"6.4",
//        "OutputType":"oss"},
//  "RequestId":"5DDF1F74-D695-4890-B899-5EA9C6ADE750",
//  "PlayInfoList":
//        {"PlayInfo":
//            [{"Status":"Normal",
//            "StreamType":"video",
//            "Size":14074678,
//            "Definition":"OD",
//            "Fps":"30",
//            "Duration":"6.4",
//            "ModificationTime":"2020-07-10T01:18:54Z",
//            "Specification":"Original",
//            "Bitrate":"17593.35",
//            "Encrypt":0,
//            "PreprocessStatus":"UnPreprocess",
//            "Format":"mp4",
//            "PlayURL":"https://outin-d0f241d8c24911ea8caf00163e1c35d5.oss-cn-shanghai.aliyuncs.com/sv/10b0d854-173364dcb95/10b0d854-173364dcb95.mp4?Expires=1594354107&OSSAccessKeyId=LTAIVVfYx6D0HeL2&Signature=VWsux%2F0QSLApx2V1RiLGuVOAW2g%3D",
//            "NarrowBandType":"0",
//            "CreationTime":"2020-07-10T01:18:54Z",
//            "Height":1080,
//            "Width":1920,
//            "JobId":"dc785d00a0fb4edebeec50520c4068bc02"}]}}
public class NormalVideoInfo {
    @SerializedName("VideoBase")
    private Object base;
    @SerializedName("RequestId")
    private String rid;
    @SerializedName("PlayInfoList")
    private PlayContainerBean container;

    public List<PlayInfoBean> getResult() {
        return container.playinfo;
    }

//  "PlayInfoList":
//        {"PlayInfo":
//            [{"Status":"Normal",
    public static class PlayContainerBean {
        @SerializedName("PlayInfo")
        public List<PlayInfoBean> playinfo;
    }

//  "PlayInfoList":
//        {"PlayInfo":
//            [{"Status":"Normal",
//            "StreamType":"video",
//            "Size":14074678,
//            "Definition":"OD",
//            "Fps":"30",
//            "Duration":"6.4",
//            "ModificationTime":"2020-07-10T01:18:54Z",
//            "Specification":"Original",
//            "Bitrate":"17593.35",
//            "Encrypt":0,
//            "PreprocessStatus":"UnPreprocess",
//            "Format":"mp4",
//            "PlayURL":"https://outin-d0f241d8c24911ea8caf00163e1c35d5.oss-cn-shanghai.aliyuncs.com/sv/10b0d854-173364dcb95/10b0d854-173364dcb95.mp4?Expires=1594354107&OSSAccessKeyId=LTAIVVfYx6D0HeL2&Signature=VWsux%2F0QSLApx2V1RiLGuVOAW2g%3D",
//            "NarrowBandType":"0",
//            "CreationTime":"2020-07-10T01:18:54Z",
//            "Height":1080,
//            "Width":1920,
//            "JobId":"dc785d00a0fb4edebeec50520c4068bc02"}]}}
    public static class PlayInfoBean {
        @SerializedName("PlayURL")
        private String url;
        @SerializedName("Format")
        private String type;
        @SerializedName("JobId")
        private String jobeid;
        @SerializedName("Size")
        private int size;

        public String getVideoUrl() {
            return url;
        }
        public String getJobId() { return jobeid; }
    }
}
