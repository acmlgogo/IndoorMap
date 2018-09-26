package com.shine.indoormap.presenter

import com.shine.indoormap.base.data.ClassifyingList
import com.shine.indoormap.base.data.QueueList
import com.shine.indoormap.base.data.SeekList
import com.shine.indoormap.model.MainModel
import com.shine.indoormap.model.MapModel
import com.shine.indoormap.view.BaseActivity
import com.shine.indoormap.view.MainActivity
import com.shine.indoormap.view.fragment.MapFragment

class MainActivityPersenter constructor(var view: BaseActivity) : BasePersenter() {

    init {
        onstart()
    }

    override fun messageAction(dismissdialog: Action, message: String) {
        when (dismissdialog) {
            Action.DISMISSDIALOG -> view.closeDialog()
            Action.SHOWERRORMESSAGE -> {
                view.closeDialog()
                view.showMessage(message)
            }
            Action.NETWORKANOMALY -> {
                view.closeDialog()
                view.networkaBnormalDialog(message)
            }
        }
    }


    override fun initModel() {
        baseModel = MainModel(this)
    }

    override fun onstart() {
        initModel()
    }

    override fun onDestroy() {


    }

    override fun onUiResponse() {
    }

    /**
     *  快速搜索
     * */
    fun loadsearch(seek_condition: String) {
        val mainModel = baseModel as MainModel
        mainModel.loadSearch(seek_condition)
    }

    /**
     * 公共设施
     * */


    fun searchResultShow(seekList: SeekList) {
        val mainActivity = view as MainActivity
        mainActivity.loadSearchData(seekList)
    }

    /**
     * 科室查询
     * */
    fun loadDepartmentsData() {
        val mainModel = baseModel as MainModel
        mainModel.loadDepartmentsData()
    }
    /**
     * 科室分类查询
     * */
    fun loadDepartmentsClassify(id: String) {
        val mainModel = baseModel as MainModel
        mainModel.loadDepartmentsParticulars(id)
    }

    fun initCommonalityFacilityData() {
        val mapModel = baseModel as MainModel
        val loadFloorInfo = mapModel.initCommonalityFacilityData()
        val mainActivity = view as MainActivity
        mainActivity.createCommonalityFacility(loadFloorInfo!!)
    }

    fun loadConmmonalityFacility(type: String, id: String, coordinatetype: String) {
        val mapModel = baseModel as MainModel
        mapModel.loadCommonalityFacilityData(type, id, coordinatetype)
    }

    fun departmentInfoShow(classifyingList: ClassifyingList) {
        val mainActivity = view as MainActivity
        mainActivity.departmentsInfoShow(classifyingList)
    }

    fun departmentsClassifyShow(queueList: QueueList) {
        val mainActivity = view as MainActivity
        mainActivity.departmentsClassify(queueList)
    }


}