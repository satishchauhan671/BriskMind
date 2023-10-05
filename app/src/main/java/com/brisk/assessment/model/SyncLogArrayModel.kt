package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "batch_mst")
class SyncLogArrayModel {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var created_at: String = ""
    var lat: String? = null
    var long: String? = null
    var log_text: String = ""
    var paper_set_id: String? = null
    var paper_type_id: String? = null
    var question_id: String? = null
    var squestion_id: String? = null
    var user_id: String? = null

    constructor()

    constructor(
        id: Int,
        batch_id: String?,
        created_at: String,
        lat: String?,
        long: String?,
        log_text: String,
        paper_set_id: String?,
        paper_type_id: String?,
        question_id: String?,
        squestion_id: String?,
        user_id: String?
    ) {
        this.id = id
        this.batch_id = batch_id
        this.created_at = created_at
        this.lat = lat
        this.long = long
        this.log_text = log_text
        this.paper_set_id = paper_set_id
        this.paper_type_id = paper_type_id
        this.question_id = question_id
        this.squestion_id = squestion_id
        this.user_id = user_id
    }


}