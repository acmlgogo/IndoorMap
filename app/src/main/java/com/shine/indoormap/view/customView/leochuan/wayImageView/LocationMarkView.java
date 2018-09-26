package com.shine.indoormap.view.customView.leochuan.wayImageView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shine.indoormap.R;

public class LocationMarkView extends View {

    private Paint mPaint;
    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;
    private ValueAnimator mValueAnimator;
    private Float mAnimatedValu;
    private int width;
    private int height;

    public LocationMarkView(Context context) {
        super(context);
    }

    public LocationMarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LocationMarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LocationMarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    /**
     * 实例化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.dialogTextColor));
    }

    /**
     * 实例化属性动画
     */
    private void initanimator() {
        mValueAnimator = ValueAnimator.ofFloat(1f, 0f);
        mValueAnimator.addUpdateListener(mAnimatorUpdateListener);
    }

    /**
     * 实例化动画监听
     */
    private void initAnimatorListeren() {
        mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValu = (float) animation.getAnimatedValue();
            }

        };
    }

    /**
     *
     * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
    private void onDrawLocation(Canvas canvas){
//        canvas.translate(width/2,height/2);
        canvas.drawOval(0,height-5,width,height,mPaint);
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location);
        canvas.drawBitmap(bitmap,0,height*mAnimatedValu,mPaint);

    }
}
