package com.dupleit.kotlin.kotlinapp.Register.model

/**
 * Created by android on 8/12/17.
 */

class userProfileModel {

    var userName: String? = null
    var userMobile: Long? = null
    var userEmail: String? = null
    var userAuthId: String? = null

    constructor(userName: String, userMobile: Long, userEmail: String, userAuthId: String) {
        this.userName = userName
        this.userMobile = userMobile
        this.userEmail = userEmail
        this.userAuthId = userAuthId
    }


}
