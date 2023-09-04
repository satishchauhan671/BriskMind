package com.brisk.assessment.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "texts")
class TextsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int, // You may need an ID field for database operations
    val login: String,
    val submit: String,
    val clear: String,
    val startTheory: String,
    val logout: String,
    val notNow: String,
    val instruction: String,
    val textInstruction: String,
    val next: String,
    val languageId: Long // Foreign key to link to LanguageEntity
)