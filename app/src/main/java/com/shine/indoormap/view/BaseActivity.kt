package com.shine.indoormap.view


import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.view.Gravity
import android.view.View
import com.orhanobut.logger.Logger
import com.shine.indoormap.R
import com.shine.indoormap.base.CurrentLocation
import com.shine.indoormap.view.`interface`.BaseViewIntface
import com.shine.indoormap.view.`interface`.MainActivityInterface
import com.shine.indoormap.view.fragment.HomeFragment
import com.shine.indoormap.view.fragment.MapFragment

abstract open class BaseActivity : AppCompatActivity(), BaseViewIntface {
    var currentLocationType= CurrentLocation.FLOOR
    var loadDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        hideBottomUIMenu()
    }

    fun replaceFragment(fragment: Fragment, layoutid: Int) {

        var slide=Slide()

        slide.slideEdge=Gravity.RIGHT

        slide.duration=resources.getInteger(R.integer.anim_duration_long).toLong()

        if (fragment is MapFragment) {
            currentLocationType=CurrentLocation.FLOOR
        }else if(fragment is HomeFragment){
            currentLocationType=CurrentLocation.PANORAMA
        }

        val commit = supportFragmentManager.beginTransaction().replace(layoutid, fragment).commit()
        Logger.d("fragment 替换结果 ${commit}")
    }
    protected fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    abstract fun initView()

    override fun onCreateViewAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     *  数据加载等待 dialog
     *
     * */
    override fun loadDialog(message: String) {
        loadDialog = ProgressDialog(this)
        loadDialog!!.setMessage(message)
        loadDialog!!.setCanceledOnTouchOutside(false)
        loadDialog!!.show()
    }

    /**
     * 数据加载完成关闭 加载框dialog
     * */
    override fun closeDialog() {
        loadDialog!!.cancel()

    }


}