package com.mikaocto.mikacore.database

import androidx.room.*
import com.mikaocto.mikacore.model.InputItem

@Dao
interface InputItemDao {

    @Query("SELECT * FROM InputItem")
    fun getAllInputItem(): List<InputItem>

    @Insert(entity = InputItem::class, onConflict = OnConflictStrategy.ABORT)
    fun insertData(inputItem: InputItem)

    @Delete(entity = InputItem::class)
    fun deleteData(inputItem: InputItem)
}