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
 *  Created by code12, 2020-07-15.
 */
package com.code12.ijkwrapper;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import tv.danmaku.ijk.media.player.misc.IMediaDataSource;

public class RawDataSourceProvider implements IMediaDataSource {

    private AssetFileDescriptor mDescriptor;

    private byte[]  mMediaBytes;

    private RawDataSourceProvider(AssetFileDescriptor descriptor) {
        this.mDescriptor = descriptor;
    }

    @Override
    public int readAt(long position, byte[] buffer, int offset, int size) {
        if(position + 1 >= mMediaBytes.length){
            return -1;
        }

        int length;
        if(position + size < mMediaBytes.length){
            length = size;
        }else{
            length = (int) (mMediaBytes.length - position);
            if(length > buffer.length)
                length = buffer.length ;

            length--;
        }
        System.arraycopy(mMediaBytes, (int) position, buffer, offset, length);

        return length;
    }

    @Override
    public long getSize() throws IOException {
        long length  = mDescriptor.getLength();
        if(mMediaBytes == null){
            InputStream inputStream = mDescriptor.createInputStream();
            mMediaBytes = readBytes(inputStream);
        }


        return length;
    }

    @Override
    public void close() throws IOException {
        if(mDescriptor != null)
            mDescriptor.close();

        mDescriptor = null;
        mMediaBytes = null;
    }

    private byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }

    public static RawDataSourceProvider create(Context context, Uri uri){
        try {
            AssetFileDescriptor fileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
            return new RawDataSourceProvider(fileDescriptor);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
