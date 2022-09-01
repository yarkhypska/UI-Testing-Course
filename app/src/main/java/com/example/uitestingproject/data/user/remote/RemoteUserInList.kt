package com.example.uitestingproject.data.user.remote

import com.example.uitestingproject.domain.user.User
import com.example.uitestingproject.domain.user.UserInList
import com.squareup.moshi.Json

data class RemoteUserInList(
    val id: Long,
    @field:Json(name = "login")
    val username: String,
    @field:Json(name = "avatar_url")
    val avatarUrl: String?,
)

data class RemoteUser(
    val id: Long,
    @field:Json(name = "login")
    val username: String,
    val name: String,
    val location: String?,
    @field:Json(name = "avatar_url")
    val avatarUrl: String?,
    @field:Json(name = "html_url")
    val htmlUrl: String?,
    val email: String,
    val followers: Int,
    val following: Int,
)

fun RemoteUserInList.toDomainUserInList() = UserInList(
    id = id,
    login = username,
    avatarUrl = avatarUrl,
)

fun RemoteUser.toDomainUser() = User(
    id = id,
    login = username,
    name = name,
    location = location,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    email = email,
    followers = followers,
    following = following
)