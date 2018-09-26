package com.shine.indoormap

import android.app.Application
import com.shine.indoormap.retrofit.HttpClient
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger.addLogAdapter
import com.orhanobut.logger.PrettyFormatStrategy
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import android.util.DisplayMetrics
import com.shine.indoormap.base.Constant
import com.shine.indoormap.engine.SpeechEngine
import com.shine.indoormap.ulits.SystemUtlis


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        HttpClient.init()
        initlog()
        getWindowsInfo()
        SpeechEngine.getInstance().init(this)
    }

    private fun initlog() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否打印线程号,默认true
                .methodCount(5)         // 展示几个方法数,默认2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                //                .logStrategy(customLog) //是否更换打印输出,默认为logcat
                .tag("CH")   // 全局的tag
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        Logger.e("终端IP${SystemUtlis.getLocalMacAddressFromIp()}")

    }

    private fun getWindowsInfo() {
        val dm = resources.displayMetrics
        Constant.HEIGTH = dm.heightPixels
        Constant.WIDTH = dm.widthPixels
    }

    override fun onTerminate() {
        super.onTerminate()
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}