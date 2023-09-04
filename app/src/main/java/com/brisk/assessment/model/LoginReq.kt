package com.brisk.assessment.model

data class LoginReq(
    val device_id: String,
    val password: String,
    val userid: String,
    val appVersion: String,
    val app_type: String,
)