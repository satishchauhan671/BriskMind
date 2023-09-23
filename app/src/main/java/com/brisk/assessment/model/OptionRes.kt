package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "option_mst")
class OptionRes {
    @PrimaryKey(autoGenerate = true)
    var optionId: Int = 0
    var id: String = ""
    var option: String = ""
    var option_image: String = ""
    var subQuestionId: String = ""
}