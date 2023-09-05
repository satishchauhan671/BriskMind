package com.brisk.assessment.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
class LanguageRes {
    var label: String? = null
    @PrimaryKey
    @ColumnInfo(name = "language_id")
    var language_id: String = ""
    @Embedded(prefix = "texts")
     @Ignore var texts: TextsRes? = null

    constructor()

    constructor(label: String?, language_id: String, texts: TextsRes?) {
        this.label = label
        this.language_id = language_id
        this.texts = texts
    }
}