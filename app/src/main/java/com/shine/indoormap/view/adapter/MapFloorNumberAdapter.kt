package com.shine.indoormap.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import com.shine.indoormap.R
import com.shine.indoormap.base.data.initalize.Initalize
import java.util.*
import java.util.logging.Logger

class MapFloorNumberAdapter constructor(var context: Context, var floordata: LinkedList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>) : RecyclerView.Adapter<MapFloorNumberAdapter.ViewHolder>() {
    var id: String? = null
    open fun setFlooriID(id: String) {
        this.id = id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_map_floornumber, null, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return floordata.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (floordata.get(position).floor_ID.equals(id)) {
            holder.number.isChecked = true
        }
        holder.number.setText(floordata.get(position).floor_Name)
        holder.number.setOnClickListener {
            setFlooriID(floordata.get(position).floor_ID)
            notifyDataSetChanged()
            com.orhanobut.logger.Logger.d("floordata.get(position).floor_ID  :${floordata.get(position).floor_ID}")

        }
    }

    class ViewHolder constructor(var view: View) : RecyclerView.ViewHolder(view) {
        var number = view.findViewById<RadioButton>(R.id.item_map_floornumber_check)
    }

}