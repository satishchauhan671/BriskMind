package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

class SyncUserProfile {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var aadhar_no: String? = null
    var batch_id: String = ""
    var email_d: String? = null
    var mobile_no: String? = null
    var user_id: String = ""
    var user_name: String? = null
    var user_type: String? = null


}