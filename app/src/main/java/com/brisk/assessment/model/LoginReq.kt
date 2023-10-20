package com.brisk.assessment.model

data class LoginReq(
    val device_id: String,
    var password: String,
    var userid: String,
    var appVersion: String,
    var app_type: String,
    val login_type: String
)