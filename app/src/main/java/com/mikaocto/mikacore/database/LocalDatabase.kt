package com.mikaocto.mikacore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mikaocto.mikacore.model.InputItem
import com.mikaocto.mikacore.model.User

@Database(entities = [InputItem::class, User::class], version = 3, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun itemDao(): InputItemDao
    abstract fun userDao(): UserDao
}