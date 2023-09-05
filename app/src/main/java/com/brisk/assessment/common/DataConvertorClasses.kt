package com.brisk.assessment.common

import androidx.room.TypeConverter
import com.brisk.assessment.model.LanguageRes

class DataConvertorClasses {
    @TypeConverter
    fun getMedia(longId: String?): LanguageRes? {
        return if (longId == null) null else LanguageRes()
    }
}