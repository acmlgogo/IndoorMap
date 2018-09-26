package com.shine.indoormap.view.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.Html
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.orhanobut.logger.Logger
import com.shine.indoormap.R
import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.Event.NavigationEvent
import com.shine.indoormap.base.Event.QueueShowEvent
import com.shine.indoormap.base.data.CurrentFloorQueueInfo
import com.shine.indoormap.base.data.Facilities
import com.shine.indoormap.base.data.QueueList
import com.shine.indoormap.base.data.WayData
import com.shine.indoormap.base.data.initalize.Initalize
import com.shine.indoormap.engine.SpeechEngine
import com.shine.indoormap.presenter.Action
import com.shine.indoormap.presenter.ActionButtonState
import com.shine.indoormap.presenter.MapFragmentPersenter
import com.shine.indoormap.rx.RxBus
import com.shine.indoormap.ulits.CanvasUtils
import com.shine.indoormap.ulits.GlideUtils
import com.shine.indoormap.ulits.Util
import com.shine.indoormap.view.BaseActivity
import com.shine.indoormap.view.MainActivity
import com.shine.indoormap.view.adapter.CurrentFloorQueueAdapter
import com.shine.indoormap.view.adapter.DepartmentsInfoAdapter
import com.shine.indoormap.view.adapter.MapAdapter
import com.shine.indoormap.view.adapter.MyTabAdapter
import com.shine.indoormap.view.customView.leochuan.CarouselLayoutManager
import com.shine.indoormap.view.customView.leochuan.ViewPagerLayoutManager.VERTICAL
import com.shine.indoormap.view.customView.leochuan.tabindicator.TabView
import com.shine.indoormap.view.customView.leochuan.tabindicator.VerticalTabLayout
import com.shine.indoormap.view.customView.leochuan.wayImageView.WayImageView
import com.shine.indoormap.view.dialog.FloorWayDialog
import com.shine.indoormap.view.dialog.WaySelectDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.departments_layout.*
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MapFragment : BaseFragment() {
    var mInitPersenter: InitPersenter? = null
    var mMapFragmentPersenter: MapFragmentPersenter? = null
    var BUILDING_ID: String? = null
    var FLOOR_ID: String? = null
    var LOCATION_NAME: String? = null
    var BUILDNAME: String? = null
    private var move = false
    var changeTab = false
    private var mIndex = 0
    var listview = ArrayList<View>()
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    var mSelectMarkId = ""
    var mData = ArrayList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>()
    var wayEndListener: WayImageView.WayEndListener? = null
    var mapAdapter: MapAdapter? = null
    var subscribe: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        FLOOR_ID = arguments!!.getString("FLOOR_ID")
        BUILDING_ID = arguments!!.getString("BUILDING_ID")
        LOCATION_NAME = arguments!!.getString("LOCATION_NAME")
        BUILDNAME = arguments!!.getString("BUILDNAME")
        initPersenter()
        mCompositeDisposable.add(
                RxBus.toFlowable(Facilities::class.java).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    cavnsCommunalFacilities(it, fragment_map_layout)
                                },
                                onComplete = {

                                },
                                onError = {

                                }
                        )
        )
        mCompositeDisposable.add(
                RxBus.toFlowable(ActionButtonState::class.java).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    listview.forEach {
                                        fragment_map_layout.removeView(it)
                                    }
                                }
                        )
        )
        mCompositeDisposable.add(
                RxBus.toFlowable(QueueShowEvent::class.java).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    if (it.isShow) {
                                        showCurrentDepartmentAnimation()
                                    } else {
                                        hideDepartmentsWindow()
                                    }
                                }
                        )

        )

    }

    private fun initPersenter() {
        mMapFragmentPersenter = MapFragmentPersenter(activity as BaseActivity, this)
        mMapFragmentPersenter!!.initModel()
        mMapFragmentPersenter!!.loadFloorData(BUILDING_ID!!)
        loadCurrentDepartment()
        mInitPersenter?.onComplete()

    }

    fun initView(data: ArrayList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>) {
        fragment_map_canvs_image.setOnClickListener {
            if (mapfragment_keshi_layout.visibility == View.INVISIBLE && fragment_map_mapimg_recyclerview.visibility == View.GONE) {

                showCurrentDepartmentAnimation()
            } else {
                hideDepartmentsWindow()
            }
        }
        var floorList = LinkedList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>()
        data.forEach {
            floorList.addFirst(it)
        }
        val arrayList = ArrayList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>()
        arrayList.clear()
        arrayList.addAll(floorList)
        mData = arrayList
        /*******************************全楼层显示***************************************************/
        var layoutmanager = CarouselLayoutManager(activity, Util.Dp2px(activity, 80f), VERTICAL)
        fragment_map_mapimg_recyclerview.layoutManager = layoutmanager
        mapAdapter = MapAdapter(activity!!, mData)
        mapAdapter!!.setClickListener(object : MapAdapter.ItemClickListener() {
            override fun itemClick(mData: Initalize.resultDataEntity.BuilDingEntity.FloorEntity) {
                FLOOR_ID = mData.floor_ID
                if (!FLOOR_ID.equals(Constant.CURRENT!!.floor_ID)) {
                    fragment_map_canvs_image.isShowCurrentTerminalLocation = false
                }
                dismissMapListWindow()
                fragment_map_canvs_image.setCurrentLocationName("地图位置：${BUILDNAME + mData.floor_Name}")
                Glide.with(activity!!).load(mData.floor_Imager_URL).into(fragment_map_canvs_image)
                loadCurrentDepartment()
            }
        })
        fragment_map_mapimg_recyclerview.adapter = mapAdapter
        fragment_map_mapimg_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState);

                if (changeTab) {

                    tablayout.setTabSelected(layoutmanager!!.findLastCompletelyVisibleItemPosition())
                }

            }
        })
        fragment_map_mapimg_recyclerview.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event!!.action == MotionEvent.ACTION_UP) {
                    changeTab = true
                } else {
                    changeTab = false
                }
                return false
            }

        })
        tablayout.setTabAdapter(MyTabAdapter(activity!!, mData))

        tablayout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabView?, position: Int) {

                smoothMoveToPosition(position, layoutmanager)
            }

            override fun onTabReselected(tab: TabView?, position: Int) {
                Logger.d("onTabReselected：" + position)
            }

        })

        /*************************************** 楼程图片展示*************************************************************/
        data.forEach {
            if (it.floor_ID == FLOOR_ID) {
                fragment_map_canvs_image.setCurrentLocationName("地图位置:" + BUILDNAME + it.floor_Name)
                Glide.with(this).load(it.floor_Imager_URL).into(fragment_map_canvs_image)
                if (Constant.CURRENT!!.floor_ID.equals(FLOOR_ID)) {
                    showTreminalMark(Constant.TERMINAL!!.x, Constant.TERMINAL!!.y)
                }

            }
        }

        fragment_map_actionbutton.setOnClickListener {
            if (fragment_map_mapimg_recyclerview.visibility == View.GONE) {
                showMapListWindow()
            } else {
                dismissMapListWindow()
            }
        }


    }

    /**
     * 获取当前楼层显示信息
     * */
    fun getCurrentBuildingID(): String? {
        return arguments!!.getString("BUILDING_ID")
    }

    /**
     * 没有过度移动
     * */
    fun moveToPosition(n: Int, layoutmanager: CarouselLayoutManager) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        var firstItem = layoutmanager.findFirstVisibleItemPosition();
        var lastItem = layoutmanager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            fragment_map_mapimg_recyclerview.scrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            var top = fragment_map_mapimg_recyclerview.getChildAt(n - firstItem).getTop();
            fragment_map_mapimg_recyclerview.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            fragment_map_mapimg_recyclerview.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }

    }

    /**
     * 滑动移动
     * */
    private fun smoothMoveToPosition(n: Int, layoutmanager: CarouselLayoutManager) {
        val firstItem = layoutmanager.findFirstVisibleItemPosition()
        val lastItem = layoutmanager.findLastVisibleItemPosition()
        if (n <= firstItem) {
            fragment_map_mapimg_recyclerview.smoothScrollToPosition(n)
        } else if (n <= lastItem) {
            val top = fragment_map_mapimg_recyclerview.getChildAt(n - firstItem).getTop()
            fragment_map_mapimg_recyclerview.smoothScrollBy(0, top)
        } else {
            fragment_map_mapimg_recyclerview.smoothScrollToPosition(n)
            move = true
        }

    }

    /**
     * 显示楼层列表
     * */
    fun showMapListWindow() {

        hideDepartmentsWindow()
        tablayout.visibility = View.VISIBLE
        var animat = ObjectAnimator.ofFloat(tablayout, "alpha", 0f, 1f);
        animat.setDuration(1500)
        animat.start()
        animat.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                fragment_map_canvs_image.visibility = View.GONE
                if (fragment_map_mapimg_recyclerview != null) {
                    val adapter = fragment_map_mapimg_recyclerview.adapter as MapAdapter
                    for (index in adapter.getdata().indices) {
                        if (FLOOR_ID.equals(adapter.getdata().get(index).floor_ID)) {
                            tablayout.setTabSelected(index)

                        }
                    }
                }

            }

        })
        var animatorSetsuofang = AnimatorSet();//组合动画
        var scaleX = ObjectAnimator.ofFloat(fragment_map_canvs_image, "scaleX", 1f, 0f);
        var scaleY = ObjectAnimator.ofFloat(fragment_map_canvs_image, "scaleY", 1f, 0f);
        animatorSetsuofang.setDuration(1000);
        animatorSetsuofang.setInterpolator(DecelerateInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.start();
        animatorSetsuofang.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                fragment_map_mapimg_recyclerview?.visibility = View.VISIBLE

            }

        })
        removeCommunalFacilities()

    }

    /**
     * 隐藏楼层列表
     * */
    fun dismissMapListWindow() {
        showCurrentDepartmentAnimation()
        fragment_map_canvs_image.visibility = View.VISIBLE
        fragment_map_mapimg_recyclerview.visibility = View.GONE
        var animat = ObjectAnimator.ofFloat(tablayout, "alpha", 1f, 0f);
        animat.setDuration(500)
        animat.start()
        var animatorSetsuofang = AnimatorSet();//组合动画
        var scaleX = ObjectAnimator.ofFloat(fragment_map_canvs_image, "scaleX", 0f, 1f);
        var scaleY = ObjectAnimator.ofFloat(fragment_map_canvs_image, "scaleY", 0f, 1f);
        animatorSetsuofang.setDuration(1000);
        animatorSetsuofang.setInterpolator(DecelerateInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始

        animatorSetsuofang.start();
        showCommunalFacilitiesButton()
    }


    /**
     *  当前楼程导航
     * */
    fun canvsWay(data: WayData) {
        val mainActivity = activity as MainActivity
        mainActivity.fabactionbutton.close(false)
        Constant.WAYDATA = data
        RxBus.post(NavigationEvent(true))//发送开始导航事件
        hideDepartmentsWindow()
        fragment_map_canvs_image.isShowCurrentTerminalLocation = false
        val dataway = data.result_data.wayData.get(0)
        var datalist = ArrayList<WayData.ResultDataEntity.WayDataEntity>()
        datalist.addAll(dataway)
        val lcotaion = data.result_data.wayPath.get(0)
        var locationNmae = "地图位置 ："
        var buildingName = ""
        var floorName = ""
        Constant.RESULT!!.builDing.forEach {
            if (it.builDing_ID.equals(lcotaion.builDing_ID.toString())) {
                buildingName = it.name
                it.floor.forEach {
                    if (it.floor_ID.equals(lcotaion.floor_ID.toString())) {
                        floorName = it.floor_Name
                    }
                }
            }
        }
        val floor_Name = data.result_data.wayPath.get(data.result_data.wayPath.lastIndex).floor_Name
        val builDing_Name = data.result_data.wayPath.get(data.result_data.wayPath.lastIndex).builDing_Name
        var targetName = "目标位置 :" + builDing_Name + floor_Name
        fragment_map_canvs_image.setWayData(datalist, locationNmae + buildingName + floorName, targetName, fragment_map_layout)
        fragment_map_canvs_image.setWayListener(object : WayImageView.WayEndListener {
            override fun wayEnd() {
                Toast.makeText(activity, "导航结束", Toast.LENGTH_SHORT).show()
                count_down_tv.visibility = View.VISIBLE
                var countDown = 3
                val observable = Observable.interval(1, TimeUnit.SECONDS)
                subscribe = observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            SpeechEngine.getInstance().add("导航结束")
                            count_down_tv.setText("${5 - it}秒后回到当前位置")
                            if (it == 5L) {
                                RxBus.post(NavigationEvent(false))//发送导航结束事件
                                count_down_tv.visibility = View.GONE
                                showLocation()
                                fragment_map_canvs_image.closeWayPath()
                                activity!!.main_backlook_actionbutton.visibility = View.VISIBLE
                                subscribe!!.dispose()

                            }
                        }


            }

        })
        activity!!.main_backlook_actionbutton.visibility = View.VISIBLE
    }

    /**
     * 路线上下楼方式选择
     * */
    fun wayTypeSelect(data: WayData, type: String) {
        var waySelectDialog = WaySelectDialog(activity, data)
        val window = waySelectDialog.window
        window.setWindowAnimations(R.style.dialogWindowAnim)
        waySelectDialog.setWaySelectListener(object : WaySelectDialog.WaySelectListener {
            override fun wayTypeSelect(one: Int, two: Int) {
                mMapFragmentPersenter!!.loadWayData(mSelectMarkId, one.toString(), two.toString(),type=type)
            }

        })
        waySelectDialog.show()

    }

    /**
     * 多层导航
     * */
    fun transRegionalWayCanvs(data: WayData) {
        val mainActivity = activity as MainActivity
        mainActivity.fabactionbutton.close(false)
        Constant.WAYDATA = data
        RxBus.post(NavigationEvent(true))//发送开始导航事件
        hideDepartmentsWindow()
        Constant.WAYTYPEARRAY?.clear()
        fragment_map_canvs_image.isShowCurrentTerminalLocation = false
        var index = 0
        val data = data.result_data
        val wayPath = data.wayPath
        val wayData = data.wayData
        wayEndListener = object : WayImageView.WayEndListener {
            override fun wayEnd() {
                index++
                if (index < wayPath.size) {
                    var type = wayData.get(index - 1)[wayData[index - 1].size - 1].state
                    var dismissListener = object : FloorWayDialog.DialogDismissListener {
                        override fun dismiss() {
                            transRegionalWayCanvs(index, wayPath, wayData, wayEndListener!!)
                        }
                    }
                    Constant.WAYTYPEARRAY!!.add(type)
                    showFloorWay(type, wayPath.get(index - 1).builDing_ID, wayPath.get(index - 1).floor_ID, wayPath.get(index).builDing_ID, wayPath.get(index).floor_ID, dismissListener)
                } else {
                    Toast.makeText(activity, "导航结束", Toast.LENGTH_SHORT).show()
                    count_down_tv.visibility = View.VISIBLE
                    val observable = Observable.interval(1, TimeUnit.SECONDS)
                    subscribe = observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                SpeechEngine.getInstance().add("导航结束")
                                count_down_tv.setText("${5 - it}秒后回到当前位子")
                                if (it == 5L) {
                                    RxBus.post(NavigationEvent(false))//发送导航结束事件
                                    count_down_tv.visibility = View.GONE
                                    showLocation()
                                    fragment_map_canvs_image.closeWayPath()
                                    activity!!.main_backlook_actionbutton.visibility = View.VISIBLE
                                    subscribe!!.dispose()
                                }
                            }
                }
            }
        }
        transRegionalWayCanvs(index, wayPath, wayData, wayEndListener!!)
    }

    /**
     * 多路线绘制
     * */
    fun canvsWay(data: List<WayData.ResultDataEntity.WayDataEntity>, wayEndListener: WayImageView.WayEndListener, path: WayData.ResultDataEntity.WayPathEntity, targetName: String) {
        var datalist = ArrayList<WayData.ResultDataEntity.WayDataEntity>()
        datalist.addAll(data)
        val lcotaion = path
        var locationNmae = "地图位置 ："
        Constant.RESULT!!.builDing.forEach {
            if (it.builDing_ID.equals(lcotaion.builDing_ID.toString())) {
                locationNmae += it.name
                it.floor.forEach {
                    if (it.floor_ID.equals(lcotaion.floor_ID.toString())) {
                        locationNmae += it.floor_Name
                    }

                }
            }
        }

        fragment_map_canvs_image.setWayData(datalist, locationNmae, targetName, fragment_map_layout)
        fragment_map_canvs_image.setWayListener(wayEndListener)
    }

    /**
     *  多层路线绘制
     * */
    fun transRegionalWayCanvs(index: Int, path: List<WayData.ResultDataEntity.WayPathEntity>, wayData: List<List<WayData.ResultDataEntity.WayDataEntity>>, wayEndListener: WayImageView.WayEndListener) {

        val floor_Name = path.get(path.lastIndex).floor_Name
        val builDing_Name = path.get(path.lastIndex).builDing_Name
        var targetName = "目标位置 ：" + builDing_Name + floor_Name
        var simpleTarget = object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                fragment_map_canvs_image.setImageBitmap(resource)
                canvsWay(wayData.get(index), wayEndListener, path.get(index), targetName)
            }
        }
        if (index < path.size) {
            //1 室内 2 室外
            if (path.get(index).type == 1) {
                if (index != 0) {
                    Constant.RESULT!!.builDing.forEach {
                        if (path.get(index).builDing_ID == it.builDing_ID.toInt()) {
                            it.floor.forEach {
                                if (path.get(index).floor_ID == it.floor_ID.toInt()) {
                                    GlideUtils.loadImage(fragment_map_canvs_image, activity, it.floor_Imager_URL, simpleTarget)
                                }
                            }
                        }
                    }
                } else {
                    canvsWay(wayData.get(index), wayEndListener, path.get(index), targetName)
                }
            } else {
                GlideUtils.loadImage(fragment_map_canvs_image, activity, Constant.AREA!!.area_Image_URL, simpleTarget)
            }
        } else {

        }
    }

    /**
     *  计算上下楼方向
     * */
    fun showFloorWay(type: Int, currentBuildId: Int, currentFloorId: Int, targetBuildId: Int, targetFloorId: Int, dialogDismissListener: FloorWayDialog.DialogDismissListener) {
        var buildingMap = HashMap<Int, List<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>>()
        Constant.RESULT!!.builDing.forEach {
            buildingMap.put(it.builDing_ID.toInt(), it.floor)
        }
        //同栋大楼上下楼导航
        if (currentBuildId == targetBuildId) {
            var currentIndex: Int? = null
            var targetIndex: Int? = null
            val floordata = buildingMap.get(currentBuildId)
            for (index in floordata!!.indices) {
                if (currentFloorId == floordata.get(index).floor_ID.toInt()) {
                    currentIndex = index
                }
                if (targetFloorId == floordata.get(index).floor_ID.toInt()) {
                    targetIndex = index
                }
            }
            val floorWayDialog = FloorWayDialog(activity!!)
            floorWayDialog.mDismissListener = dialogDismissListener
            if (currentIndex!! > targetIndex!!) {
                // 上楼
                floorWayDialog.setData(false, type)
                floorWayDialog.show()
            } else {
                floorWayDialog.setData(true, type)
                floorWayDialog.show()
            }
        } else {
            // 夸楼 直接从
            dialogDismissListener.dismiss()
        }
    }

    /**
     * 绘制公共设施点位
     * */
    fun cavnsCommunalFacilities(facilities: Facilities, parentView: ViewGroup) {
        if (facilities.result_code.equals("0")) {
            listview!!.forEach {
                parentView.removeView(it)
            }
            if (facilities.result_data.floorFacilitieData.size > 0) {
                var isHave: Boolean = false
                facilities.result_data.floorFacilitieData.forEach {
                    if (it.floor_ID == (FLOOR_ID!!.toInt())) {
                        isHave = true
                        it.markData.forEach {
                            val markId = it.markId
                            var button = ImageButton(activity)
                            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            button.layoutParams = layoutParams
                            button.background = activity!!.resources.getDrawable(R.drawable.gonggong)
                            val drawable = activity!!.resources.getDrawable(R.drawable.location)
                            button.x = (it.x.toFloat() * (parentView.width / Constant.AREA!!.x.toFloat())) - drawable.intrinsicWidth / 2
                            button.y = (it.y.toFloat() * (parentView.height / Constant.AREA!!.y.toFloat())) - drawable.intrinsicHeight
                            button.setOnClickListener {
                                //获取公共设置行走路线
                                mMapFragmentPersenter!!.loadWayType(markId.toString())
                            }
                            parentView.addView(button)
                            listview!!.add(button)
                        }
                    }
                }
                if (!isHave) {
                    AlertDialog.Builder(activity).setTitle("导航提醒").setMessage("该楼层没有此项设施，前往其他楼层查看.")
                            .setPositiveButton("确定", { d, i ->
                                for (i in 0..tablayout.childCount - 1) {
                                    tablayout.setTabBadge(i, 0)
                                }

                                facilities.result_data.floorFacilitieData.forEach {
                                    for (index in mData.indices) {
                                        if (it.floor_ID == mData[index].floor_ID.toInt()) {
                                            tablayout.setTabBadge(index, it.markData.size)
                                        }
                                    }
                                }
                                showMapListWindow()
                            }).setNegativeButton("取消", { d, i -> {} }).show()
                }
            } else {
                mMapFragmentPersenter!!.messageAction(Action.SHOWERRORMESSAGE, "该栋大楼没有此项设施")
            }
        } else {
            // 没有数据产生
        }
    }

    /**
     * 清除公共设施点位
     * */
    fun removeCommunalFacilities() {
        listview.forEach {
            fragment_map_layout.removeView(it)
        }
        hideCommunalFacilitiesButton()
    }

    /**
     * 显示公共设施提供按键
     * */
    fun showCommunalFacilitiesButton() {
        val mainActivity = activity as MainActivity
        mainActivity.showCommunalFacilitiesButton()
    }

    /**
     * 隐藏个公共设施按键
     * */
    fun hideCommunalFacilitiesButton() {
        val mainActivity = activity as MainActivity
        mainActivity.hideCommunalFacilitiesButton()

    }

    /**
     *  显示终端当前位子楼层图
     * */
    fun showLocation() {
        val mainActivity = activity as MainActivity
        mainActivity.fabactionbutton.visibility = View.VISIBLE
        if (fragment_map_mapimg_recyclerview.visibility == View.VISIBLE) {
            dismissMapListWindow()
        }
        Constant.RESULT!!.builDing.forEach {
            if (BUILDING_ID.equals(it.builDing_ID)) {
                var floor = ArrayList<Initalize.resultDataEntity.BuilDingEntity.FloorEntity>()
                floor.addAll(it.floor)
                Collections.reverse(floor)
                mData.clear()
                mData.addAll(floor)
            }
        }
        mapAdapter!!.notifyDataSetChanged()
        mData.forEach {
            if (it.floor_ID == Constant.CURRENT!!.floor_ID) {
                Glide.with(this).load(it.floor_Imager_URL).into(fragment_map_canvs_image)
                fragment_map_canvs_image.setCurrentLocationName("地图位置 ：${LOCATION_NAME}")
                showTreminalMark(Constant.TERMINAL!!.x, Constant.TERMINAL!!.y)
                loadCurrentDepartment()
            }
        }

    }

    /**
     * 显示终端坐标
     * */
    fun showTreminalMark(x: Int, y: Int) {
        fragment_map_canvs_image.setCurrentTerminalLocation(x, y)
        fragment_map_canvs_image.isShowCurrentTerminalLocation = true
    }

    /**
     * 当前楼程科室信息展示
     * */
    fun showCurrentDepartmentAnimation() {
        mapfragment_keshi_layout.visibility = View.VISIBLE
        val animator = ObjectAnimator.ofFloat(mapfragment_keshi_layout, "translationY", Constant.HEIGTH!!.toFloat(), 0f, 0f)
        animator.setDuration(1000)
        animator.start()

    }

    /**
     * 隐藏当前楼程科室列表展示窗体
     * **/
    fun hideDepartmentsWindow() {
        mapfragment_keshi_layout.visibility = View.INVISIBLE
    }

    /**
     *  显示当前楼层科室数据数据
     * */
    fun showCurrentDepartment(currentFloorQueueInfo: CurrentFloorQueueInfo) {


        showCurrentDepartmentAnimation()
        departmentsQueueInfoShow(currentFloorQueueInfo)
    }

    /**
     * 加载当前楼层科室列表数据
     * */
    fun loadCurrentDepartment() {
        mMapFragmentPersenter!!.loadCurrentDeparment(FLOOR_ID.toString())
    }

    /**
     *  设置Persent初始化监听
     * */
    fun setPersentListener(initPersenter: InitPersenter?) {
        mInitPersenter = initPersenter
    }


    /**
     * 排版科室队列信息展示
     * */
    fun createdepartemtsQueueLayout(list: MutableList<CurrentFloorQueueInfo.ResultDataEntity>): View {
        val view = layoutInflater.inflate(R.layout.departments_adapter_layout, null)
        val radioGroup1 = view.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop)
        val radioGroup2 = view.findViewById<RadioGroup>(R.id.departments_adapter_radiogrop2)
        if (list.size / 4 == 0) {
            for (i in 0..list.size % 4 - 1) {
                val radioButton = radioGroup1.getChildAt(i) as RadioButton
                radioButton.visibility = View.VISIBLE

                var locationName = list.get(i).queue_Name
                if (locationName.toCharArray().size < 7) {
                    radioButton.setText(locationName)
                } else {
                    radioButton.setText(Html.fromHtml("<small>" + locationName + "</small>"))
                }
                radioButton.tag = list.get(i).coordinate_ID
                radioButton.setOnClickListener {
                    currentQueueClick(list.get(i).coordinate_ID)
                }
            }
        } else {
            for (i in 0..3) {
                radioGroup1.getChildAt(i)
                val radioButton = radioGroup1.getChildAt(i) as RadioButton
                radioButton.visibility = View.VISIBLE
//                radioButton.setText(list.get(i).queue_Name)
                var locationName = list.get(i).queue_Name
                if (locationName.toCharArray().size < 7) {
                    radioButton.setText(locationName)
                } else {
                    radioButton.setText(Html.fromHtml("<small>" + locationName + "</small>"))
                }
                radioButton.tag = list.get(i).coordinate_ID
                radioButton.setOnClickListener {
                    radioGroup2.clearCheck()
                    currentQueueClick(list.get(i).coordinate_ID)
                }
            }
            if (list.size - 4 > 0) {
                for (i in 4..list.size - 1) {
                    val radioButton = radioGroup2.getChildAt(i - 4) as RadioButton
                    radioButton.visibility = View.VISIBLE
                    var locationName = list.get(i).queue_Name
                    if (locationName.toCharArray().size < 7) {
                        radioButton.setText(locationName)
                    } else {
                        radioButton.setText(Html.fromHtml("<small>" + locationName + "</small>"))
                    }
                    radioButton.tag = list.get(i).coordinate_ID
                    radioButton.setOnClickListener {
                        radioGroup1.clearCheck()
                        currentQueueClick(list.get(i).coordinate_ID)
                    }
                }
            }
        }
        return view

    }

    private fun currentQueueClick(mark: String?) {
        mMapFragmentPersenter!!.loadWayType(mark!!)
        val mainActivity = activity as MainActivity
        mainActivity.fabactionbutton.close(false)
        hideDepartmentsWindow()
    }

    /**
     * 创建科室队列数据展示
     * */
    fun departmentsQueueInfoShow(currentFloorQueueInfo: CurrentFloorQueueInfo) {
        var list = ArrayList<View>()
        val size = currentFloorQueueInfo.result_data.size
        if (size == 0) {
            fragment_departments_queue_tx.visibility = View.VISIBLE
        } else {
            fragment_departments_queue_tx.visibility = View.GONE
        }
        var count = size / 8
        if (count == 0) {
            val arrayList = currentFloorQueueInfo.result_data as ArrayList<CurrentFloorQueueInfo.ResultDataEntity>
            val createdepartemtsLayout = createdepartemtsQueueLayout(arrayList)
            list.add(createdepartemtsLayout)
        } else {
            val arrayList = currentFloorQueueInfo.result_data as ArrayList<CurrentFloorQueueInfo.ResultDataEntity>
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
        fragment_departments_viewpager.adapter = DepartmentsInfoAdapter(context!!, list)
        fragment_departments_queue_tablayout.setViewPager(fragment_departments_viewpager)
        fragment_departments_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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


    override fun onDestroy() {
        super.onDestroy()
        subscribe?.dispose()
        mCompositeDisposable.clear()
    }

}

