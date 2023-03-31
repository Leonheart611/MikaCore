package com.mikaocto.mikacore.database

import androidx.room.*
import com.mikaocto.mikacore.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getUserData(): List<User>

    @Insert(entity = User::class, onConflict = OnConflictStrategy.ABORT)
    fun insertUserData(user: User)

    @Delete(entity = User::class)
    fun deleteUserData(user: User)
}