package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "batch_mst")
class SyncUserProfileModel {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var aadhar_no: String? = null
    var batch_id: String = ""
    var email_d: String? = null
    var mobile_no: String? = null
    var user_id: String = ""
    var user_name: String? = null
    var user_type: String? = null

    constructor()

    constructor(
        id: Int,
        aadhar_no: String?,
        batch_id: String,
        email_d: String?,
        mobile_no: String?,
        user_id: String,
        user_name: String?,
        user_type: String?
    ) {
        this.id = id
        this.aadhar_no = aadhar_no
        this.batch_id = batch_id
        this.email_d = email_d
        this.mobile_no = mobile_no
        this.user_id = user_id
        this.user_name = user_name
        this.user_type = user_type
    }


}