package com.mikaocto.mikacore.database

import com.mikaocto.mikacore.model.InputItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface InputItemRepository {
    fun getAllInputItem(): Flow<List<InputItem>>
    fun insertData(inputItem: InputItem)
    fun deleteData(inputItem: InputItem)
}

class InputItemRepositoryImpl @Inject constructor(private val dao: InputItemDao) :
    InputItemRepository {
    override fun getAllInputItem(): Flow<List<InputItem>> = flow {
        val data = dao.getAllInputItem()
        if (data.isNotEmpty()) emit(data) else emptyFlow<List<InputItem>>()
    }

    override fun insertData(inputItem: InputItem) {
        dao.insertData(inputItem)
    }

    override fun deleteData(inputItem: InputItem) {
        dao.deleteData(inputItem)
    }

}