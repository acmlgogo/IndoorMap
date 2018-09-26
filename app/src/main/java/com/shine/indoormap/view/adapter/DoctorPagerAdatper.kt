package com.shine.indoormap.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.shine.indoormap.base.data.ClassifyingList
import com.shine.indoormap.view.fragment.BaseFragment
import com.shine.indoormap.view.fragment.DoctorerItmeFragment


class DoctorPagerAdatper(var title: ArrayList<String>, var list: ArrayList<Fragment>, var fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return list.get(position)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

    override fun getItemId(position: Int): Long {
        val doctorerItmeFragment = list.get(position) as DoctorerItmeFragment
        return doctorerItmeFragment.getDepartmensId()!!.toLong()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView((`object` as Fragment).view) // 移出viewpager两边之外的page布局
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = getItem(position)
        if (!fragment.isAdded) { // 如果fragment还没有added
            val ft = fragmentManager.beginTransaction()
            ft.add(fragment, fragment.javaClass.simpleName)
            ft.commit()
            fragmentManager.executePendingTransactions()//同步的方式添加Fragment
        }

        if (fragment.view!!.parent == null) {
            container.addView(fragment.view) // 为viewpager增加布局
        }
        return fragment
    }


}