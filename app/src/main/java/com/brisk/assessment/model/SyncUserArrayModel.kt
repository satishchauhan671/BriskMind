package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "batch_mst")
class SyncUserArrayModel {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var attendance_status: String? = null
    var batch_id: String = ""
    var consumed_time: String? = null
    var paper_set_id: String? = null
    var paper_type_id: String = ""
    var status: String? = null
    var user_end_time: String? = null
    var user_id: String? = null
    var user_start_time: String? = null
    var video_url: String? = null

    constructor()

    constructor(
        id: Int,
        attendance_status: String?,
        batch_id: String,
        consumed_time: String?,
        paper_set_id: String?,
        paper_type_id: String,
        status: String?,
        user_end_time: String?,
        user_id: String?,
        user_start_time: String?,
        video_url: String?
    ) {
        this.id = id
        this.attendance_status = attendance_status
        this.batch_id = batch_id
        this.consumed_time = consumed_time
        this.paper_set_id = paper_set_id
        this.paper_type_id = paper_type_id
        this.status = status
        this.user_end_time = user_end_time
        this.user_id = user_id
        this.user_start_time = user_start_time
        this.video_url = video_url
    }


}