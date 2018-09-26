package com.shine.indoormap.retrofit


import com.shine.indoormap.base.data.*
import com.shine.indoormap.base.data.initalize.Initalize
import io.reactivex.Observable
import okhttp3.internal.Internal
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


// 室内导航所有的 网络接口
interface IndorrMapApi {
    @GET("Terminal/Initalize")
    fun initalize(@Query("IP") ip: String, @Query("MAC") mac: String): Observable<Initalize>

    @GET("Floor/FloorList")
    fun floorList(@Query("Items_ID") items_id: String, @Query("Area_ID") area_id: String, @Query("BuilDing_ID") building_id: String): Observable<FloorList>

    @GET("Classifying/ClassifyingList")
    fun classifyingList(@Query("Items_ID") items_id: String): Observable<ClassifyingList>

    @GET("Classifying/Classifying_QueueList")
    fun classifyingQueueList(@Query("Items_ID") items_id: String, @Query("Classifying_ID") classifying_id: String): Observable<QueueList>

    @GET("Doctor/Classifying_DoctorList")
    fun doctorList(@Query("Items_ID") items_id: String, @Query("Classifying_ID") classifying_id: String): Observable<Doctor>

    @GET("Seek/SeekList")
    fun seekList(@Query("Items_ID") items_id: String, @Query("Area_ID") area_id: String, @Query("Seek_condition") seek_condition: String): Observable<SeekList>

    @GET("MapWay/GetWayPath")
    fun getWayPath( @Query("TermMarkId") termmarkid: String, @Query("TargetId") targetid: String, @Query("MarkTypeOne") marktypeone: String = "0", @Query("MarkTypeTwo") marktypetwo: String = "0",@Query("Type")type:String="Floor"): Observable<WayData>

    @GET("MapWay/GetFacilitiesInfo")
    fun getFacilitiesInfo(@Query("Type") type: String, @Query("Id") id: String, @Query("Coordinate_Type") coordinate_type: String):Observable<Facilities>

    @GET("Floor/GetOneFloorQueueInfo")
    fun getCurrentFloorQueueList(@Query("Floor_ID")floor_id:String):Observable<CurrentFloorQueueInfo>

    @GET("MapWay/GetWayPathByStoreQueueId")
    fun getWayPathByScanResult(@Query("TermMarkId")termmarkid: String,@Query("StoreQueueId")storeQueuid:String,@Query("MarkTypeOne") marktypeone: String = "0", @Query("MarkTypeTwo") marktypetwo: String = "0"):Observable<WayData>

}