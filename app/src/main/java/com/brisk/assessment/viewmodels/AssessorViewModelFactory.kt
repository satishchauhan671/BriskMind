package com.brisk.assessment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.repositories.AssessorRepo
import com.brisk.assessment.repositories.LoginRepo

class AssessorViewModelFactory(private val assessorRepo: AssessorRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AssessorRepo::class.java)
            .newInstance(assessorRepo)
    }
}