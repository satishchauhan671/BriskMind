package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "login_mst")
class LoginRes {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var client_id: String? = null
    @Embedded(prefix = "languages")
    @Ignore var languages: List<LanguageRes>? = null
    var login_app_type: String? = null
    var login_type: String? = null
    var message: String? = null
    var server_ip: String? = null
    var status: String? = null
    var user_id: String? = null
    var user_name: String? = null

    constructor(
        batch_id: String?,
        client_id: String?,
        languages: List<LanguageRes>?,
        login_app_type: String?,
        login_type: String?,
        message: String?,
        server_ip: String?,
        status: String?,
        user_id: String?,
        user_name: String?
    ) {
        this.batch_id = batch_id
        this.client_id = client_id
        this.languages = languages
        this.login_app_type = login_app_type
        this.login_type = login_type
        this.message = message
        this.server_ip = server_ip
        this.status = status
        this.user_id = user_id
        this.user_name = user_name
    }

    constructor()


}