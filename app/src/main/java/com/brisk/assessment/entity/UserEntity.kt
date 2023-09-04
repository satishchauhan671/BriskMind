package com.brisk.assessment.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int, // You may need an ID field for database operations
    val status: String,
    val loginType: String,
    val userId: String,
    val loginAppType: String,
    @ColumnInfo(name = "batch_id")
    val batchId: String,
    val serverIp: String,
    val clientId: String,
    val userName: String,
    val message: String
)