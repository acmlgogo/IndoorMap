package com.shine.indoormap

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * 作者：程峰 on 2017/6/1
 * 邮箱：cf550272553@live.com
 * github :https://github.com/acmlgogo
 */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
fun <T>Context.activity(context: Context,clazz: Class<T>){
    var intent=Intent(context,clazz)
    context.startActivity(intent)
}