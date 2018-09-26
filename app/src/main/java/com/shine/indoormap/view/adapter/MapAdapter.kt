package com.shine.indoormap.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.shine.indoormap.R
import com.shine.indoormap.base.data.initalize.Initalize
import com.shine.indoormap.rx.RxBus

class MapAdapter constructor(var context: Context, var floorData: ArrayList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>) : RecyclerView.Adapter<MapAdapter.ViewHolder>() {
    lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_map_layout, parent, false)

        return ViewHolder(view)
    }

    fun getdata(): ArrayList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity> {
        return floorData
    }

    override fun getItemCount(): Int {
        return floorData.size
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataEntity = floorData.get(position)

        holder.floorNumberTv.setText(dataEntity.floor_Name)
        Glide.with(context).load(dataEntity.floor_Imager_URL).into(holder.mapImage)
        holder.mapImage.setOnClickListener { itemClickListener.itemClick(dataEntity) }
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var floorNumberTv = view.findViewById<TextView>(R.id.item_map_tv)
        var mapImage = view.findViewById<ImageView>(R.id.item_map_image)
    }

    abstract class ItemClickListener {
        abstract fun itemClick(data: Initalize.resultDataEntity.BuilDingEntity.FloorEntity)
    }
}