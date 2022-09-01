package com.example.uitestingproject.data.user.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserInListEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}