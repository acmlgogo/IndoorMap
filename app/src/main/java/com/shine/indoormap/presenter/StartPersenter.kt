package com.shine.indoormap.presenter



import com.shine.indoormap.model.InitializeModel
import com.shine.indoormap.view.BaseActivity
import com.shine.indoormap.view.StartActivity


class StartPersenter constructor(var view: BaseActivity) : BasePersenter() {
    init {
        onstart()
    }
    override fun messageAction(dismissdialog: Action, message: String) {
        when (dismissdialog) {
            Action.DISMISSDIALOG -> view.closeDialog()
            Action.SHOWERRORMESSAGE -> {
                view.closeDialog()
                view.showMessage(message)
            }
            Action.NETWORKANOMALY -> {
                view.closeDialog()
                view.networkaBnormalDialog(message)
            }

        }
    }
    fun initialize() {
        view.loadDialog("初始化数据中.....")
        val mainModel = baseModel as InitializeModel
        mainModel.initialize()
    }

    override fun initModel() {
        baseModel = InitializeModel(this)
    }

    override fun onstart() {
        initModel()
    }

    override fun onDestroy() {

    }
    override fun onUiResponse() {
        val startActivity = view as StartActivity
        startActivity.initializeComplete()
    }

}