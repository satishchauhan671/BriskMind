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
    var trans_question: String? = null
    var option_count: String? = null
    var q_image: String? = null
    @Embedded(prefix = "trans_option_mst")
    @Ignore var trans_option_array: List<TransOptionRes>? = null

}