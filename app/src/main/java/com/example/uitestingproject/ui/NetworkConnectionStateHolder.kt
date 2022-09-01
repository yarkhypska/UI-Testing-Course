package com.example.uitestingproject.ui

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NetworkConnectionStateHolder {
    private val _connectionState = MutableStateFlow(false)
    val connectionState: StateFlow<Boolean>
        get() = _connectionState

    fun updateNetworkAvailability(isAvailable: Boolean) {
        _connectionState.value = isAvailable
    }
}