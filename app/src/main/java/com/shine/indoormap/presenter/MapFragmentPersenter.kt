package com.shine.indoormap.presenter

import android.app.Activity
import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.CurrentFloorQueueInfo
import com.shine.indoormap.base.data.WayData
import com.shine.indoormap.base.data.initalize.Initalize
import com.shine.indoormap.model.MapModel
import com.shine.indoormap.view.BaseActivity
import com.shine.indoormap.view.fragment.BaseFragment
import com.shine.indoormap.view.fragment.MapFragment

class MapFragmentPersenter constructor(var activiycontent: BaseActivity, var content: BaseFragment) : BasePersenter() {

    override fun onUiResponse() {

    }

    override fun messageAction(action: Action, message: String) {

        when (action) {
            Action.LOADDATA -> {
                activiycontent.loadDialog(message)
            }
            Action.SHOWERRORMESSAGE -> {
                activiycontent.closeDialog()
                activiycontent.showMessage(message)

            }
            Action.NETWORKANOMALY -> {
                activiycontent.showMessage(message)
            }
            Action.DISMISSDIALOG -> {
                activiycontent.closeDialog()
            }

        }

    }

    override fun initModel() {
        baseModel = MapModel(this)
    }

    override fun onstart() {

    }

    override fun onDestroy() {

    }

    fun loadFloorData(id: String) {
        val mapModel = baseModel as MapModel
        val loadFloorInfo = mapModel.loadFloorInfo(id)
        val arrayList = loadFloorInfo as ArrayList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>
        val mapFragment = content as MapFragment
        mapFragment.initView(arrayList!!)
    }

    fun loadWayType(targetId: String,type:String="Floor") {
        messageAction(Action.LOADDATA, "正在加载数据....")
        val mapModel = baseModel as MapModel
        mapModel.getWayType(targetId,type)
        val mapFragment = content as MapFragment
        mapFragment.mSelectMarkId=targetId
    }

    fun loadWayData(targetId: String,one :String,two:String,type: String="Floor") {
        messageAction(Action.LOADDATA, "正在加载数据....")
        val mapModel = baseModel as MapModel
        mapModel.getWayData(targetId,one, two,type)
    }

    /**
     * 路线选择
     * */
    fun showWayType(data: WayData,type: String) {
        val mapFragment = content as MapFragment
        mapFragment.wayTypeSelect(data,type)
    }
    /**
     *  本楼层路线绘制
     * */
    fun wayCanvs(data: WayData) {
        val mapFragment = content as MapFragment
        mapFragment.canvsWay(data)
    }
    /**
     *  跨楼层线路绘制
     * */
    fun transRegionalWayCanvs(data: WayData){
        val mapFragment = content as MapFragment
        mapFragment.transRegionalWayCanvs(data)
    }
    /**
     *  获取当前楼程科室列表
     * */
    fun loadCurrentDeparment(floorid:String){
        val mapModel = baseModel as MapModel
        mapModel.loadCurrentDepartmentList(floorid)

    }
    /**
     * 当前楼层科室数据返回显示
     */
    fun showCurrentFloorQueue(currentFloorQueueInfo: CurrentFloorQueueInfo){
        val mapFragment = content as MapFragment
        mapFragment.showCurrentDepartment(currentFloorQueueInfo)
    }

}