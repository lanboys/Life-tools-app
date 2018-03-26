/**
 * Copyright 2015 ZhangQu Li
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bing.lan.comm.api.progress;

/**
 * 进度回调接口，比如用于文件上传与下载
 */
public interface ProgressListener {

    //SD卡---->内存
    void onRequestSingleProgress(int progressId, long currentBytes, long contentLength, boolean done);

    //内存---->网络
    void onRequestProgress(int progressId, long currentBytes, long contentLength, boolean done);

    //网络---->内存
    void onResponseProgress(int progressId, long currentBytes, long contentLength, boolean done);
}