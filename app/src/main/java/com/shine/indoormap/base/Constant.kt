package com.shine.indoormap.base

import android.graphics.Bitmap
import com.shine.indoormap.base.data.Facilities
import com.shine.indoormap.base.data.FloorList
import com.shine.indoormap.base.data.WayData
import com.shine.indoormap.base.data.initalize.Initalize

object Constant {

    var BASE_URL = "http://10.0.0.91:8080/api/"
    var RESULT: Initalize.resultDataEntity? = null
    var CURRENT: Initalize.resultDataEntity.CurrentEntity? = null
    var ITEMS: Initalize.resultDataEntity.ItemsEntity? = null
    var TERMINAL: Initalize.resultDataEntity.TerminalEntity? = null
    var AREA: Initalize.resultDataEntity.AreaEntity? = null
    var FACILITIES: Facilities? = null

    /*************************** 导航路线回看       ***************************/
    var LOOKBACKIMG = ArrayList<Bitmap>()
    /*****************************两栋大楼上下楼方式 ****************************************/
    var ONETYPE: Int? = null
    var TWOTYPE: Int? = null
    /****************************   终端屏幕参数     ***************************/
    var WIDTH: Int? = null
    var HEIGTH: Int? = null
    /*********************************两栋楼导航的起始和终止点   ******************************************/
    var ONESTARTNAME: String? = null
    var ONEENDNAME: String? = null
    var TWOSTARTNAME: String? = null
    var TWOENDNAME: String? = null
    var ISDEBUG =false
    /*******************************************最新行走路线数据*************************************************/
    var WAYDATA: WayData? = null
    var WAYTYPEARRAY = ArrayList<Int>()

    // var ENTERSCREENSAVERTIME=10
    var ENTERSCREENSAVERTIME = 60 * 5
}