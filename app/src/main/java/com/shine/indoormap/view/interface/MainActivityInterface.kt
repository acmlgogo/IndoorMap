package com.shine.indoormap.view.`interface`

interface MainActivityInterface:BaseViewIntface {
    //加载天气信息
    fun loadWeatherInfo()
    //全局楼宇导航 （返回首页）
    fun overallNavigation()
    // 加载科室的信息
    fun loadDepartmentInfo()
    // 加载医生信息
    fun loadDoctorInfo()
    // 拼音搜索
    fun startSearch()
    // 显示楼栋信息
    fun showFloorInfo(floor_id:String)
    //加载当前位子
    fun loadCurrentLoaciton()
    //加载公共设施
    fun initCommunalFacilities()




}