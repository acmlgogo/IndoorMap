package com.shine.indoormap.view.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.shine.indoormap.view.MainActivity


class DepartmentsInfoAdapter(var context: Context, var list: ArrayList<View>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        list.get(position).setTag(position)


        container.addView(list.get(position))
        return list.get(position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        return container.removeView(list.get(position))

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return (position + 1).toString()
    }


}