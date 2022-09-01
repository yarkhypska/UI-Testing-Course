package com.example.uitestingproject.data.user

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.MediatorResult.Success
import androidx.paging.rxjava3.RxRemoteMediator
import com.example.uitestingproject.data.user.local.AppDatabase
import com.example.uitestingproject.data.user.local.UserInListEntity
import com.example.uitestingproject.data.user.local.toUserInListEntity
import com.example.uitestingproject.data.user.remote.RetrofitService
import com.example.uitestingproject.data.user.remote.toDomainUserInList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val database: AppDatabase,
    private val service: RetrofitService,
) : RxRemoteMediator<Int, UserInListEntity>() {

    companion object {
        private val LOG_TAG = UserRemoteMediator::class.java.simpleName
    }

    private val dao = database.userDao()

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, UserInListEntity>
    ): Single<MediatorResult> {
        Log.d(LOG_TAG, "loadSingle($loadType)")
        val loadKey: Long = when (loadType) {
            LoadType.REFRESH -> 0
            //skip logic with prepend
            LoadType.PREPEND -> null
            LoadType.APPEND -> state.lastItemOrNull()?.id
        } ?: return Single.just (Success(true))

        return service.getUsers(lastUserId = loadKey)
            .subscribeOn(Schedulers.io())
            .map { list ->
                if (list.isNotEmpty()) {
                    database.runInTransaction {
                        if (loadType == LoadType.REFRESH) {
                            Log.d(LOG_TAG, "loadSingle(): clear all")
                            dao.clearAll()
                        }
                        dao.insertAll(list.map { it.toDomainUserInList().toUserInListEntity() })
                    }
                }

                Success(endOfPaginationReached = list.isEmpty()) as MediatorResult
            }.onErrorResumeNext {
                Log.d(LOG_TAG, "onErrorResumeNext ${it.message}")
                if (it is IOException || it is HttpException) {
                    Single.just(MediatorResult.Error(it))
                } else Single.error(it)
            }
    }
}
