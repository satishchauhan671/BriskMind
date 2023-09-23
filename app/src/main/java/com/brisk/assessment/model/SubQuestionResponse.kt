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
    var question_id: String? = null
    var squestion_id: String? = null
    var question: String? = null
    var question_marks: String? = null
    @Embedded(prefix = "option_mst")
    @Ignore var option_array: List<OptionRes>? = null
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
    var trans_question: String? = null
    @Embedded(prefix = "trans_option_mst")
    @Ignore var trans_option_array: List<TransOptionRes>? = null

    constructor()
    constructor(
        id: Int,
        question_id: String?,
        squestion_id: String?,
        question: String?,
        question_marks: String?,
        option_array: List<OptionRes>?,
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
        option_10_image: String?,
        trans_question: String?,
        trans_option_array: List<TransOptionRes>?,
    ) {
        this.id = id
        this.question_id = question_id
        this.squestion_id = squestion_id
        this.question = question
        this.question_marks = question_marks
        this.option_array = option_array
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
        this.trans_question = trans_question
        this.trans_option_array = trans_option_array
    }

}