package com.briskmind.assessment.model

class LoginResponse {
    var status: String? = null
    var login_type: String? = null
    var user_id: String? = null
    var login_app_type: String? = null
    var batch_id: String? = null
    var server_ip: String? = null
    var client_id: String? = null
    var user_name: String? = null
    var message: String? = null
    var languages : List<LanguageRes>? = null
}