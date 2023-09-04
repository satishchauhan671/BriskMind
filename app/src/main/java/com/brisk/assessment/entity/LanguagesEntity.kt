package com.brisk.assessment.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
class LanguagesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int, // You may need an ID field for database operations
    val languageId: String,
    val label: String,
    val userEntityId: Long // Foreign key to link to JsonResponseEntity
)