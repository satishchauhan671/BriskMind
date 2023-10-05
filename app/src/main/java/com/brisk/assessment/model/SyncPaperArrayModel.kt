package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "batch_mst")
class SyncPaperArrayModel {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var consumed_time: String = ""
    var paper_set_id: String? = null
    var paper_type_id: String? = null
    var question_id: String = ""
    var squestion_id: String? = null
    var status: String? = null
    var sub_question_seq: String? = null
    var user_ans_id: String? = null
    var user_id: String? = null

    constructor()

    constructor(
        id: Int,
        batch_id: String?,
        consumed_time: String,
        paper_set_id: String?,
        paper_type_id: String?,
        question_id: String,
        squestion_id: String?,
        status: String?,
        sub_question_seq: String?,
        user_ans_id: String?,
        user_id: String?
    ) {
        this.id = id
        this.batch_id = batch_id
        this.consumed_time = consumed_time
        this.paper_set_id = paper_set_id
        this.paper_type_id = paper_type_id
        this.question_id = question_id
        this.squestion_id = squestion_id
        this.status = status
        this.sub_question_seq = sub_question_seq
        this.user_ans_id = user_ans_id
        this.user_id = user_id
    }


}