package com.mikaocto.mikacore.database

import com.mikaocto.mikacore.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UserRepository {
    fun getUserData(): Flow<List<User>>
    fun insertUserData(user: User): Flow<Boolean>
    fun deleteUserData(user: User)
}

class UserRepositoryImpl @Inject constructor(private val dao: UserDao) : UserRepository {
    override fun getUserData(): Flow<List<User>> = flow {
        val data = dao.getUserData()
        if (data.isNotEmpty())
            emit(dao.getUserData())
        else
            emptyFlow<User>()
    }

    override fun insertUserData(user: User): Flow<Boolean> = flow {
        try {
            dao.insertUserData(user)
            emit(true)
        } catch (e: Exception) {
            error(e)
        }
    }

    override fun deleteUserData(user: User) {
        dao.deleteUserData(user)
    }

}