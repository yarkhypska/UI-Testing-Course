package com.example.uitestingproject.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.uitestingproject.data.user.local.AppDatabase
import com.example.uitestingproject.data.user.remote.RetrofitService

class MainScreenViewModelFactory(
    private val database: AppDatabase,
    private val api: RetrofitService,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(api, database) as T
    }
}