package com.shine.indoormap.model

import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.Facilities
import com.shine.indoormap.base.data.initalize.Initalize
import com.shine.indoormap.presenter.Action
import com.shine.indoormap.presenter.BasePersenter
import com.shine.indoormap.presenter.MainActivityPersenter
import com.shine.indoormap.retrofit.HttpClient
import com.shine.indoormap.retrofit.IndorrMapApi
import com.shine.indoormap.rx.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainModel constructor(private var persenter: BasePersenter) : BaseModel() {

    override fun getPersenter(): BasePersenter {
        return this.persenter
    }

    /**
     *  快速查询
     * */
    fun loadSearch(seek_condition: String = "") {
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).seekList(Constant.ITEMS!!.items_ID, Constant.AREA!!.area_ID, seek_condition)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            val mainActivityPersenter = persenter as MainActivityPersenter
                            mainActivityPersenter.searchResultShow(it)
                        },
                        onError = {

                        },
                        onComplete = {

                        }
                )

    }

    fun loadDepartmentsData() {
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).classifyingList(Constant.ITEMS!!.items_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            val mainActivityPersenter = persenter as MainActivityPersenter
                            mainActivityPersenter.departmentInfoShow(it)
                        },
                        onComplete = {

                        },
                        onError = {

                        }
                )
    }

    fun loadDepartmentsParticulars(id: String) {
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).classifyingQueueList(Constant.ITEMS!!.items_ID, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            val mainActivityPersenter = persenter as MainActivityPersenter
                            mainActivityPersenter.departmentsClassifyShow(it)
                        },
                        onComplete = {

                        },
                        onError = {

                        }
                )
    }

    /**
     * 加载公共设置信息
     * */
    fun loadCommonalityFacilityData(type: String, id: String, coordinatetype: String) {
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).getFacilitiesInfo(type, id, coordinatetype)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            parserFacilityData(it)
                        },
                        onComplete = {

                        },
                        onError = {

                        }
                )
    }

    fun parserFacilityData(facilities: Facilities) {
        Constant.FACILITIES = facilities
        if (facilities.result_code.equals("0")) {
            RxBus.post(facilities)
        }else{
            persenter.messageAction(Action.SHOWERRORMESSAGE,facilities.result_desc)
        }

    }

    /**
     * 初始化公共设施信息
     * */
    fun initCommonalityFacilityData(): List<Initalize.resultDataEntity.FacilitiesDataEntity>? {
        return Constant.RESULT!!.facilitiesData
    }


}