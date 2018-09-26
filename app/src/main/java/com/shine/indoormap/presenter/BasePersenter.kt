package com.shine.indoormap.presenter

import com.shine.indoormap.model.BaseModel


abstract class BasePersenter {

    var baseModel: BaseModel? = null

    abstract fun initModel()

    abstract fun onstart()

    abstract fun messageAction(dismissdialog: Action, message: String = "")

    abstract fun onDestroy()

    abstract fun onUiResponse()

}