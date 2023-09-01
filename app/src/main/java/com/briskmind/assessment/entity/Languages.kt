package com.briskmind.assessment.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Languages {

    @ColumnInfo(name = "language_id")
    var languageId : String? = null

    @ColumnInfo(name = "label")
    var label : String? = null

    @Embedded(prefix = "textsRes") // We need relation to Media table
    @Expose
    @SerializedName("textsRes")
    var languages: Texts? = null
}