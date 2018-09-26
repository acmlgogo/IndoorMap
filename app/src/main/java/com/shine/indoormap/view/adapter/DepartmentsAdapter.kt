package com.shine.indoormap.view.adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.shine.indoormap.base.data.ClassifyingList
import com.shine.indoormap.view.fragment.DepartmentsFragment

class DepartmentsAdapter(fm: FragmentManager?, classifyingList: ClassifyingList) : FragmentPagerAdapter(fm) {
    var itemCount = 0
    var map = HashMap<Int, ArrayList<ClassifyingList.ResultDataEntity>>()

    init {
        var index = 0
        var arrayList = ArrayList<ClassifyingList.ResultDataEntity>()
        itemCount = classifyingList.result_data.size / 8
        if (itemCount == 0) {
            arrayList.addAll(classifyingList.result_data)
            map.put(itemCount, arrayList)
        } else {
//            var mo = classifyingList.result_data.size % 8
//            for (i in 1..itemCount - 1) {
//                if (i == itemCount - 1) {
//                    arrayList = classifyingList.result_data.subList(index, classifyingList.result_data.size - 1) as ArrayList<ClassifyingList.ResultDataEntity>
//                } else {
//                    arrayList = classifyingList.result_data.subList(index, index + 7) as ArrayList<ClassifyingList.ResultDataEntity>
//                    index += 7
//                }
//                map.put(i, arrayList)
//            }
        }


    }

    override fun getItem(position: Int): Fragment? {
        val departmentsFragment = DepartmentsFragment()
        departmentsFragment.setDepartments(map.getValue(position))
        return departmentsFragment
    }

    override fun getCount(): Int {
        return map.size
    }
}