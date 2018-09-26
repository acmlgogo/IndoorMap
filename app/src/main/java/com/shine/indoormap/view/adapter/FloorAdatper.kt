package com.shine.indoormap.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import com.shine.indoormap.R
import com.shine.indoormap.base.data.FloorInfo

import com.shine.indoormap.base.data.FloorList


class FloorAdatper constructor(var contexts: Context, var floorList: FloorList) : RecyclerView.Adapter<FloorAdatper.ViewHolder>() {
    var context: Context? = null
    var floorData: FloorList? = null
    var arrayList = ArrayList<FloorList.ResultDataEntity.QueueDataEntity>()
    var mItemClickListener: OnItemClickListener? = null

    init {
        this.context = contexts
        this.floorData = floorList

        floorList.result_data.forEach {
            it.queue_Data.forEach {
                arrayList?.add(it)
            }
        }
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_floordialog, null, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return floorData!!.result_data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resultDataEntity = floorData!!.result_data[position]
        holder.tvNumber.setText(resultDataEntity.floor_Name)
        var name = ""
        for (index in resultDataEntity.queue_Data.indices) {
            if(index==7){
                name +=  resultDataEntity.queue_Data.get(index).queue_Name+ "  \n"
            }else{
                name +=  resultDataEntity.queue_Data.get(index).queue_Name+ "  "
            }

        }

        holder.tvName.setText(name)
        holder.imNavigation.setTag(resultDataEntity.floor_ID)
        holder.relativelayout.setOnClickListener {
            //            FloorInfo()
            mItemClickListener!!.onItemClick(resultDataEntity.floor_ID, resultDataEntity.buildName, resultDataEntity.floor_Name)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var relativelayout: RelativeLayout = view.findViewById(R.id.item_layout)
        var tvNumber: TextView = view.findViewById(R.id.item_floorinfo_tv_floor_number)
        var tvName: TextView = view.findViewById(R.id.item_floorinfo_tv_floor_name)
        var imNavigation: ImageView = view.findViewById(R.id.item_floorinfo_imbutton_navigation)

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, buildNmae: String, floorName: String)
    }

}


