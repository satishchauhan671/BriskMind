package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trans_option_mst")
class TransOptionRes {
    @PrimaryKey(autoGenerate = true)
    var transOptionId: Int = 0
    var id: String = ""
    var option: String = ""
    var option_image: String = ""
    var subQuestionId: String = ""
}