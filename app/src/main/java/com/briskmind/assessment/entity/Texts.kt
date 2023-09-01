package com.briskmind.assessment.entity

import androidx.room.ColumnInfo

class Texts {
    @ColumnInfo(name = "login")
    var login : String? = null

    @ColumnInfo(name = "submit")
    var submit : String? = null

    @ColumnInfo(name = "clear")
    var clear : String? = null

    @ColumnInfo(name = "start_theory")
    var startTheory : String? = null

    @ColumnInfo(name = "logout")
    var logout : String? = null

    @ColumnInfo(name = "not_now")
    var notNow : String? = null

    @ColumnInfo(name = "instruction")
    var instruction : String? = null

    @ColumnInfo(name = "text_instruction")
    var textInstruction : String? = null

    @ColumnInfo(name = "next")
    var next : String? = null
}