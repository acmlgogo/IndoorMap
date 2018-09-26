package com.shine.indoormap.ulits

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import com.shine.indoormap.R
import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.WayData
import android.graphics.Bitmap
import android.graphics.PixelFormat


class CanvasUtils {

    companion object {
        fun CoordinatesToDraw(context: Context, view: ImageView, hashMap: HashMap<String, String>, parent: View) {
            val createBitmap = Bitmap.createBitmap(parent.width, parent.height, Bitmap.Config.ARGB_8888)
            var canvas = Canvas(createBitmap)
            val decodeResource = BitmapFactory.decodeResource(context.resources, R.drawable.gps_location)
            val drawable = context.resources.getDrawable(R.drawable.gps_location)
            val p = Paint()
            p.color = -0x10000//设置画笔颜色
            p.isAntiAlias = true//抗锯齿
            hashMap.forEach {
                Logger.d("坐标  ${it.key.toFloat()}  ${it.value.toFloat()}")
                canvas.drawBitmap(decodeResource, it.key.toFloat() - drawable.intrinsicWidth / 2, it.value.toFloat() - drawable.intrinsicHeight, p)
            }
            view.setImageBitmap(createBitmap)
        }

        /**
         * 基础线路绘制
         * */
        fun canvsWay(data: WayData, parent: View) {
            val wayData = data.result_data.wayData
            val createBitmap = Bitmap.createBitmap(parent.width, parent.height, Bitmap.Config.ARGB_8888)
            var canvas = Canvas(createBitmap)
            val paint = Paint()
            paint.setStrokeJoin(Paint.Join.ROUND)
            paint.style = Paint.Style.STROKE
            paint.color = Color.RED
            paint.isAntiAlias = true
            paint.strokeWidth = 5f
            var path = Path()
            var widthProportion = Constant.AREA!!.x.toFloat() / parent.width.toFloat()
            var heightProportion = Constant.AREA!!.y.toFloat() / parent.height.toFloat()
            //首先原点位子
            val originPoint = wayData.get(0).get(0)
            path.moveTo((originPoint.x.toFloat() + 10) * widthProportion, (originPoint.y.toFloat() + 35) * heightProportion)
            //再做划线移动
            wayData.forEach {
                it.forEach {
                    path.lineTo((it.x.toFloat() + 10) * widthProportion, (it.y.toFloat() + 35) * heightProportion)
                }
            }
            canvas.drawPath(path, paint)
            parent as ImageView
            parent.setImageBitmap(createBitmap)
        }

        /**
         *导航图保存
         * */
        fun saveWayImage() {

        }

        /**
         * 夸楼层导航线路绘制
         * */
        fun transRegionalWayCanvs() {

        }


        fun createLoactionButton(context: Context, parentView: FrameLayout, x: Float, y: Float, name: String) {
            var button = Button(context)
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            button.layoutParams = layoutParams
            button.x = x - 20
            button.y = y - 60
            button.setText(name)
            parentView.addView(button)
        }

    }


}