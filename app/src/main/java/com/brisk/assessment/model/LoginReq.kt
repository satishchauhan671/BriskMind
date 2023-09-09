package com.brisk.assessment.model

data class LoginReq(
    val deviceId: String,
    var password: String,
    var userid: String,
    var appVersion: String,
    var appType: String,
    val loginType: String
)