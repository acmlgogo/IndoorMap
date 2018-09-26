package com.shine.indoormap.model

import com.shine.indoormap.presenter.BasePersenter
import com.shine.indoormap.retrofit.IndorrMapApi


abstract class BaseModel {
    abstract fun getPersenter():BasePersenter



}