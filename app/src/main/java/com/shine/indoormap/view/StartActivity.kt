package com.shine.indoormap.view

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.transition.Slide
import android.util.Xml
import android.view.Gravity
import com.orhanobut.logger.Logger
import com.shine.indoormap.R
import com.shine.indoormap.activity
import com.shine.indoormap.base.Constant
import com.shine.indoormap.presenter.StartPersenter
import kotlinx.android.synthetic.main.activity_start.*
import org.xmlpull.v1.XmlPullParser
import java.io.File
import java.io.FileInputStream

class StartActivity : BaseActivity() {


    var startPersenter: StartPersenter? = null

    override fun showMessage(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("信息提示")
        builder.setMessage(message)
        builder.show()
    }

    override fun networkaBnormalDialog(message: String) {
        AlertDialog.Builder(this).setTitle("异常提醒").setMessage(message)
                .setPositiveButton("刷新", { d, i -> startPersenter!!.initialize() }).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        if (!Constant.ISDEBUG) {
            if (initServiceUIR()) {
                startPersenter = StartPersenter(this)
                startPersenter!!.initialize()
                val xdpi = resources.displayMetrics.xdpi
                val ydpi = resources.displayMetrics.ydpi
                Logger.d("${xdpi}    ,,,  ${ydpi}")
            } else {
                start_tv.setText("初始化文件不存在,无法找到服务器IP。")
                start_hint_tv.setText("安装提示：把Server.xml导入dota/data/目录下后重启软件即可运行！")
            }
        } else {
            startPersenter = StartPersenter(this)
            startPersenter!!.initialize()
            val xdpi = resources.displayMetrics.xdpi
            val ydpi = resources.displayMetrics.ydpi
            Logger.d("${xdpi}    ,,,  ${ydpi}")
        }


    }

    private fun initServiceUIR(): Boolean {
        val file = File("/data/data/Server.xml")
        if (!file.exists()) {
            return false
        }
        try {
            val parser = Xml.newPullParser()
            val inputStream = FileInputStream(file)
            parser.setInput(inputStream, "utf-8")
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> if ("ServerIp" == parser.name) {
                        Constant.BASE_URL = parser.nextText()

                    }
                    XmlPullParser.END_TAG -> {
                    }

                    else -> {
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    override fun initView() {

    }

    fun initializeComplete() {
        this.activity(this, MainActivity::class.java)
    }


}