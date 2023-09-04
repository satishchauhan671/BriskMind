package com.brisk.assessment.model

data class LoginRes(
    val batch_id: String,
    val client_id: String,
    val languages: List<LanguageRes>,
    val login_app_type: String,
    val login_type: String,
    val message: String,
    val server_ip: String,
    val status: String,
    val user_id: String,
    val user_name: String
)