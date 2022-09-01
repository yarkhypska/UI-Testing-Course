package com.example.uitestingproject.ui.main_screen.detailed_info

import com.example.uitestingproject.data.user.remote.RetrofitService
import com.example.uitestingproject.data.user.remote.toDomainUser
import com.example.uitestingproject.domain.user.User
import com.example.uitestingproject.ui.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailedUserInfoViewModel @Inject constructor (
    private val api: RetrofitService
) : BaseViewModel<DetailedUserInfoViewModel.State>() {

    private var currentUser: User? = null

    fun updateUser(username: String?) {
        api.getUser(
            username ?: kotlin.run {
                _stateFlow.tryEmit(State.Error)
                return
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ user ->
            val user = user.toDomainUser()
                currentUser = user
            _stateFlow.tryEmit(State.Success(user))
        }, {
            _stateFlow.tryEmit(State.Error)
        }
        ).also { disposable ->
            disposables.add(disposable)
        }
    }

    fun getUserUri() = currentUser?.htmlUrl

    sealed class State {
        data class Success(val user: User) : State()
        object Error : State()
    }
}