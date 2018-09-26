package com.shine.indoormap.view.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.shine.indoormap.R
import com.shine.indoormap.base.data.SeekList

class SearchAdatper constructor(context: Context, seekList: SeekList) : RecyclerView.Adapter<SearchAdatper.ViewHolder>() {
    var context: Context
    var seekList: SeekList
    var mSearchItmeClick:SearchItmeClick?=null
    init {
        this.context = context
        this.seekList = seekList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_seacrch_layout, null, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return seekList.result_data.size
    }
    fun setItemClick(searchItmeClick: SearchItmeClick){
        mSearchItmeClick=searchItmeClick
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = seekList.result_data.get(position)
        holder.tv_name.setText(data.queue_Name)
        holder.tv_floornumber_tv.setText(data.builDing_Name + data.floor_Name)
        holder.im_nav.setOnClickListener {
            mSearchItmeClick!!.searchItmeClick(data.coordinate_ID.toInt())
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_name = view.findViewById<TextView>(R.id.tv_name)
        var tv_floornumber_tv = view.findViewById<TextView>(R.id.tv_floornumber_tv)
        var im_nav = view.findViewById<RelativeLayout>(R.id.seacrch_layout)
    }
    interface SearchItmeClick {
        fun searchItmeClick(id:Int)
    }
}