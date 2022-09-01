package com.example.uitestingproject.data.user.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uitestingproject.Constants.DATABASE_TABLE_NAME
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserInListEntity>)

    @Query("SELECT * FROM $DATABASE_TABLE_NAME")
    fun pagingSource(): PagingSource<Int, UserInListEntity>

    @Query("SELECT * FROM $DATABASE_TABLE_NAME")
    fun getAll(): Single<List<UserInListEntity>>

    @Query("DELETE FROM $DATABASE_TABLE_NAME")
    fun clearAll()
}