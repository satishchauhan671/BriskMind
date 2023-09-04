package com.brisk.assessment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brisk.assessment.repositories.LoginRepo

class MainViewModelFactory(private val loginRepo: LoginRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(loginRepo) as T
    }
}