package com.code12.anycast.Model.Assets;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import com.code12.anycast.AcApplication;
import com.code12.anycast.auxilliary.utils.CommonUtil;
import com.code12.anycast.auxilliary.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssetsLoader {
    private String[] uris;
    private DataListener mListener;

    public AssetsLoader(DataListener l) {
        mListener = l;
    }

    public interface DataListener {
        void onDataReady(final List<SampleGroup> groups);
    }

    public void readFromAsset() {
        init();
        loadSample();
    }

    private void init() {
        ArrayList<String> uriList = new ArrayList<>();
        AssetManager assetManager = AcApplication.getInstance().getAssets();
        try {
            for (String asset : assetManager.list("")) {
                if (asset.endsWith(".exolist.json")) {
                    uriList.add(asset);//"asset:///" +
                }
            }
        } catch (IOException e) {
            LogUtil.d("Load assets error!");
        }
        uris = new String[uriList.size()];
        uriList.toArray(uris);
        Arrays.sort(uris);
    }

    private void loadSample() {
        SampleListLoader loaderTask = new SampleListLoader();
        loaderTask.execute(uris);
    }

    private void onSampleGroups(final List<SampleGroup> groups, boolean sawError) {
        if (sawError) {
            LogUtil.d("Read from assets error!");
        }

        if (mListener != null)
            mListener.onDataReady(groups);
    }

    private final class SampleListLoader extends AsyncTask<String, Void, List<SampleGroup>> {
        private boolean sawError;

        @Override
        protected List<SampleGroup> doInBackground(String... uris) {
            List<SampleGroup> result = new ArrayList<>();
            Context context = AcApplication.getInstance();
            for (String uri : uris) {
                InputStream inputStream = null;
                try {
                    inputStream = AcApplication.getInstance().getResources().getAssets().open(uri);
                    readSampleGroups(new JsonReader(new InputStreamReader(inputStream, "UTF-8")), result);
                } catch (Exception e) {
                    LogUtil.e("Error loading sample list: " + uri, e.getMessage());
                    sawError = true;
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<SampleGroup> result) {
            onSampleGroups(result, sawError);
        }

        private void readSampleGroups(JsonReader reader, List<SampleGroup> groups) throws IOException {
            reader.beginArray();
            while (reader.hasNext()) {
                readSampleGroup(reader, groups);
            }
            reader.endArray();
        }

        private void readSampleGroup(JsonReader reader, List<SampleGroup> groups) throws IOException {
            String groupName = "";
            ArrayList<Sample> samples = new ArrayList<>();

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "name":
                        groupName = reader.nextString();
                        break;
                    case "samples":
                        reader.beginArray();
                        while (reader.hasNext()) {
                            samples.add(readEntry(reader, false));
                        }
                        reader.endArray();
                        break;
                    case "_comment":
                        reader.nextString(); // Ignore.
                        break;
                    default:
                        break;
                }
            }
            reader.endObject();

            SampleGroup group = getGroup(groupName, groups);
            group.samples.addAll(samples);
        }

        private Sample readEntry(JsonReader reader, boolean insidePlaylist) throws IOException {
            String sampleName = null;
            Uri uri = null;
            String extension = null;
            boolean isLive = false;
            String drmScheme = null;
            String drmLicenseUrl = null;
            String[] drmKeyRequestProperties = null;
            boolean drmMultiSession = false;
            ArrayList<Sample.UriSample> playlistSamples = null;
            String adTagUri = null;
            String sphericalStereoMode = null;
            List<Sample.SubtitleInfo> subtitleInfos = new ArrayList<>();
            Uri subtitleUri = null;
            String subtitleMimeType = null;
            String subtitleLanguage = null;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "name":
                        sampleName = reader.nextString();
                        break;
                    case "uri":
                        uri = Uri.parse(reader.nextString());
                        break;
                    case "extension":
                        extension = reader.nextString();
                        break;
                    case "drm_scheme":
                        drmScheme = reader.nextString();
                        break;
                    case "is_live":
                        isLive = reader.nextBoolean();
                        break;
                    case "drm_license_url":
                        drmLicenseUrl = reader.nextString();
                        break;
                    case "drm_key_request_properties":
                        ArrayList<String> drmKeyRequestPropertiesList = new ArrayList<>();
                        reader.beginObject();
                        while (reader.hasNext()) {
                            drmKeyRequestPropertiesList.add(reader.nextName());
                            drmKeyRequestPropertiesList.add(reader.nextString());
                        }
                        reader.endObject();
                        drmKeyRequestProperties = drmKeyRequestPropertiesList.toArray(new String[0]);
                        break;
                    case "drm_multi_session":
                        drmMultiSession = reader.nextBoolean();
                        break;
                    case "playlist":
                        playlistSamples = new ArrayList<>();
                        reader.beginArray();
                        while (reader.hasNext()) {
                            playlistSamples.add((Sample.UriSample) readEntry(reader, /* insidePlaylist= */ true));
                        }
                        reader.endArray();
                        break;
                    case "ad_tag_uri":
                        adTagUri = reader.nextString();
                        break;
                    case "spherical_stereo_mode":
                        sphericalStereoMode = reader.nextString();
                        break;
                    case "subtitle_uri":
                        subtitleUri = Uri.parse(reader.nextString());
                        break;
                    case "subtitle_mime_type":
                        subtitleMimeType = reader.nextString();
                        break;
                    case "subtitle_language":
                        subtitleLanguage = reader.nextString();
                        break;
                    default:
                        break;
                }
            }
            reader.endObject();

            Sample.SubtitleInfo subtitleInfo =
                    subtitleUri == null
                            ? null
                            : new Sample.SubtitleInfo(
                            subtitleUri,
                            subtitleMimeType,
                            subtitleLanguage);
            if (playlistSamples != null) {
                Sample.UriSample[] playlistSamplesArray = playlistSamples.toArray(new Sample.UriSample[0]);
                return new Sample.PlaylistSample(sampleName, playlistSamplesArray);
            } else {
                return new Sample.UriSample(
                        sampleName,
                        uri,
                        extension,
                        isLive,
                        null,
                        adTagUri != null ? Uri.parse(adTagUri) : null,
                        sphericalStereoMode,
                        subtitleInfo);
            }
        }

        private SampleGroup getGroup(String groupName, List<SampleGroup> groups) {
            for (int i = 0; i < groups.size(); i++) {
                if (CommonUtil.areEqual(groupName, groups.get(i).title)) {
                    return groups.get(i);
                }
            }
            SampleGroup group = new SampleGroup(groupName);
            groups.add(group);
            return group;
        }
    }

    public static final class SampleGroup {

        public final String title;
        public final List<Sample> samples;

        public SampleGroup(String title) {
            this.title = title;
            this.samples = new ArrayList<>();
        }

    }
}
