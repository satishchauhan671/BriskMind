package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey


class SyncUserAttendance {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var entry_id: String = ""
    var entry_id_time: String? = null
    var entry_photo: String? = null
    var entry_photo_time: String ? = null
    var exit_id: String? = null
    var exit_id_time: String? = null
    var exit_photo: String? = null
    var exit_photo_time: String? = null
    var user_id: String? = null

}