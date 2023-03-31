package com.mikaocto.mikacore.ui.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikaocto.mikacore.database.InputItemRepository
import com.mikaocto.mikacore.database.UserRepository
import com.mikaocto.mikacore.model.InputItem
import com.mikaocto.mikacore.model.User
import com.mikaocto.mikacore.util.io
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val repository: InputItemRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var user: User? = null

    fun getUserData() {
        viewModelScope.launch {
            io {
                userRepository.getUserData().collect {
                    user = it.first()
                }
            }
        }
    }


    fun saveData(inputData: InputItem) {
        viewModelScope.launch {
            io {
                repository.insertData(inputData)
            }
        }
    }
}