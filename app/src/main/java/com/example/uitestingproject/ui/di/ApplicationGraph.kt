package com.example.uitestingproject.ui.di

import com.example.uitestingproject.UiTestingApplication
import com.example.uitestingproject.di.AppModule
import com.example.uitestingproject.ui.MainActivity
import com.example.uitestingproject.ui.main_screen.MainFragment
import com.example.uitestingproject.ui.main_screen.detailed_info.DetailedUserInfoDialog
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, UiModule::class])
interface ApplicationGraph {

    fun inject(uiTestingApplication: UiTestingApplication)
    fun inject(activity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(detailsDialog: DetailedUserInfoDialog)
}