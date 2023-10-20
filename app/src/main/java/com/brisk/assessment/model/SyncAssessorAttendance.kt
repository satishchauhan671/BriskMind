package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_assessor_attendance_arr")
class SyncAssessorAttendance {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var entry_id: String = ""
    var entry_id_time: String? = null
    var entry_photo: String? = null
    var entry_photo_time: String? = null
    var exit_id: String? = null
    var exit_id_time: String? = null
    var exit_photo: String? = null
    var exit_photo_time: String? = null
    var user_id: String? = null

    constructor()

    constructor(
        id: Int,
        batch_id: String?,
        entry_id: String,
        entry_id_time: String?,
        entry_photo: String?,
        entry_photo_time: String,
        exit_id: String?,
        exit_id_time: String?,
        exit_photo: String?,
        exit_photo_time: String?,
        user_id: String?
    ) {
        this.id = id
        this.batch_id = batch_id
        this.entry_id = entry_id
        this.entry_id_time = entry_id_time
        this.entry_photo = entry_photo
        this.entry_photo_time = entry_photo_time
        this.exit_id = exit_id
        this.exit_id_time = exit_id_time
        this.exit_photo = exit_photo
        this.exit_photo_time = exit_photo_time
        this.user_id = user_id
    }


}