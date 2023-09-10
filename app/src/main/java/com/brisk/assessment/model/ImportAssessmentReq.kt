package com.brisk.assessment.model

data class ImportAssessmentReq (
    val userId: String,
    var deviceId: String,
    var appUserType: String,
    var appVersion: String
)
