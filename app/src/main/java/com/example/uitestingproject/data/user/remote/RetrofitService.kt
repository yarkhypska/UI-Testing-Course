package com.example.uitestingproject.data.user.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("users")
    fun getUsers(@Query("since") lastUserId: Long = 0): Single<List<RemoteUserInList>>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Single<RemoteUser>
}