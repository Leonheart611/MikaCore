package com.mikaocto.mikacore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikaocto.mikacore.database.InputItemRepository
import com.mikaocto.mikacore.database.UserRepository
import com.mikaocto.mikacore.model.InputItem
import com.mikaocto.mikacore.model.User
import com.mikaocto.mikacore.util.io
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: InputItemRepository,
    private val userRepository: UserRepository
) :
    ViewModel() {

    private val _inputItemList = MutableLiveData<MainActivityViewState>()
    val inputItemLiveData: LiveData<MainActivityViewState> by lazy { _inputItemList }
    var user = MutableLiveData<User>()

    fun getAllData() {
        viewModelScope.launch {
            io {
                repository.getAllInputItem().onEmpty {
                    _inputItemList.postValue(MainActivityViewState.OnEmptyList)
                }.collect {
                    _inputItemList.postValue(MainActivityViewState.OnItemAvailable(it))
                }
            }
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            io {
                userRepository.getUserData().collect {
                    user.postValue(it.first())
                }
            }
        }
    }

    fun deleteData(data: InputItem) {
        viewModelScope.launch {
            io {
                repository.deleteData(data)
                getAllData()
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            io {
                user.value?.let { userRepository.deleteUserData(it) }
            }
        }
    }

    sealed class MainActivityViewState {
        class OnItemAvailable(val item: List<InputItem>) : MainActivityViewState()
        object OnEmptyList : MainActivityViewState()
    }

}