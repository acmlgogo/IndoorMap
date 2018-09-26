package com.shine.indoormap.view.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class WayBackAdapter constructor(var context: Context, var list: ArrayList<Bitmap>) : PagerAdapter() {
    var imageViewList: ArrayList<ImageView>? = null

    init {
        imageViewList = ArrayList<ImageView>()
        list.forEach {
            var imageView = ImageView(context)
            Glide.with(context).load(it).into(imageView)
            imageViewList!!.add(imageView)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(imageViewList!!.get(position))
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(imageViewList!!.get(position))
        return imageViewList!!.get(position)
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun getCount(): Int {
        return list.size
    }

}