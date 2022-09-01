package com.example.uitestingproject.ui.main_screen.detailed_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.uitestingproject.data.user.remote.RetrofitService

class DetailedUseInfoViewModelFactory(
    private val api: RetrofitService,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailedUserInfoViewModel(api) as T
    }
}