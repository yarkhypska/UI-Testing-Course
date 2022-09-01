package com.example.uitestingproject.ui.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.uitestingproject.data.user.local.AppDatabase
import com.example.uitestingproject.data.user.remote.RetrofitService
import com.example.uitestingproject.ui.main_screen.MainScreenViewModel
import com.example.uitestingproject.ui.main_screen.MainScreenViewModelFactory
import com.example.uitestingproject.ui.main_screen.detailed_info.DetailedUserInfoViewModel
import com.example.uitestingproject.ui.main_screen.detailed_info.DetailedUseInfoViewModelFactory
import dagger.Module

@Module
class UiModule {

    @ActivityScope
    fun provideMainScreenViewModel(
        fragment: Fragment,
        database: AppDatabase,
        api: RetrofitService,
    ): MainScreenViewModel {
        return ViewModelProvider(fragment, MainScreenViewModelFactory(database, api))[
                MainScreenViewModel::class.java
        ]
    }

    @ActivityScope
    fun provideDetailedInfoViewModel(
        fragment: Fragment,
        api: RetrofitService,
    ): DetailedUserInfoViewModel {
        return ViewModelProvider(fragment, DetailedUseInfoViewModelFactory(api))[
                DetailedUserInfoViewModel::class.java
        ]
    }
}