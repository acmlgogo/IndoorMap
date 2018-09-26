package com.shine.indoormap.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import com.shine.indoormap.R
import com.shine.indoormap.base.data.Doctor

class DoctorAdapter constructor(context: Context, doctor: Doctor) : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {
    var context: Context
    var doctor: Doctor
    var mDoctorNavigation: DoctorNavigation? = null

    init {
        this.context = context
        this.doctor = doctor
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctorInfo = doctor.result_data.get(position)
        Glide.with(context).load(doctorInfo.image_URL).into(holder.doctorImage)
        holder.doctorName.setText(doctorInfo.doctor_Name)
        holder.doctorJobTitle.setText(doctorInfo.title)
        holder.doctorDepartments.setText(doctorInfo.queue_Name)
        val split = doctorInfo.visit_Time.split(",")

        var data: String? = ""
        for (index in split.indices) {
            data += "${split[index]}  "
            if (index == 2||index==5) {
                data += "\n"
            }
        }

        holder.doctorDate.setText(data)

        holder.doctorDegoodat.setText("    "+doctorInfo.speciality)
        if (doctorInfo.whether_Visit.equals("0")) {
            holder.doctor_nav_but.background = context.resources.getDrawable(R.drawable.doctor_out_call_selector)
        } else {
            holder.doctor_nav_but.background = context.resources.getDrawable(R.drawable.doctor_no_out_call_selector)
            holder.doctor_nav_but.isEnabled = false
        }

        holder.doctor_nav_but.setOnClickListener {
            mDoctorNavigation!!.startNavigation(doctorInfo.coordinate_ID)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_doctor_layout, null)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        if (doctor.result_data == null) {
            return 0
        }
        return doctor.result_data.size
    }

    fun setDoctorNavigation(doctorNavigation: DoctorNavigation) {
        mDoctorNavigation = doctorNavigation
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var doctor_nav_but = view.findViewById<Button>(R.id.doctor_nav_but)
        var doctorImage = view.findViewById<ImageView>(R.id.item_doctor_imageview)
        var doctorName = view.findViewById<TextView>(R.id.item_doctor_name_tv)
        var doctorJobTitle = view.findViewById<TextView>(R.id.item_doctor_jobtitle_tv)
        var doctorDepartments = view.findViewById<TextView>(R.id.item_doctor_departments)
        var doctorDate = view.findViewById<TextView>(R.id.item_doctor_tv_date)
        var doctorDegoodat = view.findViewById<TextView>(R.id.item_doctor_begoodat_content_tv)
    }

    interface DoctorNavigation {
        fun startNavigation(markId: String)
    }

}