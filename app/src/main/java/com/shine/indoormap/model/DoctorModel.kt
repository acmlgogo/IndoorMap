package com.shine.indoormap.model

import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.ClassifyingList
import com.shine.indoormap.base.data.Doctor
import com.shine.indoormap.presenter.Action
import com.shine.indoormap.presenter.BasePersenter
import com.shine.indoormap.retrofit.HttpClient
import com.shine.indoormap.retrofit.IndorrMapApi
import com.shine.indoormap.rx.RxBus
import com.shine.indoormap.view.fragment.BaseFragment
import com.shine.indoormap.view.fragment.DoctorerItmeFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DoctorModel(basePersenter: BasePersenter) : BaseModel() {
    var basePersenter: BasePersenter

    init {
        this.basePersenter = basePersenter
    }

    override fun getPersenter(): BasePersenter {
        return basePersenter
    }

    /**
     *  获取分类信息
     * */
    fun getClassifyingList(itemId: String) {
        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).classifyingList(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            parserClassifyingList(it)
                        },
                        onError = {
//                            basePersenter.messageAction(Action.DISMISSDIALOG)
                        },
                        onComplete = {
//                            basePersenter.messageAction(Action.NETWORKANOMALY)
                        }
                )
    }

    /**
     * 获取分类下医生数据
     * */
    fun getDoctorData(itemId: String, classifyingId: String,baseFragment: BaseFragment) {

        HttpClient.createRetrofit(IndorrMapApi::class.java, Constant.BASE_URL).doctorList(itemId, classifyingId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            parserDoctorData(it,baseFragment)
                        },
                        onComplete = {
//                            basePersenter.messageAction(Action.DISMISSDIALOG)
                        },
                        onError = {
//                            basePersenter.messageAction(Action.NETWORKANOMALY)
                        }
                )
    }


    fun parserClassifyingList(classifyingList: ClassifyingList) {
        RxBus.post(classifyingList)
    }

    fun parserDoctorData(doctor: Doctor,baseFragment: BaseFragment) {
        val doctorerItmeFragment = baseFragment as DoctorerItmeFragment
        doctorerItmeFragment.initViewpager(doctor)
    }

}