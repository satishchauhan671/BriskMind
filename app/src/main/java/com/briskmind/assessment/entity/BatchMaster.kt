package com.briskmind.assessment.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BatchMst")
class BatchMaster {
    @PrimaryKey
    val id = Int


}