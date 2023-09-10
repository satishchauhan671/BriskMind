package com.brisk.assessment.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user_data_mst")
class UserResponse {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var user_type: String? = null
    var login_id: String? = null
    var login_pass: String? = null
    var user_id: String? = null
    var user_name: String? = null
    var father_name: String? = null
    var mobile_no: String? = null
    var jobrole: String? = null
    var theory_paper_set_id: String? = null
    var viva_paper_set_id: String? = null
    var practical_paper_set_id: String? = null
    var alt_lang_id: String? = null
    var client_id: String? = null

    constructor()

    constructor(
        id: Int,
        batch_id: String?,
        user_type: String?,
        login_id: String?,
        login_pass: String?,
        user_id: String?,
        user_name: String?,
        father_name: String?,
        mobile_no: String?,
        jobrole: String?,
        theory_paper_set_id: String?,
        viva_paper_set_id: String?,
        practical_paper_set_id: String?,
        alt_lang_id: String?,
        client_id: String?
    ) {
        this.id = id
        this.batch_id = batch_id
        this.user_type = user_type
        this.login_id = login_id
        this.login_pass = login_pass
        this.user_id = user_id
        this.user_name = user_name
        this.father_name = father_name
        this.mobile_no = mobile_no
        this.jobrole = jobrole
        this.theory_paper_set_id = theory_paper_set_id
        this.viva_paper_set_id = viva_paper_set_id
        this.practical_paper_set_id = practical_paper_set_id
        this.alt_lang_id = alt_lang_id
        this.client_id = client_id
    }


}