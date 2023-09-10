package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "paper_mst")
class PaperResponse {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var paper_set_id: String? = null
    var question_id: String? = null
    var question: String? = null
    var question_marks: String? = null
    var q_image: String? = null
    var sub_question_count: String? = null
    @Embedded(prefix = "sub_questions_mst")
    @Ignore var sub_questions: List<SubQuestionResponse>? = null

    constructor(
        paper_set_id: String?,
        question_id: String?,
        question: String?,
        question_marks: String?,
        q_image: String?,
        sub_question_count: String?,
        sub_questions: List<SubQuestionResponse>?
    ) {
        this.paper_set_id = paper_set_id
        this.question_id = question_id
        this.question = question
        this.question_marks = question_marks
        this.q_image = q_image
        this.sub_question_count = sub_question_count
        this.sub_questions = sub_questions
    }

    constructor()


}