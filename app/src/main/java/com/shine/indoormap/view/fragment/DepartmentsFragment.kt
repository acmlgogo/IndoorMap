package com.shine.indoormap.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.orhanobut.logger.Logger
import com.shine.indoormap.R
import com.shine.indoormap.base.data.ClassifyingList
import kotlinx.android.synthetic.main.departments_adapter_layout.*


class DepartmentsFragment : Fragment() {
    var classResultData = ArrayList<ClassifyingList.ResultDataEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("departments     onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.departments_adapter_layout, null, false)
        initview(view)
        Logger.d("departments     onCreateView")
        return view
    }

    private fun initview(view: View?) {
        for (index in classResultData.indices) {
            if (index > 3) {
                val childAt = departments_adapter_radiogrop.getChildAt(index) as RadioButton
                childAt.setText(classResultData[index].display_Name)
                childAt.setTag(classResultData[index].classifying_ID)
                childAt.visibility = View.VISIBLE
            } else {
                val childAt = departments_adapter_radiogrop2.getChildAt(index) as RadioButton
                childAt.setText(classResultData[index].display_Name)
                childAt.setTag(classResultData[index].classifying_ID)
                childAt.visibility = View.VISIBLE
            }
        }
    }

    fun setDepartments(listdata: ArrayList<ClassifyingList.ResultDataEntity>) {
        this.classResultData = listdata
    }

}