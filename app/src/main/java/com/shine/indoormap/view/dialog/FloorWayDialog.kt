package com.shine.indoormap.view.dialog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shine.indoormap.R
import com.shine.indoormap.engine.SpeechEngine
import com.shine.indoormap.ulits.CanvasUtils
import com.shine.indoormap.ulits.GlideUtils
import kotlinx.android.synthetic.main.floor_way_dialog_layout.*

class FloorWayDialog(context: Context) : BaseDialog(context) {
    var imageView: ImageView? = null
    var isUp: Boolean? = null
    var type: Int? = null
    var mHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            dismiss()
            mDismissListener!!.dismiss()
        }
    }
    var mDismissListener: DialogDismissListener? = null
    override fun initdata() {
        imageView = findViewById<ImageView>(R.id.floor_way_dialog_im)
        floorAnimation(isUp!!, type!!, mHandler, imageView!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.floor_way_dialog_layout)
        initdata()

    }

    fun setListener(dismissListener: DialogDismissListener) {
        mDismissListener = dismissListener
    }

    fun setData(isUp: Boolean, type: Int) {
        this.isUp=isUp
        this.type=type
    }

    /**
     * 上下楼动画  @param type 3 楼梯 4 手扶楼梯 5 自动电梯
     * */
    fun floorAnimation(isUp: Boolean, type: Int, handler: Handler, view: ImageView) {
        when (type) {
            3 -> {
                if (isUp) {
                    GlideUtils.loadGif(view, context, R.drawable.shanglou, handler)
                } else {
                    GlideUtils.loadGif(view, context, R.drawable.xialou, handler)

                }
            }
            4 -> {
                if (isUp) {
                    GlideUtils.loadGif(view, context, R.drawable.auto_shang, handler)

                } else {
                    GlideUtils.loadGif(view, context, R.drawable.auto_xia, handler)
                }
            }
            5 -> {
                if (isUp) {
                    GlideUtils.loadGif(view, context, R.drawable.elevator_up, handler)

                } else {
                    GlideUtils.loadGif(view, context, R.drawable.elevator_down, handler)

                }
            }
        }
    }

    interface DialogDismissListener {
        fun dismiss()
    }


}