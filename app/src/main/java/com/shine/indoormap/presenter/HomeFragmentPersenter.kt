package com.shine.indoormap.presenter

import android.app.Fragment
import android.view.View
import com.shine.indoormap.model.HomeModel
import com.shine.indoormap.view.BaseActivity
import com.shine.indoormap.view.fragment.BaseFragment
import com.shine.indoormap.view.fragment.HomeFragment

class HomeFragmentPersenter constructor(var view: BaseFragment) : BasePersenter() {

    override fun initModel() {
        baseModel = HomeModel(this)
    }
    override fun onstart() {
    }

    override fun messageAction(dismissdialog: Action, message: String) {
        when (dismissdialog) {
            Action.LOADDATA -> {
                val baseActivity = view.activity as BaseActivity
                baseActivity.loadDialog(message)
            }
            Action.DISMISSDIALOG->{
                val baseActivity = view.activity as BaseActivity
                baseActivity.closeDialog()
            }
        }

    }

    override fun onDestroy() {

    }

    override fun onUiResponse() {

    }

    fun getfloorData(id: String) {
        val homeModel = baseModel as HomeModel
        homeModel.getFloorInfo(id)
        messageAction(Action.LOADDATA, "正在加载楼程数据")
    }



}