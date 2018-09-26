package com.shine.indoormap.model

import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.FloorList
import com.shine.indoormap.base.data.WayData
import com.shine.indoormap.presenter.Action
import com.shine.indoormap.presenter.BasePersenter
import com.shine.indoormap.presenter.HomeFragmentPersenter
import com.shine.indoormap.presenter.MapFragmentPersenter
import com.shine.indoormap.retrofit.HttpClient
import com.shine.indoormap.retrofit.IndorrMapApi
import com.shine.indoormap.rx.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeModel(private var persenter: BasePersenter) : BaseModel() {

    override fun getPersenter(): BasePersenter {

        return this.persenter
    }

    fun getFloorInfo(id: String) {
        val floorList = HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).floorList(Constant.ITEMS!!.items_ID, Constant.AREA!!.area_ID, id)
        floorList.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onNext = {
                            parserFloorInfo(it)
                        },
                        onComplete = {
                            persenter.messageAction(Action.DISMISSDIALOG)
                        },
                        onError = {

                        }
                )
    }

    /**
     * 获取路线数据
     * */
    fun getWayType(targetId: String) {
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).getWayPath(Constant.TERMINAL!!.floor_Coordinate_ID, targetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(

                        onNext = {
//                            parserWayData(it)
                        },

                        onError = {
                            com.orhanobut.logger.Logger.d("异常问题${it.toString()}")
                            persenter.messageAction(Action.NETWORKANOMALY, "网络异常获取数据失败")
                        },

                        onComplete = {
                            persenter.messageAction(Action.DISMISSDIALOG)
                        }
                )

    }

    private fun parserFloorInfo(floorList: FloorList) {
        RxBus.post(floorList)
    }
    //TODO 思考线路过去方法 是否应该全局化  是否应该放在main里面做
    /**
     *
     *  处理路线数据
     * */
//    fun parserWayData(wayData: WayData) {
//
//        if (wayData.result_code.equals("0")) {
//            if (wayData.result_data.walkMethodOneData.walkData.size > 0 || wayData.result_data.walkMethodTwoData.walkData.size > 0) {
//                //路线导航行走方式选择
//                val mapFragmentPersenter = persenter as HomeFragmentPersenter
//                mapFragmentPersenter.showWayType(wayData)
//            } else {
//                //线路导航
//                val mapFragmentPersenter = persenter as HomeFragmentPersenter
//                mapFragmentPersenter.wayCanvs(wayData)
//            }
//        } else {
//            persenter.messageAction(Action.SHOWERRORMESSAGE, "数据获取失败")
//        }
//
//    }
}