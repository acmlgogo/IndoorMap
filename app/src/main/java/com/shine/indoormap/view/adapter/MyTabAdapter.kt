package com.shine.indoormap.view.adapter

import android.content.Context
import android.graphics.Color
import com.shine.indoormap.base.data.initalize.Initalize
import com.shine.indoormap.view.customView.leochuan.tabindicator.QTabView
import com.shine.indoormap.view.customView.leochuan.tabindicator.TabAdapter
import java.util.*
import kotlin.collections.ArrayList

class MyTabAdapter(var context: Context, var list: ArrayList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>) : TabAdapter {


    override fun getCount(): Int {
        return list.size
    }

    override fun getBadge(position: Int): Int {
        return 0
    }

    override fun getIcon(position: Int): QTabView.TabIcon? {
        return null
    }

    override fun getTitle(position: Int): QTabView.TabTitle {
        return QTabView.TabTitle.Builder(context)
                .setContent(list[position].floor_Name)
                .setTextColor(Color.BLUE, Color.BLACK)
                .build()
    }

    override fun getBackground(position: Int): Int {
        return 0
    }
}
