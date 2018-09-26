package com.shine.indoormap.view.dialog

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.View
import android.view.ViewAnimationUtils
import com.shine.indoormap.R
import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.WayData
import com.shine.indoormap.rx.RxBus
import com.shine.indoormap.view.adapter.WaySelectAdapter
import com.shine.indoormap.view.adapter.WaySelectTwoAdapter
import kotlinx.android.synthetic.main.diaglog_way_select_layout.*
import kotlinx.android.synthetic.main.search_layout.*
import java.text.FieldPosition

class WaySelectDialog(context: Context?, var data: WayData) : BaseDialog(context) {
    var mWayOntType = 0
    var mWayTwoType = 0
    var mContext: Context?
    var wayTypeSelect: WaySelectListener? = null
    var twoAdapter: WaySelectTwoAdapter? = null
    var oneAdapter: WaySelectAdapter? = null
    var oneCheck = false
    var twoCheck = false

    init {
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diaglog_way_select_layout)
        initdata()
    }

    fun setWaySelectListener(waySelectListener: WaySelectListener) {
        this.wayTypeSelect = waySelectListener
    }

    override fun initdata() {

        val walkMethodOneData = data.result_data.walkMethodOneData
        val walkMethodTwoData = data.result_data.walkMethodTwoData
        if (walkMethodOneData.walkData.size > 0) {
            one_rg.visibility = View.VISIBLE
            tv_current.visibility = View.VISIBLE
            tv_current.setText(Html.fromHtml("<font color=#35bab0><big>${walkMethodOneData.startPosition}</big></font>     到    <font color=#35bab0><big>${walkMethodOneData.endPosition}</big></font>"))
            walkMethodOneData.walkData.forEach {
                for (i in 0..one_rg.childCount - 1) {
                    if (one_rg.getChildAt(i).tag.equals(it.type.toString())) {
                        one_rg.getChildAt(i).visibility = View.VISIBLE
                        one_rg.getChildAt(i).setOnClickListener {
                            oneCheck = true
                            mWayOntType = it.tag.toString().toInt()
                            if (oneCheck && twoCheck) {
                                //楼梯3  自动扶梯4 电梯5
                                wayTypeSelect!!.wayTypeSelect(mWayOntType, mWayTwoType)
                                dismiss()
                            }
                            if (walkMethodTwoData.walkData.size == 0) {
                                wayTypeSelect!!.wayTypeSelect(mWayOntType, mWayTwoType)
                                dismiss()
                            }
                        }
                    }
                }
            }
        } else {
            tv_current.visibility = View.GONE
            one_rg.visibility = View.GONE
        }
        if (walkMethodTwoData.walkData.size > 0) {
            two_rg.visibility = View.VISIBLE
            tv_target.visibility = View.VISIBLE
            tv_target.setText(Html.fromHtml("<font color=#35bab0><big>${walkMethodTwoData.startPosition}</big></font>    到    <font color=#35bab0><big>${walkMethodTwoData.endPosition}</big></font>"))
            walkMethodTwoData.walkData.forEach {
                for (i in 0..two_rg.childCount - 1) {
                    if (two_rg.getChildAt(i).tag.equals(it.type.toString())) {
                        two_rg.getChildAt(i).visibility = View.VISIBLE
                        two_rg.getChildAt(i).setOnClickListener {
                            twoCheck = true
                            mWayTwoType = it.tag.toString().toInt()
                            if (walkMethodOneData.walkData.size ==0) {
                                if (twoCheck) {
                                    wayTypeSelect!!.wayTypeSelect(mWayOntType, mWayTwoType)
                                }
                            }else{
                                if (oneCheck && twoCheck) {
                                    //楼梯3  自动扶梯4 电梯5
                                    wayTypeSelect!!.wayTypeSelect(mWayOntType, mWayTwoType)

                                    dismiss()
                                }
                            }

                        }
                    }
                }
            }
        } else {
            two_rg.visibility = View.GONE
            tv_target.visibility = View.GONE
        }

    }

    interface WaySelectListener {
        fun wayTypeSelect(one: Int, two: Int)
    }
}