package com.shine.indoormap.view.`interface`

/**
 *
 *  v  层公共接口
 *     四个功能按键的功能接口 天气接口
 *
 * */
interface BaseViewIntface {


    //进入页面view模块动画生成
    fun onCreateViewAnimation()

    //加载dialog
    fun loadDialog(message: String)

    //结束加载dialog
    fun closeDialog()

    fun networkaBnormalDialog(message: String)

    fun showMessage(message:String)
}