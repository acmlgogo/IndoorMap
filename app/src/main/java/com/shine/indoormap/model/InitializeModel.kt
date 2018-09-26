package com.shine.indoormap.model


import android.util.Log
import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.initalize.Initalize
import com.shine.indoormap.presenter.Action
import com.shine.indoormap.presenter.BasePersenter
import com.shine.indoormap.retrofit.HttpClient
import com.shine.indoormap.retrofit.IndorrMapApi
import com.shine.indoormap.rx.RxThread
import com.shine.indoormap.ulits.SystemUtlis
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.logging.Logger


class InitializeModel constructor(private val basePersenter: BasePersenter) : BaseModel() {

    override fun getPersenter(): BasePersenter {
        return basePersenter
    }

    /**
     *  @author cf
     *  初始化终端数据
     * */
    fun initialize() {
        if(Constant.ISDEBUG){
        val initalize = HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).initalize("10.0.0.108", "E0-28-17-05-22-45")
        initalize.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            parserInitalizeData(it)
                        },
                        onComplete = {
                            basePersenter.messageAction(Action.DISMISSDIALOG)
                        },
                        onError = {
                            com.orhanobut.logger.Logger.e(it.toString())
                            basePersenter.messageAction(Action.NETWORKANOMALY, "网络异常")
                        }
                )
        }else{
            com.orhanobut.logger.Logger.d("ip:${SystemUtlis.getLocalInetAddress().hostAddress}  mac${SystemUtlis.getLocalMacAddressFromIp()}")
            val initalize = HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).initalize(SystemUtlis.getLocalInetAddress().hostAddress,SystemUtlis.getLocalMacAddressFromIp())

            initalize.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onNext = {
                                parserInitalizeData(it)
                            },
                            onComplete = {
                                basePersenter.messageAction(Action.DISMISSDIALOG)
                            },
                            onError = {
                                com.orhanobut.logger.Logger.e(it.toString())
                                basePersenter.messageAction(Action.NETWORKANOMALY, "网络异常")
                            }
                    )
        }

    }

    /**
     * 解析初始化数据
     * */
    fun parserInitalizeData(initalize: Initalize) {

        if (initalize.result_code.equals("0")) {

            val resultdata = initalize.result_data.get(0)


            Constant.RESULT = initalize.result_data[0]

            Constant.CURRENT = resultdata.current

            Constant.AREA = resultdata.area

            Constant.ITEMS = resultdata.items

            Constant.TERMINAL = resultdata.terminal
            basePersenter.onUiResponse()
        } else {
            basePersenter.messageAction(Action.NETWORKANOMALY, initalize.result_desc)
        }

    }


}