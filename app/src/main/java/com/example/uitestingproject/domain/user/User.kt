package com.example.uitestingproject.domain.user

data class User(
    val id: Long,
    val login: String?,
    val name: String?,
    val location: String?,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val email: String?,
    val followers: Int,
    val following: Int,
)

data class UserInList(
    val id: Long,
    val login: String?,
    val avatarUrl: String?,
)