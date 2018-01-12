package com.dupleit.kotlin.kotlinapp

/**
 * Created by android on 8/1/18.
 */
data class UserServerData(var userLat: Double = 0.0,var userLang: Double = 0.0, var timeStamp: Long){
    constructor(): this(0.0,0.0,1)
}