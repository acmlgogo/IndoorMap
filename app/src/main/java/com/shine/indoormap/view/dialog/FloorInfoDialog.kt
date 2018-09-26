package com.shine.indoormap.view.dialog

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.shine.indoormap.R
import com.shine.indoormap.base.data.FloorInfo
import com.shine.indoormap.base.data.FloorList
import com.shine.indoormap.rx.RxBus
import com.shine.indoormap.view.adapter.FloorAdatper
import kotlinx.android.synthetic.main.dialog_floorinfo.*

class FloorInfoDialog(context: Context?) : BaseDialog(context), FloorAdatper.OnItemClickListener {

    var name = ""
    var floorList: FloorList? = null
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_floorinfo)
        initdata()
    }

    fun setFloorData(floorList: FloorList) {
        this.floorList = floorList
    }

    fun setBuildingID(id: String) {
        this.id = id
    }

    fun setTilelName(name: String) {
        this.name = name
    }


    override fun initdata() {
        tv_title.setText(name+"信息展示")
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dialog_floor_recyclerview.layoutManager = linearLayoutManager

        val floorAdatper = FloorAdatper(context, floorList!!)
        floorAdatper.setItemClickListener(this)
        dialog_floor_recyclerview.adapter = floorAdatper


    }

    override fun onItemClick(position: Int,builderName:String,floorName:String) {
        dismiss()
        RxBus.post( FloorInfo(id, position.toString(),builderName,floorName))

    }


}