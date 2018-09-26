package com.shine.indoormap.ulits.DowdUlits;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface CallBack {
    void taskNumber(int count);

    //开始下载
    void onStart(long totalLength);

    // 处于是否暂停状态
    boolean isStop(boolean isStop);

    //失败
    void onFailure();

    // 下载完成
    void onComplete();

    // 进度
    void onProgress(int prgress);

}
