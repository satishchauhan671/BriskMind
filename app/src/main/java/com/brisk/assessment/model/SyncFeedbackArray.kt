package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_feedback_arr")
class SyncFeedbackArray {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var created_at: String? = null
    var feedback_q_id: String? = null
    var response: String? = null
    var user_id: String? = null
    var user_type: String? = null

    constructor()

    constructor(
        id: Int,
        batch_id: String?,
        created_at: String,
        feedback_q_id: String?,
        response: String?,
        user_id: String,
        user_type: String?
    ) {
        this.id = id
        this.batch_id = batch_id
        this.created_at = created_at
        this.feedback_q_id = feedback_q_id
        this.response = response
        this.user_id = user_id
        this.user_type = user_type
    }


}