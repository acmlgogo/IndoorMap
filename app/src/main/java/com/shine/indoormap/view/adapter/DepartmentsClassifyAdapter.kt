package com.shine.indoormap.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.shine.indoormap.R
import com.shine.indoormap.base.data.QueueList

class DepartmentsClassifyAdapter(var context: Context, var queueList: QueueList) : RecyclerView.Adapter<DepartmentsClassifyAdapter.ViewHolder>() {

    var mDepartmentItmeClick: DepartmentItmeClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_departments_classify_layout, null, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return queueList.result_data.size
    }

    fun setClickListener(departmentItmeClick: DepartmentItmeClick) {
        mDepartmentItmeClick = departmentItmeClick
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = queueList.result_data.get(position)
        holder.tvName.setText(data.queue_Name)
        holder.tvLoctaion.setText("( ${data.builDing_Name}${data.floor_Name}  )")
        holder.layout.setOnClickListener {
            mDepartmentItmeClick!!.departmentItemClick(data.coordinate_ID)
        }
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvName = view.findViewById<TextView>(R.id.item_departments_classify_tv_name)
        var tvLoctaion = view.findViewById<TextView>(R.id.item_departments_classify_tv_loaction)
        var layout = view.findViewById<RelativeLayout>(R.id.item_departments_classify_layout)
    }

    interface DepartmentItmeClick {
        fun departmentItemClick(targetID: String)
    }
}