package com.example.uitestingproject.data.user.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.uitestingproject.Constants.DATABASE_TABLE_NAME
import com.example.uitestingproject.domain.user.UserInList

@Entity(tableName = DATABASE_TABLE_NAME)
data class UserInListEntity(
    @PrimaryKey val id: Long,
    val login: String?,
    val imageUrl: String?,
)

fun UserInList.toUserInListEntity() = UserInListEntity(
    id = id, login = login, imageUrl = avatarUrl,
)

fun UserInListEntity.toDomainUser() = UserInList(
    id = id, login = login, avatarUrl = imageUrl,
)