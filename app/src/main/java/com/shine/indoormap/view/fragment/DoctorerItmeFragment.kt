package com.shine.indoormap.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shine.indoormap.R
import com.shine.indoormap.base.data.Doctor
import com.shine.indoormap.presenter.BasePersenter
import com.shine.indoormap.presenter.DoctorFragmentPersenter
import com.shine.indoormap.rx.RxBus
import com.shine.indoormap.view.GridItemDecoration
import com.shine.indoormap.view.MainActivity
import com.shine.indoormap.view.adapter.DoctorAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_doctoer_item_layout.*
import kotlinx.android.synthetic.main.fragment_doctor_layout.*

class DoctorerItmeFragment : BaseFragment() {
    var mPersenter: DoctorFragmentPersenter? = null
    var mDepartmentsid: String? = null
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_doctoer_item_layout, null, false)
    }

    override fun onStart() {
        super.onStart()
        var layoutManager = GridLayoutManager(activity, 4)
        fragment_doctor_recyclerivew.addItemDecoration(GridItemDecoration(4, 20, true))
        fragment_doctor_recyclerivew.setHasFixedSize(true)
        fragment_doctor_recyclerivew.layoutManager = layoutManager
        mPersenter!!.getDoctorIndoById(mDepartmentsid!!, this)

    }

    fun setpersenter(persenter: DoctorFragmentPersenter) {
        mPersenter = persenter
    }

    fun setDepartmentsId(id: String) {
        mDepartmentsid = id
    }

    fun getDepartmensId(): String? {
        return mDepartmentsid
    }

    fun initViewpager(doctor: Doctor) {
        val doctorAdapter = DoctorAdapter(activity!!, doctor)
        doctorAdapter.setDoctorNavigation(object : DoctorAdapter.DoctorNavigation {
            override fun startNavigation(markId: String) {
                val mainActivity = activity as MainActivity
                mainActivity.doctorNavigation(markId)
            }
        })
        fragment_doctor_recyclerivew.adapter = doctorAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }
}