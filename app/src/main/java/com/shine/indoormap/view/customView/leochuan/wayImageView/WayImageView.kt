package com.shine.indoormap.view.customView.leochuan.wayImageView

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewParent
import android.widget.ImageView
import android.widget.ViewAnimator
import com.shine.indoormap.R
import com.shine.indoormap.base.Constant
import com.shine.indoormap.base.data.WayData
import com.shine.indoormap.rx.RxBus
import com.shine.indoormap.ulits.WindowUtils
import com.shine.indoormap.ulits.WindowUtils.Companion.loadBitmapFromView
import java.util.jar.Attributes
import android.R.raw
import android.R.attr.bitmap
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.lang.reflect.Array.getLength
import android.graphics.PathMeasure
//import java.awt.font.ShapeGraphicAttribute.STROKE
import android.os.Build
import android.annotation.TargetApi


class WayImageView : ImageView {

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)

    var data: ArrayList<WayData.ResultDataEntity.WayDataEntity>? = null
    var mViewWidth: Int = 0
    var mViewheight: Int = 0
    var mPaint: Paint? = null
    var mPath: Path? = null
    var mPathMeasure: PathMeasure? = null
    var mDefaultDuration = 5000L
    var mPathAnimator: ValueAnimator? = null
    var mAnimatorValue: Float? = null
    var mAnimatorUpListener: ValueAnimator.AnimatorUpdateListener? = null
    var isStartAnimtor = false
    var mParent: View? = null
    var mWayEndListener: WayEndListener? = null
    var mCurrentLocationName: String? = null
    var mCurrentTerminalX: Int? = null
    var mCurrentTerminalY: Int? = null
    var isShowCurrentTerminalLocation = false
    lateinit var pos: FloatArray
    lateinit var tan: FloatArray
    var mMatrix: Matrix? = null
    var decodeResource: Bitmap? = null
    var mTargetName: String? = null

    init {
        initPaint()
        initAnimatorListener()
        initanimator()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewheight = h

    }

    fun setWayData(data: ArrayList<WayData.ResultDataEntity.WayDataEntity>, currentLocationName: String, targetName: String?, parent: View) {
        this.data = data
        initPath()
        mPathAnimator!!.start()
        isStartAnimtor = true
        mParent = parent
        mCurrentLocationName = currentLocationName
        mTargetName = targetName
    }

    fun setCurrentLocationName(currentLocationName: String) {
        mCurrentLocationName = currentLocationName
    }

    fun setCurrentTerminalLocation(x: Int, y: Int) {
        mCurrentTerminalX = x
        mCurrentTerminalY = y
    }

    fun setWayListener(wayEndListener: WayEndListener) {
        mWayEndListener = wayEndListener
    }

    fun initPaint() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.strokeWidth = 5f
        mPaint!!.setStrokeJoin(Paint.Join.ROUND)
        mPaint!!.strokeCap = Paint.Cap.ROUND
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.color = context.resources.getColor(R.color.colorAccent)
        pos = FloatArray(2)
        tan = FloatArray(2)
        mMatrix = Matrix()
        val options = BitmapFactory.Options()
        options.inSampleSize = 2       // 缩放图片
