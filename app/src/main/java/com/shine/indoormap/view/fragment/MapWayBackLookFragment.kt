package com.shine.indoormap.view.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shine.indoormap.R
import com.shine.indoormap.base.Constant
import com.shine.indoormap.view.adapter.DepartmentsClassifyAdapter
import com.shine.indoormap.view.adapter.WayLookBackAdapter
import kotlinx.android.synthetic.main.departments_layout.*
import kotlinx.android.synthetic.main.fragment_backlook_layout.*
import android.view.animation.DecelerateInterpolator
import android.animation.AnimatorSet
import android.app.Activity
import android.widget.ImageView
import com.shine.indoormap.view.MainActivity
import com.shine.indoormap.view.adapter.WayBackAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition


class MapWayBackLookFragment : BaseFragment(), WayLookBackAdapter.ItemClick {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_backlook_layout, null, false)
    }

    override fun onStart() {
        super.onStart()
        val mainActivity = activity as MainActivity
        mainActivity.fabactionbutton.visibility = View.GONE
        mainActivity.main_backlook_actionbutton.visibility = View.GONE
        fragment_backlook_recyclerview.layoutManager = GridLayoutManager(activity, 5, GridLayoutManager.VERTICAL, false)
        val wayLookBackAdapter = WayLookBackAdapter(activity!!, Constant.LOOKBACKIMG)
        wayLookBackAdapter.setItemClickListener(this)
        fragment_backlook_recyclerview.adapter = wayLookBackAdapter
        fragment_wayback_viewpager.adapter = WayBackAdapter(activity!!, Constant.LOOKBACKIMG)
        fragment_wayback_tab.setViewPager(fragment_wayback_viewpager,Constant.LOOKBACKIMG.size)
        fragment_framelayout_exit.x = 50f
        fragment_framelayout_exit.y = 50f
        fragment_framelayout_exit.setOnClickListener {
            fragment_map_way_framelayout.visibility = View.GONE
            fragment_wayback_viewpager.visibility = View.GONE
            fragment_wayback_tab.visibility=View.GONE
        }

    }

    override fun onItemClick(position: Int) {
        showMap(position)
        fragment_wayback_tab.visibility=View.VISIBLE
    }

    fun showMap(position: Int) {
        if (fragment_map_way_framelayout.visibility == View.GONE) {
            fragment_map_way_framelayout.visibility = View.VISIBLE

        }
        if (fragment_wayback_viewpager.visibility == View.GONE) {
            fragment_wayback_viewpager.visibility = View.VISIBLE
            fragment_wayback_viewpager.currentItem = position
        }
    }

}