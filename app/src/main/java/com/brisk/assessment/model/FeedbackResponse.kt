package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedback_mst")
class FeedbackResponse {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var fq_id: String? = null
    var fq_text: String? = null
    var fq_type: String? = null
    var user_type: String? = null
    var option1: String? = null
    var option2: String? = null
    var option3: String? = null
    var option4: String? = null


    constructor()

    constructor(
        id: Int,
        fq_id: String?,
        fq_text: String?,
        fq_type: String?,
        user_type: String?,
        option1: String?,
        option2: String?,
        option3: String?,
        option4: String?
    ) {
        this.id = id
        this.fq_id = fq_id
        this.fq_text = fq_text
        this.fq_type = fq_type
        this.user_type = user_type
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.option4 = option4
    }


}