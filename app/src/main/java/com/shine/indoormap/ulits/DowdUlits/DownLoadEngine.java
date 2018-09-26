package com.shine.indoormap.ulits.DowdUlits;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/24.
 */

public class DownLoadEngine {
    private Builder builder;
    private String url;
    private String cache;
    private String fileName;

    static {
        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).build();
    }

    private static OkHttpClient mOkHttpClient;
    private final Handler handler;

    public DownLoadEngine(Builder builder) {
        handler = new Handler(Looper.getMainLooper());
        this.url = builder.url;
        this.cache = builder.cache;
        this.fileName = builder.fileName;
    }

    public void execute(final CallBack callBack) {
        Request request = new Request.Builder().url(this.url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final long contentLength = response.body().contentLength();

                InputStream inputStream = response.body().byteStream();

                FileOutputStream fileOutputStream = null;
                try {
                    File file = new File(cache + "//" + fileName);
                    file.setExecutable(true, false);
                    file.setWritable(true, false);
                    file.setReadable(true, false);
                    fileOutputStream = new FileOutputStream(file);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onStart(contentLength);
                        }
                    });
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                        final int finalLen = len;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onProgress(finalLen);
                            }
                        });
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailure();
                        }
                    });

                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onComplete();
                    }
                });

            }
        });


    }


    public static class Builder {
        private String url;
        private String cache;
        private String fileName;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder cache(String cache) {
            this.cache = cache;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }


        public DownLoadEngine Build() {
            return new DownLoadEngine(this);
        }
    }


}
