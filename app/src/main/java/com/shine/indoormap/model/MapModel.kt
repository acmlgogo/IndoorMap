package com.shine.indoormap.model

import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.CurrentLocation
import com.shine.indoormap.base.data.*
import com.shine.indoormap.base.data.initalize.Initalize
import com.shine.indoormap.presenter.Action
import com.shine.indoormap.presenter.BasePersenter
import com.shine.indoormap.presenter.MapFragmentPersenter
import com.shine.indoormap.retrofit.HttpClient
import com.shine.indoormap.retrofit.IndorrMapApi
import com.shine.indoormap.rx.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.logging.Logger

class MapModel(basePersenter: BasePersenter) : BaseModel() {
    var basePersenter: BasePersenter

    init {
        this.basePersenter = basePersenter
    }

    override fun getPersenter(): BasePersenter {
        return basePersenter
    }

    fun loadFloorInfo(id: String): List<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>? {
        Constant.RESULT!!.builDing.forEach {
            if (it.builDing_ID.equals(id)) {
                return it.floor
            }
        }
        return null
    }

    fun loadCurrentDepartmentList(floorId: String) {
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).getCurrentFloorQueueList(floorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(

                        onNext = {
                            parserQueueInfo(it)
                        },
                        onError = {

                        },
                        onComplete = {

                        }

                )

    }

    /**
     * 获取路线数据
     * */
    fun getWayType(targetId: String,type: String="Floor") {
        Constant.LOOKBACKIMG.clear()
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).getWayPath(Constant.TERMINAL!!.floor_Coordinate_ID, targetId,type=type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            parserWayData(it,type)
                        },
                        onError = {
                            com.orhanobut.logger.Logger.d("异常问题${it.toString()}")
                            basePersenter.messageAction(Action.DISMISSDIALOG)
                            basePersenter.messageAction(Action.NETWORKANOMALY, "网络异常获取数据失败")
                        },
                        onComplete = {
                            basePersenter.messageAction(Action.DISMISSDIALOG)
                        }
                )

    }

    /**
     * 获取路线数据
     * */
    fun getWayData(targetId: String, oneType: String) {
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).getWayPath(Constant.TERMINAL!!.floor_Coordinate_ID, targetId, oneType)
    }

    /**
     * 获取路线数据
     * */
    fun getWayData(targetId: String, oneType: String, twoType: String,type: String="Floor") {
        Constant.LOOKBACKIMG.clear()
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).getWayPath(Constant.TERMINAL!!.floor_Coordinate_ID, targetId, oneType, twoType,type=type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            if (it.result_code.equals("0")) {
                                val mapFragmentPersenter = basePersenter as MapFragmentPersenter
                                mapFragmentPersenter.messageAction(Action.DISMISSDIALOG)
                                mapFragmentPersenter.transRegionalWayCanvs(it)
                            } else {
                                val mapFragmentPersenter = basePersenter as MapFragmentPersenter
                                mapFragmentPersenter.messageAction(Action.SHOWERRORMESSAGE, it.result_desc)
                            }
                        },
                        onComplete = {

                        },
                        onError = {
                        }
                )
    }

    /**
     * 处理路线数据
     * */
    fun parserWayData(wayData: WayData,type: String) {
        if (wayData.result_code.equals("0")) {
            if (wayData.result_data.walkMethodOneData.walkData.size > 0 || wayData.result_data.walkMethodTwoData.walkData.size > 0) {
                //路线导航行走方式选择
                val mapFragmentPersenter = basePersenter as MapFragmentPersenter
                mapFragmentPersenter.showWayType(wayData,type)
            } else {
                //线路导航
                val mapFragmentPersenter = basePersenter as MapFragmentPersenter
                if (wayData.result_data.wayData.size > 0) {
                    mapFragmentPersenter.transRegionalWayCanvs(wayData)
                }else{
                    mapFragmentPersenter.wayCanvs(wayData)
                }

            }
        } else {
            basePersenter.messageAction(Action.SHOWERRORMESSAGE, "${wayData.result_desc}")

        }

    }

    /**
     * 处理当前楼层科室列表数据
     * */
    fun parserQueueInfo(currentFloorQueueInfo: CurrentFloorQueueInfo) {
        val mapFragmentPersenter = basePersenter as MapFragmentPersenter
        mapFragmentPersenter.showCurrentFloorQueue(currentFloorQueueInfo)
    }
}