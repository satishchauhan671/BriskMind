package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "import_language_mst")
class ImportLanguageResponse {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var language_id: String? = null
    var language_name: String? = null
    var theory_instructions: String? = null
    var viva_instructions: String? = null
    var practical_instructions: String? = null


    constructor()

    constructor(
        id: Int,
        language_id: String?,
        language_name: String?,
        theory_instructions: String?,
        viva_instructions: String?,
        practical_instructions: String?
    ) {
        this.id = id
        this.language_id = language_id
        this.language_name = language_name
        this.theory_instructions = theory_instructions
        this.viva_instructions = viva_instructions
        this.practical_instructions = practical_instructions
    }


}