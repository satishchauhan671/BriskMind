package com.brisk.assessment.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "texts")
class TextsRes {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var clear: String? = null
    var instruction: String? = null
    var login: String? = null
    var logout: String? = null
    var next: String? = null
    var not_now: String? = null
    var start_theory: String? = null
    var submit: String? = null
    var text_instruction: String? = null
    var language_id: String? = ""


    constructor()

    @Ignore
    constructor(
        clear: String?,
        instruction: String?,
        login: String?,
        logout: String?,
        next: String?,
        not_now: String?,
        start_theory: String?,
        submit: String?,
        text_instruction: String?
    ) {
        this.clear = clear
        this.instruction = instruction
        this.login = login
        this.logout = logout
        this.next = next
        this.not_now = not_now
        this.start_theory = start_theory
        this.submit = submit
        this.text_instruction = text_instruction
    }
}