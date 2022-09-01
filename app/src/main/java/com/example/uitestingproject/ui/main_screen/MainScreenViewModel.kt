package com.example.uitestingproject.ui.main_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava3.flowable
import com.example.uitestingproject.data.user.UserRemoteMediator
import com.example.uitestingproject.data.user.local.AppDatabase
import com.example.uitestingproject.data.user.remote.RetrofitService
import com.example.uitestingproject.ui.BaseViewModel
import com.example.uitestingproject.ui.NetworkConnectionStateHolder
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val api: RetrofitService,
    private val database: AppDatabase,
): BaseViewModel<MainScreenViewModel.State>() {

    init {
        viewModelScope.launch {
            NetworkConnectionStateHolder.connectionState.collect {
                Log.d("taag", "MainScreenViewModel collect $it")
                _stateFlow.tryEmit(State(it))
            }
        }
    }

    private val mediator = UserRemoteMediator(database, api)
    @OptIn(ExperimentalPagingApi::class)
    private val pager = Pager(
        PagingConfig(pageSize = 10),
        0,
        remoteMediator = mediator
    ) {
        database.userDao().pagingSource()
    }

    val pageSourceFlow = pager.flowable

    data class State(
        val isNetworkAvailable: Boolean
    )
}