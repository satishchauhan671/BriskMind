package com.brisk.assessment.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "sub_question_mst")
class SubQuestionResponse {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var squestion_id: String? = null
    var question: String? = null
    var question_marks: String? = null
    var option_1: String? = null
    var option_2: String? = null
    var option_3: String? = null
    var option_4: String? = null
    var option_5: String? = null
    var option_6: String? = null
    var option_7: String? = null
    var option_8: String? = null
    var option_9: String? = null
    var option_10: String? = null
    var seq_no: String? = null
    var option_count: String? = null
    var q_image: String? = null
    var option_1_image: String? = null
    var option_2_image: String? = null
    var option_3_image: String? = null
    var option_4_image: String? = null
    var option_5_image: String? = null
    var option_6_image: String? = null
    var option_7_image: String? = null
    var option_8_image: String? = null
    var option_9_image: String? = null
    var option_10_image: String? = null

    constructor()
    constructor(
        id: Int,
        squestion_id: String?,
        question: String?,
        question_marks: String?,
        option_1: String?,
        option_2: String?,
        option_3: String?,
        option_4: String?,
        option_5: String?,
        option_6: String?,
        option_7: String?,
        option_8: String?,
        option_9: String?,
        option_10: String?,
        seq_no: String?,
        option_count: String?,
        q_image: String?,
        option_1_image: String?,
        option_2_image: String?,
        option_3_image: String?,
        option_4_image: String?,
        option_5_image: String?,
        option_6_image: String?,
        option_7_image: String?,
        option_8_image: String?,
        option_9_image: String?,
        option_10_image: String?
    ) {
        this.id = id
        this.squestion_id = squestion_id
        this.question = question
        this.question_marks = question_marks
        this.option_1 = option_1
        this.option_2 = option_2
        this.option_3 = option_3
        this.option_4 = option_4
        this.option_5 = option_5
        this.option_6 = option_6
        this.option_7 = option_7
        this.option_8 = option_8
        this.option_9 = option_9
        this.option_10 = option_10
        this.seq_no = seq_no
        this.option_count = option_count
        this.q_image = q_image
        this.option_1_image = option_1_image
        this.option_2_image = option_2_image
        this.option_3_image = option_3_image
        this.option_4_image = option_4_image
        this.option_5_image = option_5_image
        this.option_6_image = option_6_image
        this.option_7_image = option_7_image
        this.option_8_image = option_8_image
        this.option_9_image = option_9_image
        this.option_10_image = option_10_image
    }
}