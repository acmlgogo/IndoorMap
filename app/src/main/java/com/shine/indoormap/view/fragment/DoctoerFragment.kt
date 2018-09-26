package com.shine.indoormap.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shine.indoormap.R
import com.shine.indoormap.base.data.ClassifyingList
import com.shine.indoormap.base.data.Doctor
import com.shine.indoormap.presenter.DoctorFragmentPersenter
import com.shine.indoormap.rx.RxBus
import com.shine.indoormap.view.adapter.DoctorPagerAdatper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_doctor_layout.*
import android.support.v4.app.FragmentManager;
import com.orhanobut.logger.Logger
import com.shine.indoormap.view.MainActivity


class DoctoerFragment : BaseFragment() {
    var mDoctorFragmentPersenter: DoctorFragmentPersenter? = null
    var doctor = Doctor()
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_doctor_layout, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        initPersenter()
        fragment_doctor_tablayout.setupWithViewPager(fragment_doctor_viewpager)
        mCompositeDisposable.add(
                RxBus.toFlowable(ClassifyingList::class.java)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    initTablayout(it)
                                },
                                onComplete = {

                                },
                                onError = {

                                }
                        )
        )
        val mainActivity = activity as MainActivity
        mainActivity.hideCommunalFacilitiesButton()
    }

    private fun initPersenter() {
        mDoctorFragmentPersenter = DoctorFragmentPersenter(this)
        mDoctorFragmentPersenter!!.onstart()
        mDoctorFragmentPersenter!!.getDepartmentInfo()
    }
    @SuppressLint("ResourceType")
    fun initTablayout(classifyingList: ClassifyingList) {
        Logger.e("doctoreFragmenttauylayout")
        fragment_doctor_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE)
        var list = ArrayList<Fragment>()
        var titleList=ArrayList<String>()
        classifyingList.result_data.forEach {
            var doctorerItmeFragment = DoctorerItmeFragment()
            doctorerItmeFragment.setpersenter(mDoctorFragmentPersenter!!)
            doctorerItmeFragment.setDepartmentsId(it.classifying_ID)
            list.add(doctorerItmeFragment)
            titleList.add(it.display_Name)
        }
        fragment_doctor_viewpager.adapter = DoctorPagerAdatper(titleList,list, activity!!.supportFragmentManager)

    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }

}