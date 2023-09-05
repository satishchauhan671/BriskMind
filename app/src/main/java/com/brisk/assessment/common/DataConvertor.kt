package com.brisk.assessment.common

import androidx.room.TypeConverter
import com.brisk.assessment.model.LanguageRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DataConvertor {

    companion object {
        @TypeConverter

        fun fromLanguageDataString(value: String) : List<LanguageRes>{
            val typeList = object : TypeToken<List<LanguageRes>>(){}.type
            return Gson().fromJson(value,typeList)
        }

        @TypeConverter
        fun fromLanguageList(list: List<LanguageRes>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

    }

    @TypeConverter
    fun getMedia(longId: String?): LanguageRes? {
        return if (longId == null) null else LanguageRes()
    }

}