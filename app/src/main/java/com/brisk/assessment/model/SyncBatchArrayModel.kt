package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "batch_mst")
class SyncBatchArrayModel {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var device_id: String = ""
    var exported_by: String? = null
    var exported_user_type: String? = null
    var export_time: String = ""
    var export_type: String? = null

    constructor()

    constructor(
        id: Int,
        batch_id: String?,
        device_id: String,
        exported_by: String?,
        exported_user_type: String?,
        export_time: String,
        export_type: String?
    ) {
        this.id = id
        this.batch_id = batch_id
        this.device_id = device_id
        this.exported_by = exported_by
        this.exported_user_type = exported_user_type
        this.export_time = export_time
        this.export_type = export_type
    }


}