package com.shine.indoormap.rx

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler



class RxThread constructor( val mainSchedulers: Scheduler,val ioScheduler: Scheduler) {

    fun <T> applyAsync(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(ioScheduler)
                    .observeOn(mainSchedulers)
        }
    }
}