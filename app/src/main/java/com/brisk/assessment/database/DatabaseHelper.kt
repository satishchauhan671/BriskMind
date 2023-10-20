package com.brisk.assessment.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper private constructor(context: Context) :

    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "briskMind.db"
        const val DB_VERSION = 1
        private var databaseHelper: DatabaseHelper? = null
        val lock = Any()

        // User Data Table
        const val login_mst = "login_mst"
        const val languages_mst = "languages_mst"
        const val languages_text_mst = "languages_text_mst"
        const val batch_mst = "batch_mst"
        const val batch_config_mst = "batch_config_mst"
        const val user_data_mst = "user_data_mst"
        const val import_language_mst = "import_language_mst"
        const val feedback_mst = "feedback_mst"
        const val paper_mst = "paper_mst"
        const val sub_question_mst = "sub_question_mst"
        const val option_mst = "option_mst"
        const val trans_option_mst = "trans_option_mst"


        //Sync Master
        const val sync_batch_arr_mst = "sync_batch_arr_mst"
        const val sync_user_attendance_mst = "sync_user_attendance_mst"
        const val sync_user_theory_attendance_mst = "sync_user_theory_attendance_mst"
        const val sync_user_viva_attendance_mst = "sync_user_viva_attendance_mst"
        const val sync_user_practical_attendance_mst = "sync_user_practical_attendance_mst"
        const val sync_assessor_attendance_mst = "sync_assessor_attendance_mst"
        const val sync_user_profile_mst = "sync_user_profile_mst"
        const val sync_feedback_arr_mst = "sync_feedback_arr_mst"
        const val sync_paper_arr_mst = "sync_paper_arr_mst"
        const val sync_user_arr_mst = "sync_user_arr_mst"
        const val sync_image_arr_mst = "sync_image_arr_mst"
        const val sync_log_arr_mst = "sync_log_arr_mst"


        //user tables fields
        const val id = "id"
        const val batch_id = "batch_id"
        const val client_id = "client_id"
        const val login_app_type = "login_app_type"
        const val login_type = "login_type"
        const val message = "message"
        const val server_ip = "server_ip"
        const val status = "status"
        const val user_id = "user_id"
        const val user_name = "user_name"
        const val language_id = "language_id"
        const val label = "label"
        const val clear = "clear"
        const val instruction = "instruction"
        const val login = "login"
        const val logout = "logout"
        const val next = "next"
        const val not_now = "not_now"
        const val start_theory = "start_theory"
        const val submit = "submit"
        const val text_instruction = "text_instruction"
        const val assessor_name = "assessor_name"
        const val sector_id = "sector_id"
        const val sector_name = "sector_name"
        const val batch_no = "batch_no"
        const val assessment_date = "assessment_date"
        const val duration = "duration"
        const val alt_lang_id = "alt_lang_id"
        const val s3_secret_key = "s3_secret_key"
        const val s3_secret_pwd = "s3_secret_pwd"
        const val s3_group_path = "s3_group_path"
        const val total_practical_marks = "total_practical_marks"
        const val total_theory_marks = "total_theory_marks"
        const val total_viva_marks = "total_viva_marks"
        const val img_capture_time = "img_capture_time"
        const val option_randomize = "option_randomize"
        const val u_attendance_before_theory = "u_attendance_before_theory"
        const val u_attendance_after_theory = "u_attendance_after_theory"
        const val u_attendance_before_viva = "u_attendance_before_viva"
        const val u_attendance_after_viva = "u_attendance_after_viva"
        const val aadhar_front = "aadhar_front"
        const val aadhar_back = "aadhar_back"
        const val profile_pic = "profile_pic"
        const val assessor_attendance_before = "assessor_attendance_before"
        const val assessor_attendance_after = "assessor_attendance_after"
        const val mark_for_review = "mark_for_review"
        const val video_in_viva = "video_in_viva"
        const val candidate_profile = "candidate_profile"
        const val assessor_profile = "assessor_profile"
        const val candidate_feedback = "candidate_feedback"
        const val assessor_feedback = "assessor_feedback"
        const val attempt_all = "attempt_all"
        const val user_type = "user_type"
        const val login_id = "login_id"
        const val login_pass = "login_pass"
        const val enrollment_no = "enrollment_no"
        const val candidate_id = "candidate_id"
        const val father_name = "father_name"
        const val mobile_no = "mobile_no"
        const val jobrole = "jobrole"
        const val theory_paper_set_id = "theory_paper_set_id"
        const val viva_paper_set_id = "viva_paper_set_id"
        const val practical_paper_set_id = "practical_paper_set_id"
        const val language_name = "language_name"
        const val theory_instructions = "theory_instructions"
        const val viva_instructions = "viva_instructions"
        const val practical_instructions = "practical_instructions"
        const val fq_id = "fq_id"
        const val fq_text = "fq_text"
        const val fq_type = "fq_type"
        const val option1 = "option1"
        const val option2 = "option2"
        const val option3 = "option3"
        const val option4 = "option4"
        const val paper_set_id = "paper_set_id"
        const val question_id = "question_id"
        const val question = "question"
        const val question_marks = "question_marks"
        const val q_image = "q_image"
        const val trans_question = "trans_question"
        const val sub_question_count = "sub_question_count"
        const val squestion_id = "squestion_id"
        const val seq_no = "seq_no"
        const val option_count = "option_count"
        const val option = "option"
        const val subQuestionId = "subQuestionId"
        const val option_image = "option_image"
        const val optionId = "optionId"
        const val transOptionId = "transOptionId"


        // Sync Column
        const val entry_id = "entry_id"
        const val entry_id_time = "entry_id_time"
        const val entry_photo = "entry_photo"
        const val entry_photo_time = "entry_photo_time"
        const val exit_id = "exit_id"
        const val exit_id_time = "exit_id_time"
        const val exit_photo = "exit_photo"
        const val exit_photo_time = "exit_photo_time"
        const val device_id = "device_id"
        const val exported_by = "exported_by"
        const val exported_user_type = "exported_user_type"
        const val export_time = "export_time"
        const val export_type = "export_type"
        const val email_id = "email_id"
        const val aadhar_no = "aadhar_no"
        const val feedback_q_id = "feedback_q_id"
        const val response = "response"
        const val created_at = "created_at"
        const val user_ans_id = "user_ans_id"
        const val sub_question_seq = "sub_question_seq"
        const val consumed_time = "consumed_time"
        const val paper_type_id = "paper_type_id"
        const val user_start_time = "user_start_time"
        const val user_end_time = "user_end_time"
        const val attendance_status = "attendance_status"
        const val video_url = "video_url"
        const val image_url = "image_url"
        const val capture_time = "capture_time"
        const val captured_in = "captured_in"
        const val lat = "lat"
        const val long = "long"
        const val log_text = "log_text"

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper? {
            synchronized(lock) {
                if (databaseHelper == null) {
                    databaseHelper = DatabaseHelper(context.applicationContext)
                }
            }
            return databaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {

        val TABLE_USER_MST = "CREATE TABLE " + login_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                batch_id + " TEXT," +
                client_id + " TEXT," +
                login_app_type + " TEXT," +
                login_type + " TEXT," +
                message + " TEXT," +
                server_ip + " TEXT," +
                status + " TEXT," +
                user_id + " TEXT," +
                user_name + " TEXT" +
                ");"

        val TABLE_LANGUAGE_MST = "CREATE TABLE " + languages_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                language_id + " TEXT," +
                label + " TEXT" +
                ");"

        val TABLE_LANGUAGE_TEXT_MST = "CREATE TABLE " + languages_text_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                clear + " TEXT," +
                instruction + " TEXT," +
                login + " TEXT," +
                logout + " TEXT," +
                next + " TEXT," +
                not_now + " TEXT," +
                start_theory + " TEXT," +
                submit + " TEXT," +
                text_instruction + " TEXT," +
                language_id + " TEXT" +
                ");"


        val TABLE_BATCH_MST = "CREATE TABLE " + batch_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                assessor_name + " TEXT," +
                batch_id + " TEXT," +
                sector_id + " TEXT," +
                sector_name + " TEXT," +
                batch_no + " TEXT," +
                assessment_date + " TEXT," +
                duration + " TEXT," +
                client_id + " TEXT," +
                alt_lang_id + " TEXT," +
                s3_secret_key + " TEXT," +
                s3_secret_pwd + " TEXT," +
                s3_group_path + " TEXT," +
                total_practical_marks + " TEXT," +
                total_theory_marks + " TEXT," +
                total_viva_marks + " TEXT" +
                ");"


        val TABLE_BATCH_CONFIG_MST = "CREATE TABLE " + batch_config_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                batch_id + " TEXT," +
                img_capture_time + " TEXT," +
                option_randomize + " TEXT," +
                u_attendance_before_theory + " TEXT," +
                u_attendance_after_theory + " TEXT," +
                u_attendance_before_viva + " TEXT," +
                u_attendance_after_viva + " TEXT," +
                aadhar_front + " TEXT," +
                aadhar_back + " TEXT," +
                profile_pic + " TEXT," +
                assessor_attendance_before + " TEXT," +
                assessor_attendance_after + " TEXT," +
                mark_for_review + " TEXT," +
                video_in_viva + " TEXT," +
                candidate_profile + " TEXT," +
                assessor_profile + " TEXT," +
                candidate_feedback + " TEXT," +
                assessor_feedback + " TEXT," +
                attempt_all + " TEXT" +
                ");"


        val TABLE_USER_DATA_MST = "CREATE TABLE " + user_data_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                batch_id + " TEXT," +
                user_type + " TEXT," +
                login_id + " TEXT," +
                login_pass + " TEXT," +
                user_id + " TEXT," +
                user_name + " TEXT," +
                enrollment_no + " TEXT," +
                candidate_id + " TEXT," +
                father_name + " TEXT," +
                mobile_no + " TEXT," +
                jobrole + " TEXT," +
                theory_paper_set_id + " TEXT," +
                viva_paper_set_id + " TEXT," +
                practical_paper_set_id + " TEXT," +
                alt_lang_id + " TEXT," +
                client_id + " TEXT" +
                ");"


        val TABLE_PAPER_MST = "CREATE TABLE " + paper_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                paper_set_id + " TEXT," +
                question_id + " TEXT," +
                question + " TEXT," +
                question_marks + " TEXT," +
                q_image + " TEXT," +
                trans_question + " TEXT," +
                sub_question_count + " TEXT" +
                ");"

        val TABLE_SUB_QUESTION_MST = "CREATE TABLE " + sub_question_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                question_id + " TEXT," +
                squestion_id + " TEXT," +
                question + " TEXT," +
                question_marks + " TEXT," +
                seq_no + " TEXT," +
                trans_question + " TEXT," +
                option_count + " TEXT," +
                q_image + " TEXT" +
                ");"

        val TABLE_OPTION_MST = "CREATE TABLE " + option_mst + "(" +
                optionId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                id + " TEXT," +
                option + " TEXT," +
                subQuestionId + " TEXT," +
                option_image + " TEXT" +
                ");"

        val TABLE_TRANS_OPTION_MST = "CREATE TABLE " + trans_option_mst + "(" +
                transOptionId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                id + " TEXT," +
                option + " TEXT," +
                subQuestionId + " TEXT," +
                option_image + " TEXT" +
                ");"

        val TABLE_IMPORT_LANGUAGE_MST = "CREATE TABLE " + import_language_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                language_id + " TEXT," +
                language_name + " TEXT," +
                theory_instructions + " TEXT," +
                viva_instructions + " TEXT," +
                practical_instructions + " TEXT" +
                ");"


        val TABLE_FEEDBACK_MST = "CREATE TABLE " + feedback_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                fq_id + " TEXT," +
                fq_text + " TEXT," +
                fq_type + " TEXT," +
                user_type + " TEXT," +
                option1 + " TEXT," +
                option2 + " TEXT," +
                option3 + " TEXT," +
                option4 + " TEXT" +
                ");"


        val TABLE_SYNC_BATCH_ARR_MST = "CREATE TABLE " + sync_batch_arr_mst + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                batch_id + " TEXT," +
                device_id + " TEXT," +
                exported_by + " TEXT," +
                exported_user_type + " TEXT," +
                export_time + " TEXT," +
                export_type + " TEXT" +
                ");"

        val TABLE_SYNC_USER_ATTENDANCE_MST =
            "CREATE TABLE " + sync_user_attendance_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    batch_id + " TEXT," +
                    entry_id + " TEXT," +
                    entry_id_time + " TEXT," +
                    entry_photo + " TEXT," +
                    entry_photo_time + " TEXT," +
                    exit_id + " TEXT," +
                    exit_id_time + " TEXT," +
                    exit_photo + " TEXT," +
                    exit_photo_time + " TEXT," +
                    user_id + " TEXT" +
                    ");"

        val TABLE_SYNC_USER_THEORY_ATTENDANCE_MST =
            "CREATE TABLE " + sync_user_theory_attendance_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    batch_id + " TEXT," +
                    entry_id + " TEXT," +
                    entry_id_time + " TEXT," +
                    entry_photo + " TEXT," +
                    entry_photo_time + " TEXT," +
                    exit_id + " TEXT," +
                    exit_id_time + " TEXT," +
                    exit_photo + " TEXT," +
                    exit_photo_time + " TEXT," +
                    user_id + " TEXT" +
                    ");"


        val TABLE_SYNC_USER_VIVA_ATTENDANCE_MST =
            "CREATE TABLE " + sync_user_viva_attendance_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    batch_id + " TEXT," +
                    entry_id + " TEXT," +
                    entry_id_time + " TEXT," +
                    entry_photo + " TEXT," +
                    entry_photo_time + " TEXT," +
                    exit_id + " TEXT," +
                    exit_id_time + " TEXT," +
                    exit_photo + " TEXT," +
                    exit_photo_time + " TEXT," +
                    user_id + " TEXT" +
                    ");"

        val TABLE_SYNC_USER_PRACTICAL_ATTENDANCE_MST =
            "CREATE TABLE " + sync_user_practical_attendance_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    batch_id + " TEXT," +
                    entry_id + " TEXT," +
                    entry_id_time + " TEXT," +
                    entry_photo + " TEXT," +
                    entry_photo_time + " TEXT," +
                    exit_id + " TEXT," +
                    exit_id_time + " TEXT," +
                    exit_photo + " TEXT," +
                    exit_photo_time + " TEXT," +
                    user_id + " TEXT" +
                    ");"



        val TABLE_SYNC_USER_ASSESSOR_ATTENDANCE_MST =
            "CREATE TABLE " + sync_assessor_attendance_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    batch_id + " TEXT," +
                    entry_id + " TEXT," +
                    entry_id_time + " TEXT," +
                    entry_photo + " TEXT," +
                    entry_photo_time + " TEXT," +
                    exit_id + " TEXT," +
                    exit_id_time + " TEXT," +
                    exit_photo + " TEXT," +
                    exit_photo_time + " TEXT," +
                    user_id + " TEXT" +
                    ");"

        val TABLE_SYNC_USER_PROFILE_MST =
            "CREATE TABLE " + sync_user_profile_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    user_id + " TEXT," +
                    user_type + " TEXT," +
                    batch_id + " TEXT," +
                    user_name + " TEXT," +
                    mobile_no + " TEXT," +
                    email_id + " TEXT," +
                    aadhar_no + " TEXT" +
                    ");"

        val TABLE_SYNC_FEEDBACK_ARR_MST =
            "CREATE TABLE " + sync_feedback_arr_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    user_id + " TEXT," +
                    user_type + " TEXT," +
                    batch_id + " TEXT," +
                    feedback_q_id + " TEXT," +
                    response + " TEXT," +
                    created_at + " TEXT" +
                    ");"

        val TABLE_SYNC_PAPER_ARR_MST =
            "CREATE TABLE " + sync_paper_arr_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    user_id + " TEXT," +
                    batch_id + " TEXT," +
                    question_id + " TEXT," +
                    squestion_id + " TEXT," +
                    user_ans_id + " TEXT," +
                    sub_question_seq + " TEXT," +
                    consumed_time + " TEXT," +
                    status + " TEXT," +
                    paper_set_id + " TEXT," +
                    paper_type_id + " TEXT," +
                    created_at + " TEXT" +
                    ");"

        val TABLE_SYNC_USER_ARR_MST =
            "CREATE TABLE " + sync_user_arr_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    user_id + " TEXT," +
                    batch_id + " TEXT," +
                    consumed_time + " TEXT," +
                    user_start_time + " TEXT," +
                    user_end_time + " TEXT," +
                    status + " TEXT," +
                    attendance_status + " TEXT," +
                    paper_set_id + " TEXT," +
                    paper_type_id + " TEXT," +
                    video_url + " TEXT" +
                    ");"


        val TABLE_SYNC_IMAGE_ARR_MST =
            "CREATE TABLE " + sync_image_arr_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    user_id + " TEXT," +
                    batch_id + " TEXT," +
                    image_url + " TEXT," +
                    capture_time + " TEXT," +
                    captured_in + " TEXT," +
                    lat + " TEXT," +
                    long + " TEXT" +
                    ");"

        val TABLE_SYNC_LOG_ARR_MST =
            "CREATE TABLE " + sync_log_arr_mst + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    user_id + " TEXT," +
                    batch_id + " TEXT," +
                    log_text + " TEXT," +
                    question_id + " TEXT," +
                    squestion_id + " TEXT," +
                    lat + " TEXT," +
                    long + " TEXT," +
                    paper_set_id + " TEXT," +
                    paper_type_id + " TEXT," +
                    created_at+ " TEXT" +
                    ");"






        db.execSQL(TABLE_USER_MST)
        db.execSQL(TABLE_LANGUAGE_MST)
        db.execSQL(TABLE_LANGUAGE_TEXT_MST)
        db.execSQL(TABLE_BATCH_MST)
        db.execSQL(TABLE_BATCH_CONFIG_MST)
        db.execSQL(TABLE_USER_DATA_MST)
        db.execSQL(TABLE_PAPER_MST)
        db.execSQL(TABLE_SUB_QUESTION_MST)
        db.execSQL(TABLE_OPTION_MST)
        db.execSQL(TABLE_TRANS_OPTION_MST)
        db.execSQL(TABLE_IMPORT_LANGUAGE_MST)
        db.execSQL(TABLE_FEEDBACK_MST)



        db.execSQL(TABLE_SYNC_BATCH_ARR_MST)
        db.execSQL(TABLE_SYNC_USER_ATTENDANCE_MST)
        db.execSQL(TABLE_SYNC_USER_THEORY_ATTENDANCE_MST)
        db.execSQL(TABLE_SYNC_USER_VIVA_ATTENDANCE_MST)
        db.execSQL(TABLE_SYNC_USER_PRACTICAL_ATTENDANCE_MST)
        db.execSQL(TABLE_SYNC_USER_ASSESSOR_ATTENDANCE_MST)
        db.execSQL(TABLE_SYNC_USER_PROFILE_MST)
        db.execSQL(TABLE_SYNC_FEEDBACK_ARR_MST)
        db.execSQL(TABLE_SYNC_PAPER_ARR_MST)
        db.execSQL(TABLE_SYNC_USER_ARR_MST)
        db.execSQL(TABLE_SYNC_IMAGE_ARR_MST)
        db.execSQL(TABLE_SYNC_LOG_ARR_MST)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
        }
    }

}