package com.shine.indoormap.view.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.shine.indoormap.R
import com.shine.indoormap.base.data.WayData

import com.shine.indoormap.ulits.GlideUtils.loadWayTypeImage

class WaySelectAdapter(content: Context?, var data: WayData.ResultDataEntity.WalkMethodOneDataEntity?, var type: TypeSelect) : RecyclerView.Adapter<WaySelectAdapter.ViewHolder>() {
    var mContext: Context? = null
    var mPosition: Int? = null

    init {
        mContext = content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_way_select_layout, null, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        try {
            return data!!.walkData.size
        } catch (e: RuntimeException) {
            return 0
        }
    }

    fun setClickItem(position: Int) {
        mPosition = position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTypeTv.setText(data!!.walkData.get(position).name)
        if(mPosition==position) holder.mLayout.setBackgroundColor(mContext!!.resources.getColor(R.color.colorAccent))else holder.mLayout.setBackgroundColor(mContext!!.resources.getColor(R.color.whiteColor))
        holder.mLayout.setOnClickListener {
            notifyDataSetChanged()
            type.typeSelect(data!!.walkData.get(position).type,position)

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mLayout = view.findViewById<RelativeLayout>(R.id.way_select_layout)
        var mTypeIm = view.findViewById<ImageView>(R.id.type_im)
        var mTypeTv = view.findViewById<TextView>(R.id.type_tv)

    }

    interface TypeSelect {
        fun typeSelect(type: Int,position: Int)
    }
}