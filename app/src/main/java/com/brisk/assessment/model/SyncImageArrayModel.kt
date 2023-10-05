package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "batch_mst")
class SyncImageArrayModel {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var captured_in: String = ""
    var capture_time: String? = null
    var image_url: String? = null
    var lat: String = ""
    var long: String? = null
    var user_id: String? = null

    constructor()

    constructor(
        id: Int,
        batch_id: String?,
        captured_in: String,
        capture_time: String?,
        image_url: String?,
        lat: String,
        long: String?,
        user_id: String?
    ) {
        this.id = id
        this.batch_id = batch_id
        this.captured_in = captured_in
        this.capture_time = capture_time
        this.image_url = image_url
        this.lat = lat
        this.long = long
        this.user_id = user_id
    }


}