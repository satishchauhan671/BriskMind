package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "batch_config_mst")
class BatchConfigRes {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var batch_id: String? = null
    var img_capture_time: String? = null
    var option_randomize: String? = null
    var u_attendance_before_theory: String? = null
    var u_attendance_after_theory: String? = null
    var u_attendance_before_viva: String? = null
    var u_attendance_after_viva: String? = null
    var aadhar_front: String? = null
    var aadhar_back: String? = null
    var profile_pic: String? = ""
    var assessor_attendance_before: String? = ""
    var assessor_attendance_after: String? = ""
    var mark_for_review: String? = ""
    var video_in_viva: String? = ""
    var candidate_profile: String? = ""
    var assessor_profile: String? = ""
    var candidate_feedback: String? = ""
    var assessor_feedback: String? = ""
    var attempt_all: Int = 0

    constructor()

    @Ignore
    constructor(
        id: Int,
        batch_id: String?,
        img_capture_time: String?,
        option_randomize: String?,
        u_attendance_before_theory: String?,
        u_attendance_after_theory: String?,
        u_attendance_before_viva: String?,
        u_attendance_after_viva: String?,
        aadhar_front: String?,
        aadhar_back: String?,
        profile_pic: String?,
        assessor_attendance_before: String?,
        assessor_attendance_after: String?,
        mark_for_review: String?,
        video_in_viva: String?,
        candidate_profile: String?,
        assessor_profile: String?,
        candidate_feedback: String?,
        assessor_feedback: String?,
        attempt_all: Int
    ) {
        this.id = id
        this.batch_id = batch_id
        this.img_capture_time = img_capture_time
        this.option_randomize = option_randomize
        this.u_attendance_before_theory = u_attendance_before_theory
        this.u_attendance_after_theory = u_attendance_after_theory
        this.u_attendance_before_viva = u_attendance_before_viva
        this.u_attendance_after_viva = u_attendance_after_viva
        this.aadhar_front = aadhar_front
        this.aadhar_back = aadhar_back
        this.profile_pic = profile_pic
        this.assessor_attendance_before = assessor_attendance_before
        this.assessor_attendance_after = assessor_attendance_after
        this.mark_for_review = mark_for_review
        this.video_in_viva = video_in_viva
        this.candidate_profile = candidate_profile
        this.assessor_profile = assessor_profile
        this.candidate_feedback = candidate_feedback
        this.assessor_feedback = assessor_feedback
        this.attempt_all = attempt_all
    }
}