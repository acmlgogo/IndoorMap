package com.shine.indoormap.engine;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.util.LinkedList;

public class SpeechEngine {
    private static SpeechEngine instance;
    private static String TAG = "milan";
    private Context mContext;
    private MySynthesizerListener mSynthesizerListener;
    private SpeechSynthesizer mSynthesizer;
    private LinkedList<String> mQueue;
    private String speed;

    private SpeechEngine() {

    }

    public static SpeechEngine getInstance() {
        if (instance == null) {
            instance = new SpeechEngine();
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        mQueue = new LinkedList<>();
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=557e3b39");
        MyInitListener initListener = new MyInitListener();
        mSynthesizerListener = new MySynthesizerListener();
        mSynthesizer = SpeechSynthesizer.createSynthesizer(mContext, initListener);
    }

    private void setParmas(SpeechSynthesizer synthesizer, String speed) {
        // 清空参数
        synthesizer.setParameter(SpeechConstant.PARAMS, null);
        synthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        // 设置本地合成发音人 voicer为空，默认通过语音+界面指定发音人。
        synthesizer.setParameter(SpeechConstant.VOICE_NAME, "");
        //设置合成音调
        synthesizer.setParameter(SpeechConstant.PITCH, "40");
        //设置合成语速
        synthesizer.setParameter(SpeechConstant.SPEED, speed);
        //设置合成音量
        synthesizer.setParameter(SpeechConstant.VOLUME, "100");
        //设置播放器音频流类型
        synthesizer.setParameter(SpeechConstant.STREAM_TYPE, "3");
    }


    private void speech(String content) {
        setParmas(mSynthesizer, speed);
        mSynthesizer.startSpeaking(content, mSynthesizerListener);
    }

    public void add(String content) {
        add(content, "50");
    }

    public void add(String content, String speed) {
        this.speed = speed;
        mQueue.add(content);
        if (mQueue.size() == 1) {
            speech(content);
        }
    }

    public void stop() {
        mSynthesizer.destroy();
    }


    private class MySynthesizerListener implements SynthesizerListener {


        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            Log.e(TAG, "onCompleted");
            mQueue.removeFirst();
            if (mQueue.size() > 0) {
                String content = mQueue.getFirst();
                speech(content);
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    }

    private class MyInitListener implements InitListener {

        @Override
        public void onInit(int i) {

        }
    }


}
