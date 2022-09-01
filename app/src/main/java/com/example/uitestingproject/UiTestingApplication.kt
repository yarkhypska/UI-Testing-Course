package com.example.uitestingproject

import android.app.Application
import com.example.uitestingproject.di.AppModule
import com.example.uitestingproject.ui.di.DaggerApplicationGraph

class UiTestingApplication: Application() {

    val applicationGraph = DaggerApplicationGraph.builder()
        .appModule(AppModule(this)).build()
}