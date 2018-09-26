package com.shine.indoormap.ulits;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.shine.indoormap.R;
import com.shine.indoormap.rx.RxBus;

import java.util.logging.Logger;


public class GlideUtils {


    public static void loadImageSimpleTarget(ImageView view, Context context, String url) {
        SimpleTarget<Bitmap> mSimpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                view.setImageBitmap(resource);
                RxBus.INSTANCE.post(view);
            }
        };
        Glide.with(context).asBitmap().load(url).into(mSimpleTarget);
    }

    public static void loadImage(ImageView view, Context context, String url, SimpleTarget<Bitmap> simpleTarget) {

        Glide.with(context).asBitmap().load(url).into(simpleTarget);
    }

    public static void loadGif(ImageView view, Context context, int src, Handler handler) {
        Glide.with(context)

                .load(src)

                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // 计算动画时长

                        GifDrawable drawable = (GifDrawable) resource;

                        //发送延时消息，通知动画结束
                        handler.sendEmptyMessageDelayed(1,
                                3000);

                        return false;
                    }
                }) //仅仅加载一次gif动画

                .into(view);
    }

    /**
     * 楼道 = 1,
     * 科室 = 2,
     * 楼梯口 = 3,
     * 自动扶梯 = 4,
     * 电梯口 = 5,
     * 终端机 = 6,
     * 楼宇出入口 = 7,
     * 楼层出入口 =8,
     * 楼宇 = 9
     */
    public static void loadWayTypeImage(Context context, ImageView view, Integer type) {
        Integer image = null;
        switch (type) {
            case 1:
                break;
            case 2:
                break;
            case 3:

                break;
            case 4:
                break;
            case 5:

                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;

        }
        Glide.with(context).load(image).into(view);
    }
}