//        decodeResource = BitmapFactory.decodeResource(context.resources, R.drawable.jt, options)
    }

    fun initPath() {
        mPath = Path()
        for (index in data!!.indices) {
            if (index == 0) {
                mPath!!.moveTo(data!![index].x.toFloat(), data!![index].y.toFloat())
            } else {
                mPath!!.lineTo(data!![index].x.toFloat(), data!![index].y.toFloat())
            }
        }
        mPathMeasure = PathMeasure(mPath!!, false)
    }

    fun initanimator() {
        mPathAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(mDefaultDuration)
        mPathAnimator!!.addUpdateListener(mAnimatorUpListener)
        mPathAnimator!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                mWayEndListener?.wayEnd()
                val loadBitmapFromView = loadBitmapFromView(mParent)
                Constant.LOOKBACKIMG.add(loadBitmapFromView)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }

    fun initAnimatorListener() {
        mAnimatorUpListener = ValueAnimator.AnimatorUpdateListener { animation ->
            mAnimatorValue = animation.animatedValue as Float
            invalidate()
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isStartAnimtor) {
            mPaint!!.strokeWidth = 5f
            mPaint!!.style = Paint.Style.STROKE
            mPathMeasure!!.setPath(mPath, false)
//            val dst = Path()
//            mPathMeasure!!.getSegment(0f, mPathMeasure!!.getLength() * mAnimatorValue!!, dst, true)
            mPaint!!.color = context.resources.getColor(R.color.dialogTextColor)
            canvas!!.drawPath(mPath, mPaint)
            mPaint!!.color = context.resources.getColor(R.color.colorPrimaryDark)
//            canvas!!.drawPath(dst, mPaint)
            val startX = data!!.get(0).x.toFloat()
            val startY = data!!.get(0).y.toFloat()
            val endX = data!!.get(data!!.size - 1).x.toFloat()
            val endY = data!!.get(data!!.size - 1).y.toFloat()
            mPaint!!.style = Paint.Style.FILL
            PaintMatr(canvas!!)
            val bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.start_location)
            val endbitmap = BitmapFactory.decodeResource(resources, R.drawable.gonggong)
            canvas?.drawBitmap(bitmap, startX!!.toFloat() - bitmap.width / 2, startY!!.toFloat() - bitmap.height, mPaint)
            canvas?.drawBitmap(endbitmap, data!!.get(data!!.lastIndex).x.toFloat() - endbitmap.width / 2, data!!.get(data!!.lastIndex).y.toFloat() - endbitmap.height, mPaint)
            canvas.drawCircle(endX, endY, 6f, mPaint)
            canvsLocationName(canvas, mCurrentLocationName)
            canvsTargetLocationName(canvas, mCurrentLocationName, mTargetName)

        } else {
            canvsLocationName(canvas, mCurrentLocationName)
        }
        if (isShowCurrentTerminalLocation) {
            canvsCurrentLocation(canvas)

        }

    }

    fun canvsLocationName(canvas: Canvas?, currentLocationName: String?) {
        if (currentLocationName != null) {
            mPaint!!.strokeWidth = 1f
            mPaint!!.style = Paint.Style.FILL
//          mPaint!!.style = Paint.Style.STROKE
            mPaint!!.color = context.resources.getColor(R.color.location_bg)
            canvas?.drawRoundRect(30f, 5f, currentLocationName.length * 30f + 30f, 50f, 10f, 10f, mPaint)
            mPaint!!.textSize = 30f
            mPaint!!.color = context.resources.getColor(R.color.whiteColor)
            canvas?.drawText(currentLocationName!!, 50f, 40f, mPaint)
        }
    }

    fun canvsTargetLocationName(canvas: Canvas?, currentLocationName: String?, targetName: String?) {
        if (targetName != null) {
            mPaint!!.strokeWidth = 1f
            mPaint!!.style = Paint.Style.FILL
            mPaint!!.color = context.resources.getColor(R.color.location_bg)
            canvas?.drawRoundRect(currentLocationName!!.length * 30f + 35f, 5f, currentLocationName!!.length * 30f + 35f + targetName.length * 30f + 25f, 50f, 10f, 10f, mPaint)
            mPaint!!.textSize = 30f
            mPaint!!.color = context.resources.getColor(R.color.whiteColor)
            canvas?.drawText(targetName!!, currentLocationName!!.length * 30f + 25f + 40f, 40f, mPaint)
        }
    }

    fun canvsCurrentLocation(canvas: Canvas?) {
        mPaint!!.strokeWidth = 1f
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.textSize = 30f
        mPaint!!.color = Color.RED
        val bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location)
        canvas?.drawBitmap(bitmap, mCurrentTerminalX!!.toFloat() - bitmap.width / 2, mCurrentTerminalY!!.toFloat() - bitmap.height, mPaint)


    }



    fun closeWayPath() {
        isStartAnimtor = false
        invalidate()
    }

    interface WayEndListener {
        fun wayEnd()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun PaintMatr(canvas: Canvas) {


        mPaint!!.setStrokeWidth(5f)
        mPaint!!.setStyle(Paint.Style.STROKE)
        val matrix = Matrix()
        mPathMeasure!!.getPosTan(mPathMeasure!!.length * mAnimatorValue!!, pos, tan)
        val path1 = Path()
        path1.moveTo(pos[0] - 15, pos[1] + 15)
        path1.lineTo(pos[0], pos[1])
        path1.lineTo(pos[0] + 15, pos[1] + 15)
        //        是否闭合，闭合就是三角形了
//        path1.close()
        val path2 = Path()
        val degrees = (Math.atan2(tan[1].toDouble(), tan[0].toDouble()) * 180.0 / Math.PI).toFloat()
        matrix.setRotate(degrees + 90, pos[0], pos[1])
        path2.addPath(path1, matrix)
        //        进度线
        mPathMeasure!!.getSegment(0f, mPathMeasure!!.length * mAnimatorValue!!, path2, true)
        mPaint!!.color = context.resources.getColor(R.color.colorPrimaryDark)
        canvas.drawPath(path2, mPaint)
        invalidate()

    }
}