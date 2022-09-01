package com.example.uitestingproject.ui

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

open class BaseViewModel<T> : ViewModel() {
    protected val disposables = CompositeDisposable()

    protected val _stateFlow = MutableSharedFlow<T>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val stateFlow: SharedFlow<T>
        get() = _stateFlow

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}