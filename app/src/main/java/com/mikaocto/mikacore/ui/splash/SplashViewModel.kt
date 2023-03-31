package com.mikaocto.mikacore.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikaocto.mikacore.database.UserRepository
import com.mikaocto.mikacore.model.User
import com.mikaocto.mikacore.util.io
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    private val _splashViewState = MutableLiveData<SplashViewState>()
    val splashViewState: LiveData<SplashViewState> by lazy { _splashViewState }

    private val _loginViewState = MutableLiveData<LoginViewState>()
    val loginViewState: LiveData<LoginViewState> by lazy { _loginViewState }

    fun checkUser() {
        viewModelScope.launch {
            delay(3000)
            io {
                userRepository.getUserData().onEmpty {
                    _splashViewState.postValue(SplashViewState.OnEmptyUser)
                }.collect { data ->
                    _splashViewState.postValue(SplashViewState.UserData(data.first()))
                }
            }
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            io {
                userRepository.insertUserData(user).catch { e ->
                    _loginViewState.postValue(LoginViewState.OnErrorSaveLogin(e.message.toString()))
                }.collect {
                    _loginViewState.postValue(LoginViewState.OnSuccessLogin)
                }
            }
        }
    }

    sealed class SplashViewState {
        class UserData(val user: User) : SplashViewState()
        object OnEmptyUser : SplashViewState()
    }

    sealed class LoginViewState {
        object OnSuccessLogin : LoginViewState()
        class OnErrorSaveLogin(val message: String) : LoginViewState()
    }
}