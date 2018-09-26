package com.shine.indoormap.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler

abstract class BaseDialog(context: Context?) : Dialog(context) {
//    init {
//        var cureeentTie:Int
//        var handler=Handler()
//
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    abstract  fun initdata()
}