package com.example.uitestingproject.di

import android.app.Application
import androidx.room.Room
import com.example.uitestingproject.data.user.local.AppDatabase
import com.example.uitestingproject.data.user.remote.RequestLimitInterceptor
import com.example.uitestingproject.data.user.remote.RetrofitService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule(
    private val application: Application
) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Singleton
    @Provides
    fun provideRetrofitService(): RetrofitService = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(RequestLimitInterceptor()).build())
        .build()
        .create(RetrofitService::class.java)

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase = Room.databaseBuilder(
        app.applicationContext, AppDatabase::class.java, "app-database"
    )
        .fallbackToDestructiveMigration()
        .build()
}