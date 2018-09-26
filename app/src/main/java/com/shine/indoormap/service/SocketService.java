package com.shine.indoormap.service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import com.orhanobut.logger.Logger;
import com.shine.indoormap.base.data.MaterialInfo;
import com.shine.indoormap.rx.RxBus;
import com.shine.indoormap.ulits.DowdUlits.CallBack;
import com.shine.indoormap.ulits.SpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by Administrator on 2017/10/25.
 */

public class SocketService extends Service {
    private volatile boolean isRunning = true;

    private String DefaultCache;
    private ProgressDialog progressDialog;
    private CallBack callBack;
    private Handler handler;

    private Socket socket;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        handler = new Handler();
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    public class MyBinder extends Binder {
        public SocketService getMyService() {
            return SocketService.this;
        }
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void startSocket() {

        new Thread(new Runnable() {
            private ServerSocket serverSocket;
            @Override
            public void run() {
                try {
                    if (serverSocket == null) {
                        serverSocket = new ServerSocket(9500);
                        serverSocket.setReuseAddress(true);
                    }
                    Logger.e("socket创建");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while (isRunning) {
                    try {
                        socket = serverSocket.accept();
                        Logger.d("接受到socket链接 ");
                        InputStream reader = socket.getInputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(reader));
                        String msg = in.readLine();
                        Logger.d("屏保json数据" + msg);
                        final Gson gson = new Gson();
                        final MaterialInfo materialInfo = gson.fromJson(msg, MaterialInfo.class);
                        RxBus.INSTANCE.post(materialInfo);
                        SpUtil.setString(SocketService.this, SpUtil.SCREEN_JSON,msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }


}
