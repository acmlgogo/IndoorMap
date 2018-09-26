package com.shine.indoormap.view.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.orhanobut.logger.Logger
import com.shine.indoormap.R
import com.shine.indoormap.base.Constant

class WayLookBackAdapter constructor(context: Context, imglist: ArrayList<Bitmap>) : RecyclerView.Adapter<WayLookBackAdapter.ViewHolder>() {

    var context: Context
    var imglist: ArrayList<Bitmap>
    var itemClick: ItemClick? = null

    init {
        this.context = context
        this.imglist = imglist

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_way_lookback_layout, null, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return imglist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wayPathData = Constant.WAYDATA!!.result_data.wayPath
        holder.mLookBackIm.setImageBitmap(imglist.get(position))
        val wayPath = wayPathData.get(position)
        var type = ""
        if (Constant.WAYTYPEARRAY.size - 1 >= position) {
            when (Constant.WAYTYPEARRAY.get(position)) {
                3 -> type = "楼梯"
                4 -> type = "自动扶梯"
                5 -> type = "升降电梯"
            }
        }
        holder.mLookBackLayoutI.setOnClickListener {
            itemClick!!.onItemClick(position)
        }
        holder.mTvIndex.setText((position + 1).toString())
        if (position < itemCount - 1) {
            if (!wayPath.builDing_Name.equals("")) {
                holder.mTvOne.setText(Html.fromHtml( "您的所在位子：  <font color='#000000'><big>${wayPath.builDing_Name} ${wayPath.floor_Name}</big></font>"))
                holder.mTvTwo.setText(Html.fromHtml("<font color='#000000'><big>${wayPath.builDing_Name}${wayPath.floor_Name}</big></font>  从  <font color='#000000'><big>${type}</big></font>到<font color='#000000'><big>${wayPathData.get(position + 1).builDing_Name}${wayPathData.get(position + 1).floor_Name}</big></font>"))
                if (wayPathData.get(position + 1).floor_Name.equals("")) {
                    holder.mTvTwo.setText(Html.fromHtml("<font color ='#000000'><big>${wayPath.builDing_Name}${wayPath.floor_Name}</big></font>  从  <font color='#000000'><big>${type}</big></font>  到室外"))
                } else {
                    holder.mTvTwo.setText(Html.fromHtml("<font color='#000000'><big>${wayPath.builDing_Name}${wayPath.floor_Name}</big></font>  从  <font color='#000000'><big>${type}</big></font>  到  <font color='#000000'><big>${wayPathData.get(position + 1).builDing_Name}${wayPathData.get(position + 1).floor_Name}</big></font>"))
                }
            } else {
                holder.mTvOne.setText(Html.fromHtml("您的所在位子：  <font color='#000000'><big>室外</big></font>"))
                holder.mTvTwo.setText(Html.fromHtml("<font color='#000000'><big>${wayPathData.get(position - 1).builDing_Name}</big></font>  步行到  <font color='#000000'><big>${wayPathData.get(position + 1).builDing_Name}</big></font>"))
            }
        } else {
            holder.mTvOne.setText(Html.fromHtml("终点位子：  <font color='#000000'><big>${wayPath.builDing_Name} ${wayPath.floor_Name}</big></font>"))
            holder.mTvTwo.setText("本次导航结束，谢谢使用！")
        }
    }

    fun setItemClickListener(itemClick: ItemClick) {
        this.itemClick = itemClick
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mLookBackLayoutI = view.findViewById<CardView>(R.id.item_way_lookback_layout)
        var mLookBackIm = view.findViewById<ImageView>(R.id.item_way_lookback_im)
        var mTvIndex = view.findViewById<TextView>(R.id.tv_index)
        var mTvOne = view.findViewById<TextView>(R.id.way_tv_1)
        var mTvTwo = view.findViewById<TextView>(R.id.way_tv_2)

    }


    interface ItemClick {
        fun onItemClick(position: Int)
    }
}