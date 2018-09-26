package com.shine.indoormap.ulits

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.shine.indoormap.base.data.MaterialInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.concurrent.TimeUnit

class ScreensaverUlits {
    var subscribe: Disposable? = null
    fun playLogic(context: Context, index: Int, materialInfo: MaterialInfo, videoView: VideoView, imageView: ImageView) {
        val size = materialInfo.sources.size
        val sources = materialInfo.getSources()
        val sourcesEntity = sources.get(index)
        if (sourcesEntity.getName().indexOf("mp4") > -1) {
            videoView.setVisibility(View.VISIBLE)
            videoView.setVideoPath(sourcesEntity.path)
            videoView.start()
            videoView.setOnCompletionListener({
                videoView.setVisibility(View.GONE)
                if (index==size-1) {
                    playLogic(context, index -(size-1), materialInfo, videoView, imageView)
                }else{
                    playLogic(context, index + 1, materialInfo, videoView, imageView)
                }



            })
        } else {
            imageView.setVisibility(View.VISIBLE)
            Glide.with(context).load(sourcesEntity.path).into(imageView)
            val observable = Observable.interval(1, TimeUnit.SECONDS)
            subscribe = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it == sourcesEntity.getTime().toLong()) {
                            if (index==size-1) {
                                playLogic(context, index -(size-1), materialInfo, videoView, imageView)
                            }else{
                                playLogic(context, index + 1, materialInfo, videoView, imageView)
                            }
                            subscribe!!.dispose()
                        }
                    }

        }


    }
}