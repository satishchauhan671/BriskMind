package com.briskmind.assessment.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "UserMst")
class UserEntity {
    @ColumnInfo(name = "batch_id")
    var batchId: String? = null

    @Expose
    @SerializedName("client_id")
    var clientId: String? = null

    @Expose
    @SerializedName("login_app_type")
    var loginAppType: String? = null

    @Expose
    @SerializedName("login_type")
    var loginType: String? = null

    @Expose
    @SerializedName("server_ip")
    var serverIp: String? = null

    @Expose
    @SerializedName("user_id")
    var userId: String? = null

    @Expose
    @SerializedName("user_name")
    var userName: String? = null

    @Embedded(prefix = "languages") // We need relation to Media table
    @Expose
    @SerializedName("languages")
    var languages: List<Languages>? = null
}