package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "batch_mst")
class BatchRes {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var assessor_name: String? = null
    var batch_id: String = ""
    var sector_id: String? = null
    var sector_name: String? = null
    var batch_no: String = ""
    var assessment_date: String? = null
    var duration: String? = null
    var client_id: String? = null
    var alt_lang_id: String? = null
    var s3_secret_key: String? = ""
    var s3_secret_pwd: String? = ""
    var s3_group_path: String? = ""
    var total_practical_marks: String? = ""
    var total_theory_marks: String? = ""
    var total_viva_marks: String? = ""

    @Embedded(prefix = "batch_config_mst")
    @Ignore
    var batch_config: BatchConfigRes? = null


    constructor()
    constructor(
        id: Int,
        assessor_name: String?,
        batch_id: String,
        sector_id: String?,
        sector_name: String?,
        batch_no: String,
        assessment_date: String?,
        duration: String?,
        client_id: String?,
        alt_lang_id: String?,
        s3_secret_key: String?,
        s3_secret_pwd: String?,
        s3_group_path: String?,
        total_practical_marks: String?,
        total_theory_marks: String?,
        total_viva_marks: String?,
        batch_config: BatchConfigRes?
    ) {
        this.id = id
        this.assessor_name = assessor_name
        this.batch_id = batch_id
        this.sector_id = sector_id
        this.sector_name = sector_name
        this.batch_no = batch_no
        this.assessment_date = assessment_date
        this.duration = duration
        this.client_id = client_id
        this.alt_lang_id = alt_lang_id
        this.s3_secret_key = s3_secret_key
        this.s3_secret_pwd = s3_secret_pwd
        this.s3_group_path = s3_group_path
        this.total_practical_marks = total_practical_marks
        this.total_theory_marks = total_theory_marks
        this.total_viva_marks = total_viva_marks
        this.batch_config = batch_config
    }

    override fun toString(): String {
        return "BatchRes(id=$id, assessor_name=$assessor_name, batch_id='$batch_id', sector_id=$sector_id, sector_name=$sector_name, batch_no='$batch_no', assessment_date=$assessment_date, duration=$duration, client_id=$client_id, alt_lang_id=$alt_lang_id, s3_secret_key=$s3_secret_key, s3_secret_pwd=$s3_secret_pwd, s3_group_path=$s3_group_path, total_practical_marks=$total_practical_marks, total_theory_marks=$total_theory_marks, total_viva_marks=$total_viva_marks, batch_config=$batch_config)"
    }


}