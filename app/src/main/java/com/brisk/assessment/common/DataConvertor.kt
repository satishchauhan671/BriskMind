package com.brisk.assessment.common

import androidx.room.TypeConverter
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.ImportAssessmentReq
import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.OptionRes
import com.brisk.assessment.model.SubQuestionResponse
import com.brisk.assessment.model.TransOptionRes
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

        @TypeConverter
        fun fromImportBatchDataString(value: String) : List<BatchConfigRes>{
            val typeList = object : TypeToken<List<BatchConfigRes>>(){}.type
            return Gson().fromJson(value,typeList)
        }

        @TypeConverter
        fun fromImportBatchList(list: List<BatchConfigRes>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        fun fromSubQuestionDataString(value: String) : List<SubQuestionResponse>{
            val typeList = object : TypeToken<List<SubQuestionResponse>>(){}.type
            return Gson().fromJson(value,typeList)
        }

        @TypeConverter
        fun fromSubQuestionList(list: List<SubQuestionResponse>): String {
            val gson = Gson()
            return gson.toJson(list)
        }


        @TypeConverter
        fun fromOptionDataString(value: String) : List<OptionRes>{
            val typeList = object : TypeToken<List<OptionRes>>(){}.type
            return Gson().fromJson(value,typeList)
        }

        @TypeConverter
        fun fromOptionList(list: List<OptionRes>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        fun fromTransOptionDataString(value: String) : List<TransOptionRes>{
            val typeList = object : TypeToken<List<TransOptionRes>>(){}.type
            return Gson().fromJson(value,typeList)
        }

        @TypeConverter
        fun fromTransOptionList(list: List<TransOptionRes>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

    }

    @TypeConverter
    fun getMedia(longId: String?): LanguageRes? {
        return if (longId == null) null else LanguageRes()
    }


}