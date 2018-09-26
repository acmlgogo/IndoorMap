package com.shine.indoormap.ulits

import com.shine.indoormap.view.fragment.BaseFragment
import android.graphics.Bitmap
import android.graphics.Canvas
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View


class WindowUtils {
    companion object {
        /**
         * 截屏
         * */
        fun screenCapture(fragment: BaseFragment): Bitmap {
            val cv = fragment.activity!!.getWindow().getDecorView()
            cv.setDrawingCacheEnabled(true)
            cv.buildDrawingCache()
            val bmp = cv.getDrawingCache()
            bmp.setHasAlpha(false)
            bmp.prepareToDraw()
            return bmp
        }
        fun loadBitmapFromView(v: View?): Bitmap {
            val screenshot: Bitmap
            screenshot = Bitmap.createBitmap(v!!.getWidth(), v!!.getHeight(), Bitmap.Config.RGB_565)
            val c = Canvas(screenshot)
            c.translate(-v!!.getScrollX().toFloat(), -v!!.getScrollY().toFloat())
            v!!.draw(c)
            return screenshot
        }
    }


}