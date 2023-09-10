package com.brisk.assessment.model

class ImportAssessmentResponse {
    var batch_array: List<BatchRes>? = null
    var user_array: List<UserResponse>? = null
    var lang_record: List<ImportLanguageResponse>? = null
    var feedback_array: List<FeedbackResponse>? = null
    var paper_array: List<Any>? = null
}