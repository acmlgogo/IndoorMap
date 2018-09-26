package com.shine.indoormap.view.fragment

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import com.bumptech.glide.Glide
import com.shine.indoormap.R
import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.FloorList
import com.shine.indoormap.presenter.HomeFragmentPersenter
import com.shine.indoormap.rx.RxBus
import com.shine.indoormap.view.dialog.FloorInfoDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_layout.*
import com.orhanobut.logger.Logger
import android.view.LayoutInflater
import android.graphics.Bitmap
import android.widget.*
import com.bumptech.glide.request.target.SimpleTarget
import com.orhanobut.logger.LogAdapter
import com.shine.indoormap.base.data.Facilities
import com.shine.indoormap.base.data.MaterialInfo
import com.shine.indoormap.presenter.ActionButtonState
import com.shine.indoormap.presenter.BasePersenter
import com.shine.indoormap.ulits.CanvasUtils
import com.shine.indoormap.ulits.GlideUtils
import com.shine.indoormap.ulits.ScreensaverUlits
import com.shine.indoormap.view.BaseActivity
import com.shine.indoormap.view.MainActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import org.reactivestreams.Subscription
import kotlin.math.log


class HomeFragment : BaseFragment() {


    var inflate: View? = null
    var homeFragmentPersenter: HomeFragmentPersenter? = null
    var cont: Context? = null
    var activityContext: Activity? = null
    var name: String? = null
    var building_id: String? = null
    var mFrameLayout: FrameLayout? = null
    var mImage: ImageView? = null
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    var listview = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflate = inflater!!.inflate(R.layout.home_layout, container, false)
        mFrameLayout = inflate!!.findViewById<FrameLayout>(R.id.fragment_home_framelayout)
        mImage = inflate!!.findViewById<ImageView>(R.id.fragment_home_framelayout_im)
        return inflate!!
    }

    override fun onStart() {
        super.onStart()
        homeFragmentPersenter = HomeFragmentPersenter(this)
        homeFragmentPersenter!!.initModel()
        initView()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        cont = context
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        this.activityContext = activity
    }

    fun initView() {
        GlideUtils.loadImageSimpleTarget(fragment_home_im_bg, activityContext!!, Constant.AREA!!.area_Image_URL)
        mCompositeDisposable.add(RxBus.toFlowable(ImageView::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    cavnsLocation()
                }
        )
        mCompositeDisposable.add(
                RxBus.toFlowable(FloorList::class.java).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { it ->
                            Logger.d("创建楼程dialog")
                            showFloorDialog(it)
                        }
        )
        mCompositeDisposable.add(
                RxBus.toFlowable(Facilities::class.java).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    cavnsCommunalFacilities(it, fragment_home_framelayout)
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
                                        fragment_home_framelayout!!.removeView(it)
                                    }
                                }
                        )
        )



    }


    /**
     *  显示楼栋信息详情
     * */
    fun showFloorDialog(floorList: FloorList) {
        var floorDialog = FloorInfoDialog(activityContext)
        floorDialog.setFloorData(floorList)
        floorDialog.setBuildingID(building_id!!)
        floorDialog.setTilelName(name!!)
        floorDialog.setOnDismissListener {
        }
        floorDialog.show()
    }

    /**
     * 绘制楼宇坐标点
     * */
    fun createLoactionButton(context: Context, parentView: FrameLayout, x: Float, y: Float, name: String, id: String) {
        var button = Button(context)
//        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val layoutParams = ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT)
        button.layoutParams = layoutParams
        val drawable = context.resources.getDrawable(R.drawable.gps_location)
//        button.x = x - drawable.intrinsicWidth / 2 - 100
        button.x = x  - 100
        button.y = y - drawable.intrinsicHeight * 2
        button.setText(name)
        button.setTextSize(20f)
        button.background = context.resources.getDrawable(R.drawable.map_button_selector)
        button.setOnClickListener { v ->
            homeFragmentPersenter!!.getfloorData(id)
            val button1 = v as Button
            this.name = button1.text.toString()
            this.building_id = id
        }

        parentView.addView(button)

    }

    fun cavnsLocation() {

        val builDing = Constant.RESULT!!.builDing
        var hashMap = HashMap<String, String>()

        builDing.forEach {
            hashMap.put(it.x, it.y)
            createLoactionButton(activityContext as Context, this!!.mFrameLayout!!, it.x.toFloat(), it.y.toFloat(), it.name, it.builDing_ID)
        }

        CanvasUtils.CoordinatesToDraw(activityContext as Context, this!!.mImage!!, hashMap, this!!.mFrameLayout!!)
    }

    /**
     * 绘制公共设施点位信息
     * */
    fun cavnsCommunalFacilities(facilities: Facilities, parentView: ViewGroup) {
        if (facilities.result_code.equals("0")) {
            listview!!.forEach {
                parentView.removeView(it)
            }
            facilities.result_data.areaFacilitieData.forEach {
                var button = ImageButton(activity)
                val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                button.layoutParams = layoutParams
                button.background = activity!!.resources.getDrawable(R.drawable.gonggong)
                val drawable = activity!!.resources.getDrawable(R.drawable.location)
               var mark= it.markId.toString()
                button.x = (it.x.toFloat() * (parentView.width / Constant.AREA!!.x.toFloat())) - drawable.intrinsicWidth / 2
                button.y = (it.y.toFloat() * (parentView.height / Constant.AREA!!.y.toFloat())) - drawable.intrinsicHeight
                button.setOnClickListener {
                    communalFacilities(mark)
                }
                parentView.addView(button)
                listview!!.add(button)
            }

        } else {
            // 没有数据产生
        }
    }

    /**
     * 查看室外公共设施
     * */
    fun communalFacilities(targetID: String){
        val mainActivity = activity as MainActivity
        mainActivity.communalFacilitiesNav(targetID,"Area")
    }


    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }


}