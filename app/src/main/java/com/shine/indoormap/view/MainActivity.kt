package com.shine.indoormap.view

import android.animation.ObjectAnimator
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.view.ViewPager
import com.shine.indoormap.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.*
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import com.orhanobut.logger.Logger
import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.CurrentLocation
import com.shine.indoormap.base.Event.NavigationEvent
import com.shine.indoormap.base.Event.QueueShowEvent
import com.shine.indoormap.base.data.*
import com.shine.indoormap.base.data.initalize.Initalize
import com.shine.indoormap.presenter.ActionButtonState
import com.shine.indoormap.presenter.MainActivityPersenter
import com.shine.indoormap.rx.RxBus
import com.shine.indoormap.service.SocketService
import com.shine.indoormap.ulits.ScreensaverUlits
import com.shine.indoormap.ulits.SpUtil
import com.shine.indoormap.ulits.SystemUtlis
import com.shine.indoormap.view.`interface`.MainActivityInterface
import com.shine.indoormap.view.adapter.*
import com.shine.indoormap.view.customView.leochuan.actionButton.FloatingActionButton
import com.shine.indoormap.view.fragment.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.departments_layout.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.search_layout.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), MainActivityInterface, SearchAdatper.SearchItmeClick {
    var mScreensaverUlits = ScreensaverUlits()
    var mainPersenter: MainActivityPersenter? = null
    val homeFragment = HomeFragment()
    val mapFragment = MapFragment()
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    var mInputContent = LinkedList<String>()
    var position = 0
    var type = ""
    var isStartNavigation = false
    var mSocketConnetino: SocketConnetino? = null
    var mSubscribe: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val resolution = Constant.TERMINAL!!.resolution
        if (resolution.equals("0") || resolution.equals("2")) {
            //横屏
            setContentView(R.layout.activity_main)
        } else {
            //竖屏
            setContentView(R.layout.activity_main_vertical)
        }
        mainPersenter = MainActivityPersenter(this)
        replaceFragment(homeFragment, R.id.frameLayout)
        mCompositeDisposable.add(RxBus.toFlowable(FloorInfo::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            var bundle = Bundle()
                            bundle.putString("BUILDING_ID", it.building_id)
                            bundle.putString("FLOOR_ID", it.floor_id)
                            bundle.putString("LOCATION_NAME", it.building_name + it.floor_name)
                            bundle.putString("BUILDNAME", it.building_name)
                            mapFragment.arguments = bundle
                            replaceFragment(mapFragment, R.id.frameLayout)
                        },
                        onComplete = {

                        },
                        onError = {

                        }
                ))
        tv_date.setOnLongClickListener {
            Toast.makeText(this, "当前版本号：${SystemUtlis.getVerName(this)}", Toast.LENGTH_LONG).show()
            return@setOnLongClickListener false
        }

        initCommunalFacilities()
        initService()
        screensaver()
        handlerNavigationEvent()
    }

    fun initService() {
        val intent = Intent()
        val dowdloadservice = intent.setClass(this, SocketService::class.java!!)
        mSocketConnetino = SocketConnetino()
        bindService(dowdloadservice, mSocketConnetino, Context.BIND_AUTO_CREATE)
        mCompositeDisposable.add(RxBus.toFlowable(MaterialInfo::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            if (it.requestType.equals("1")) {
                                closeScreensaver()
                            } else {
                                screensaver()
                            }


                        }
                )
        )
    }

    /**
     *  处理导航事件逻辑
     * */
    fun handlerNavigationEvent() {
        mCompositeDisposable.add(RxBus.toFlowable(NavigationEvent::class.java).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            isStartNavigation = it.isstartNavigation
                        }
                ))
    }

    inner class SocketConnetino : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as SocketService.MyBinder
            val myService = myBinder.getMyService()
            myService.startSocket()
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }


    }

    override fun initView() {
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        seacrch_hot_recycler.layoutManager = layoutManager
        fragment_home_im_doctoer.setOnClickListener { loadDoctorInfo() }
        fragment_home_im_keshi.setOnClickListener { loadDepartmentInfo() }
        fragment_home_im_louyu.setOnClickListener { overallNavigation() }
        fragment_home_im_search.setOnClickListener { startSearch() }
        main_actionbutton.setOnClickListener { loadCurrentLoaciton() }
        main_backlook_actionbutton.setOnClickListener {
            wayBitmapLookBack()
        }
        fabactionbutton.setOnMenuToggleListener {
            if (!it) {
                RxBus.post(ActionButtonState(it))
            }
        }
    }

    override fun showMessage(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("信息提示")
        builder.setMessage(message)
        builder.show()
    }

    override fun networkaBnormalDialog(message: String) {
    }


    override fun loadWeatherInfo() {

    }

    /**
     * 楼宇导航按键事件
     * */
    override fun overallNavigation() {
        fabactionbutton.visibility = View.VISIBLE
        fabactionbutton.close(false)
        activity_main_radiogroup.clearCheck()
        replaceFragment(homeFragment, R.id.frameLayout)
    }

    /**
     * 科室导航按键事件
     * */
    override fun loadDepartmentInfo() {
        fabactionbutton.close(false)
        activity_main_radiogroup.clearCheck()
        hideSearchWindow()
        if (activity_main_departments_layout.visibility == View.VISIBLE) {
            activity_main_departments_layout.visibility = View.GONE
            activity_main_centre_relativelayout.visibility = View.GONE
        } else {
            activity_main_departments_layout.visibility = View.VISIBLE
            mainPersenter!!.loadDepartmentsData()
            val translationY = activity_main_departments_relativelayout.translationY
            val animator = ObjectAnimator.ofFloat(activity_main_departments_relativelayout, "translationY", Constant.HEIGTH!!.toFloat(), 0f, 0f)
            animator.setDuration(1000)
            animator.start()
        }
        activity_main_departments_layout.setOnClickListener { hideDepartmentsWindow() }
        RxBus.post(QueueShowEvent(false))
    }

    /**
     * 医生介绍按键事件
     * */
    override fun loadDoctorInfo() {
        fabactionbutton.visibility = View.GONE
        fabactionbutton.close(false)
        activity_main_radiogroup.clearCheck()
        hideWindow()
        var doctoerFragment = DoctoerFragment()
        replaceFragment(doctoerFragment, R.id.frameLayout)
    }

    /**
     * 快速查询按键事件
     * */
    override fun startSearch() {
        fabactionbutton.close(false)
        activity_main_radiogroup.clearCheck()
        hideDepartmentsWindow()
        mInputContent.clear()
        if (search_layout_main.visibility == View.GONE) {
            onSearch()
            search_layout_main.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(search_button_layout, "translationY", Constant.HEIGTH!!.toFloat(), 0f, 0f)
            animator.setDuration(1000);
            animator.start();
            val animator1 = ObjectAnimator.ofFloat(search_top_layout, "alpha", 0f, 1f)
            animator1.setDuration(1000);
            animator1.start();
        } else {
            search_layout_main.visibility = View.GONE
        }
        seacrch_layout.setOnClickListener { hideSearchWindow() }
        RxBus.post(QueueShowEvent(false))
    }

    /**
     * 显示终端当前位子
     * */
    override fun loadCurrentLoaciton() {
        fabactionbutton.visibility = View.VISIBLE
        if (mapFragment.isAdded) {
            mapFragment.FLOOR_ID = Constant.CURRENT!!.floor_ID
            mapFragment.BUILDING_ID = Constant.CURRENT!!.builDing_ID
            mapFragment.BUILDNAME = Constant.CURRENT!!.builDing_Name
            mapFragment.LOCATION_NAME = Constant.CURRENT!!.builDing_Name + Constant.CURRENT!!.floor_Name
            mapFragment.showLocation()
        } else {
            var bundle = Bundle()
            bundle.putString("BUILDING_ID", Constant.CURRENT!!.builDing_ID)
            bundle.putString("FLOOR_ID", Constant.CURRENT!!.floor_ID)
            bundle.putString("LOCATION_NAME", Constant.CURRENT!!.builDing_Name + Constant.CURRENT!!.floor_Name)
            bundle.putString("BUILDNAME", Constant.CURRENT!!.builDing_Name)
            mapFragment.arguments = bundle
            mapFragment.setPersentListener(null)
            replaceFragment(mapFragment, R.id.frameLayout)

        }

    }

    /**
     *  初始化公共设施
     * */
    override fun initCommunalFacilities() {
        mainPersenter!!.initCommonalityFacilityData()
    }

    override fun showFloorInfo(id: String) {

    }

    /**
     * 创建科室信息展示布局规划
     * */
    fun departmentsInfoShow(classifyingList: ClassifyingList) {
        var list = ArrayList<View>()
        val size = classifyingList.result_data.size
        var count = size / 8
        if (count == 0) {
            val arrayList = classifyingList.result_data as ArrayList<ClassifyingList.ResultDataEntity>
            val createdepartemtsLayout = createdepartemtsLayout(arrayList)
            list.add(createdepartemtsLayout)
        } else {
            val arrayList = classifyingList.result_data as ArrayList<ClassifyingList.ResultDataEntity>
            var index = 0
            for (i in 1..count) {
                val subList = arrayList.subList(index, index + 8)
                index += 8
                val createdepartemtsLayout = createdepartemtsLayout(subList)
                list.add(createdepartemtsLayout)
            }

            if (size - count * 8 > 0) {
                val arrayList1 = arrayList.subList(index, size)
                val createdepartemtsLayout = createdepartemtsLayout(arrayList1)
                list.add(createdepartemtsLayout)
            }
        }
        activity_main_departments_viewpager.adapter = DepartmentsInfoAdapter(this, list)
        activity_main_departments_tablayout.setViewPager(activity_main_departments_viewpager)
        activity_main_departments_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                list.forEach {
                    it.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop).clearCheck()
                    it.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop2).clearCheck()
                }
            }
        })

    }

    /**
     * 创建科室信息展示
     * */
    fun createdepartemtsLayout(list: MutableList<ClassifyingList.ResultDataEntity>): View {
        val view = layoutInflater.inflate(R.layout.departments_adapter_layout, null)
        val radioGroup1 = view.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop)
        val radioGroup2 = view.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop2)
        if (list.size / 4 == 0) {
            for (i in 0..list.size % 4 - 1) {
                val radioButton = radioGroup1.getChildAt(i) as RadioButton
                radioButton.visibility = View.VISIBLE
                radioButton.setText(list.get(i).display_Name)
                radioButton.tag = list.get(i).classifying_ID
                radioButton.setOnClickListener {
                    mainPersenter!!.loadDepartmentsClassify(it.tag.toString())
                }
            }
        } else {
            for (i in 0..3) {
                radioGroup1.getChildAt(i)
                val radioButton = radioGroup1.getChildAt(i) as RadioButton
                radioButton.visibility = View.VISIBLE
                radioButton.setText(list.get(i).display_Name)
                radioButton.tag = list.get(i).classifying_ID
                radioButton.setOnClickListener {
                    radioGroup2.clearCheck()
                    mainPersenter!!.loadDepartmentsClassify(it.tag.toString())
                }
            }
            if (list.size - 4 > 0) {
                for (i in 4..list.size - 1) {
                    val radioButton = radioGroup2.getChildAt(i - 4) as RadioButton
                    radioButton.visibility = View.VISIBLE
                    radioButton.setText(list.get(i).display_Name)
                    radioButton.tag = list.get(i).classifying_ID
                    radioButton.setOnClickListener {
                        radioGroup1.clearCheck()
                        mainPersenter!!.loadDepartmentsClassify(it.tag.toString())
                    }
                }
            }
        }
        return view

    }

    /**
     * 排版科室队列信息展示
     * */
    fun createdepartemtsQueueLayout(list: MutableList<QueueList.ResultDataEntity>): View {
        val view = layoutInflater.inflate(R.layout.departments_adapter_layout, null)
        val radioGroup1 = view.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop)
        val radioGroup2 = view.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop2)
        if (list.size / 4 == 0) {
            for (i in 0..list.size % 4 - 1) {
                val radioButton = radioGroup1.getChildAt(i) as RadioButton
                radioButton.visibility = View.VISIBLE
               var locationName= list.get(i).queue_Name
                if(locationName.toCharArray().size<7){
                    radioButton.setText(Html.fromHtml(list.get(i).queue_Name + "<br />" + "<small>(" + list.get(i).builDing_Name + list.get(i).floor_Name + ")</small>"))
                }else{
                    radioButton.setText(Html.fromHtml("<small>"+list.get(i).queue_Name + "</small><br />" + "<small>(" + list.get(i).builDing_Name + list.get(i).floor_Name + ")</small>"))
                }

                radioButton.tag = list.get(i).coordinate_ID
                radioButton.setOnClickListener {
                    departmentItemClick(list.get(i).coordinate_ID)
                }
            }
        } else {
            for (i in 0..3) {
                radioGroup1.getChildAt(i)
                val radioButton = radioGroup1.getChildAt(i) as RadioButton
                radioButton.visibility = View.VISIBLE
                var locationName= list.get(i).queue_Name
                if(locationName.toCharArray().size<7){
                    radioButton.setText(Html.fromHtml(list.get(i).queue_Name + "<br />" + "<small>(" + list.get(i).builDing_Name + list.get(i).floor_Name + ")</small>"))
                }else{
                    radioButton.setText(Html.fromHtml("<small>"+list.get(i).queue_Name + "</small><br />" + "<small>(" + list.get(i).builDing_Name + list.get(i).floor_Name + ")</small>"))
                }
                radioButton.tag = list.get(i).coordinate_ID
                radioButton.setOnClickListener {
                    radioGroup2.clearCheck()
                    departmentItemClick(list.get(i).coordinate_ID)
                }
            }
            if (list.size - 4 > 0) {
                for (i in 4..list.size - 1) {
                    val radioButton = radioGroup2.getChildAt(i - 4) as RadioButton
                    radioButton.visibility = View.VISIBLE
                    var locationName= list.get(i).queue_Name
                    if(locationName.toCharArray().size<7){
                        radioButton.setText(Html.fromHtml(list.get(i).queue_Name + "<br />" + "<small>(" + list.get(i).builDing_Name + list.get(i).floor_Name + ")</small>"))
                    }else{
                        radioButton.setText(Html.fromHtml("<small>"+list.get(i).queue_Name + "</small><br />" + "<small>(" + list.get(i).builDing_Name + list.get(i).floor_Name + ")</small>"))
                    }
//                    radioButton.setText(Html.fromHtml(list.get(i).queue_Name + "<br />" + "<small>(" + list.get(i).builDing_Name + list.get(i).floor_Name + ")</small>"))
                    radioButton.tag = list.get(i).coordinate_ID
                    radioButton.setOnClickListener {
                        radioGroup1.clearCheck()
                        departmentItemClick(list.get(i).coordinate_ID)
                    }
                }
            }
        }
        return view

    }

    /**
     * 创建科室队列数据展示
     * */
    fun departmentsQueueInfoShow(queueList: QueueList) {
        var list = ArrayList<View>()
        val size = queueList.result_data.size

        if (size == 0) {
            departments_queue_tx.visibility = View.VISIBLE
        } else {
            departments_queue_tx.visibility = View.GONE
        }
        var count = size / 8
        if (count == 0) {
            val arrayList = queueList.result_data as ArrayList<QueueList.ResultDataEntity>
            val createdepartemtsLayout = createdepartemtsQueueLayout(arrayList)
            list.add(createdepartemtsLayout)
        } else {
            val arrayList = queueList.result_data as ArrayList<QueueList.ResultDataEntity>
            var index = 0
            for (i in 1..count) {
                val subList = arrayList.subList(index, index + 8)
                index += 8
                val createdepartemtsLayout = createdepartemtsQueueLayout(subList)
                list.add(createdepartemtsLayout)
            }

            if (size - count * 8 > 0) {
                val arrayList1 = arrayList.subList(index, size)
                val createdepartemtsLayout = createdepartemtsQueueLayout(arrayList1)
                list.add(createdepartemtsLayout)
            }
        }
        departments_viewpager.adapter = DepartmentsInfoAdapter(this, list)
        departments_queue_tablayout.setViewPager(departments_viewpager)
        activity_main_departments_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                list.forEach {
                    it.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop).clearCheck()
                    it.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop2).clearCheck()
                }
            }
        })

    }

    /**
     * 科室队列数据展示动画
     * */
    fun departmentsClassify(queueList: QueueList) {
        activity_main_centre_relativelayout.visibility = View.VISIBLE
        val animator1 = ObjectAnimator.ofFloat(activity_main_centre_relativelayout, "alpha", 0f, 1f)
        animator1.setDuration(500);
        animator1.start();
        departmentsQueueInfoShow(queueList)
    }

    /**
     * 科室点击 直接跳转至终端当前位子获取点位信息
     * */
    fun departmentItemClick(targetID: String) {
        hideWindow()
        loadCurrentLoaciton()
        if (mapFragment.mMapFragmentPersenter == null) {
            mapFragment.setPersentListener(object : InitPersenter {
                override fun onComplete() {
                    mapFragment.mMapFragmentPersenter!!.loadWayType(targetID)
                }
            })
        } else {
            mapFragment.mMapFragmentPersenter!!.loadWayType(targetID)
        }
    }
    /**
     * homepager使用的外景区域公司设施导航
     * */
    fun communalFacilitiesNav(targetID: String,type:String){
        hideWindow()
        loadCurrentLoaciton()
        if (mapFragment.mMapFragmentPersenter == null) {
            mapFragment.setPersentListener(object : InitPersenter {
                override fun onComplete() {
                    mapFragment.mMapFragmentPersenter!!.loadWayType(targetID,type)
                }
            })
        } else {
            mapFragment.mMapFragmentPersenter!!.loadWayType(targetID,type)
        }
    }
    /**
     *  快速查询
     * */
    fun onSearch() {
        RxTextView.textChanges(search_intpu_tv)
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = {
                    mainPersenter!!.loadsearch(it.toString())
                })
    }

    /**
     * 加载快速查询结果
     * */
    fun loadSearchData(seekList: SeekList) {
        if (seekList.result_code.equals("0") && mInputContent.size > 0) {
            search_title_tv.setText("你可能在找以下位置")
        }
        val searchAdatper = SearchAdatper(this, seekList)
        searchAdatper.setItemClick(this)
        seacrch_hot_recycler.adapter = searchAdatper
    }

    /**
     *  搜索结果导航 直接进入终端当前位子获取点位路线信息
     * */
    override fun searchItmeClick(id: Int) {
        hideSearchWindow()
        loadCurrentLoaciton()
        if (mapFragment.mMapFragmentPersenter == null) {
            mapFragment.setPersentListener(object : InitPersenter {
                override fun onComplete() {
                    mapFragment.mMapFragmentPersenter!!.loadWayType(id.toString())
                }
            })
        } else {
            mapFragment.mMapFragmentPersenter!!.loadWayType(id.toString())
        }
    }

    /**
     *
     *  医生导航
     */
    fun doctorNavigation(markId: String) {
        loadCurrentLoaciton()
        mapFragment.setPersentListener(object : InitPersenter {
            override fun onComplete() {
                mapFragment.mMapFragmentPersenter!!.loadWayType(markId)
            }
        })
    }

    fun textchange(view: View) {
        val button = view as Button
        val inputContent = button.text.toString()
        mInputContent.addLast(inputContent)
        var mInputStrContent = ""
        mInputContent.forEach {
            mInputStrContent += it
        }
        search_intpu_tv.setText(mInputStrContent)
    }

    fun delete(view: View) {
        if (mInputContent.size > 1) {
            mInputContent.removeLast()
            var mInputStrContent = ""
            mInputContent.forEach {
                mInputStrContent += it
            }
            search_intpu_tv.setText(mInputStrContent)
        } else {
            mInputContent.clear()
            search_intpu_tv.setText("")
            search_title_tv.setText("热门目的地")
        }
    }

    fun empty(view: View) {
        mInputContent.clear()
        search_intpu_tv.setText("")
        search_title_tv.setText("热门目的地")
    }

    /**
     * 隐藏所有弹窗
     * */
    fun hideWindow() {
        RxBus.post(QueueShowEvent(true))
        activity_main_departments_layout.visibility = View.GONE
        activity_main_centre_relativelayout.visibility = View.GONE
        search_layout_main.visibility = View.GONE
    }

    fun hideSearchWindow() {
        RxBus.post(QueueShowEvent(true))
        search_layout_main.visibility = View.GONE
    }

    fun hideDepartmentsWindow() {
        RxBus.post(QueueShowEvent(true))
        activity_main_departments_layout.visibility = View.GONE
        activity_main_centre_relativelayout.visibility = View.INVISIBLE
    }

    fun close(view: View) {
        hideWindow()
    }

    /**
     * 动态创建公共设施
     */
    fun createCommonalityFacility(datalist: List<Initalize.resultDataEntity.FacilitiesDataEntity>) {

        datalist.forEach {
            var actionbutton = FloatingActionButton(this)
            actionbutton.labelText = it.name
            val value = it.value
            Glide.with(this).load(it.image_Url).into(actionbutton)
            fabactionbutton.addMenuButton(actionbutton)
            actionbutton.setOnClickListener {
                var id = ""
                if (currentLocationType == CurrentLocation.PANORAMA) {
                    type = "Area"
                    id = Constant.AREA!!.area_ID
                } else {
                    type = "Floor"
                    id = mapFragment.getCurrentBuildingID()!!
                }

                mainPersenter!!.loadConmmonalityFacility(type, id, value)
            }
        }
    }

    /**
     * 隐藏公共设施
     * */
    fun hideCommunalFacilitiesButton() {
        fabactionbutton.close(false)
        fabactionbutton.visibility = View.GONE
    }

    /**
     * 显示公共设施
     * */
    fun showCommunalFacilitiesButton() {
        fabactionbutton.visibility = View.VISIBLE
    }

    /**
     *线路导航图回看图片列表展示
     * */
    fun wayBitmapLookBack() {
        var backLookFragment = MapWayBackLookFragment()
        replaceFragment(backLookFragment, R.id.frameLayout)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        closeScreensaver()
        screensaver()
        if (isStartNavigation) {
            return false
        } else {
            return super.dispatchTouchEvent(ev)
        }

    }

    /**
     * 关闭屏保
     * */
    private fun closeScreensaver() {
        video_view.visibility = View.GONE
        video_view.stopPlayback()
        screensaver_im.visibility = View.GONE
        mScreensaverUlits.subscribe?.dispose()
    }

    /**
     * 屏保逻辑
     * */
    fun screensaver() {
        mSubscribe?.dispose()
        val observable = Observable.interval(1, TimeUnit.SECONDS)
        mSubscribe = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it == Constant.ENTERSCREENSAVERTIME.toLong()) {
                        val json = SpUtil.getString(this, SpUtil.SCREEN_JSON)
                        if (!json.equals("")) {

                            val gson = Gson()
                            val materialfinfo = gson.fromJson(json, MaterialInfo::class.java)
                            if (materialfinfo.requestType.equals("0")) {
                                mScreensaverUlits.playLogic(this, 0, materialfinfo, video_view, screensaver_im)
                            } else {
                                mSubscribe!!.dispose()
                            }

                        } else {
                            mSubscribe!!.dispose()
                        }

                    }
                }
    }



    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
        unbindService(mSocketConnetino)
    }

}
