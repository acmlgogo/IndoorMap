package com.shine.indoormap.presenter

import android.app.Activity
import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.ClassifyingList
import com.shine.indoormap.model.DoctorModel
import com.shine.indoormap.view.BaseActivity
import com.shine.indoormap.view.fragment.BaseFragment

class DoctorFragmentPersenter(var fragment: BaseFragment) : BasePersenter() {
    override fun initModel() {
        baseModel = DoctorModel(this)
    }

    override fun onstart() {
        initModel()
    }

    override fun messageAction(dismissdialog: Action, message: String) {
        when (dismissdialog) {
            Action.DISMISSDIALOG -> {
                val baseActivity = fragment.activity as BaseActivity
                baseActivity.closeDialog()
            }
            Action.LOADDATA->{
                val baseActivity = fragment.activity as BaseActivity
                baseActivity.loadDialog(message)
            }
        }
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUiResponse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 获取科室信息
     * */
    fun getDepartmentInfo() {
        val doctorModel = baseModel as DoctorModel
//        messageAction(Action.LOADDATA,"加载数据中.....")
        doctorModel.getClassifyingList(Constant.ITEMS!!.items_ID)
    }

    fun getDoctorIndoById(classifyingListID: String ,baseFragment: BaseFragment) {
        val doctorModel = baseModel as DoctorModel
        doctorModel.getDoctorData(Constant.ITEMS!!.items_ID, classifyingListID,baseFragment)
    }


}