package com.shine.indoormap.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.shine.indoormap.R
import com.shine.indoormap.base.data.CurrentFloorQueueInfo

class CurrentFloorQueueAdapter(var context: Context, var currentFloorQueueInfo: CurrentFloorQueueInfo) : RecyclerView.Adapter<CurrentFloorQueueAdapter.ViewHoler>() {
    var mQueueClick: QueueClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val view = LayoutInflater.from(context).inflate(R.layout.item_current_floor_queue, null, false)
        return ViewHoler(view)
    }

    fun setOnClick(queueClick: QueueClick) {
        mQueueClick = queueClick
    }

    override fun getItemCount(): Int {
        return currentFloorQueueInfo.result_data.size
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val data = currentFloorQueueInfo.result_data.get(position)
        holder.mTvName.setText(data.queue_Name)
        holder.mTvName.setOnClickListener { mQueueClick!!.queueClick(data.coordinate_ID) }
    }

    class ViewHoler(view: View) : RecyclerView.ViewHolder(view) {
        var mTvName = view.findViewById<TextView>(R.id.item_current_floor_queue_tv)
    }

    interface QueueClick {
        fun queueClick(mark: String);
    }
}