package com.mikaocto.mikacore.di

import android.content.Context
import androidx.room.Room
import com.mikaocto.mikacore.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            LocalDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideInputItemDao(appDatabase: LocalDatabase): InputItemDao {
        return appDatabase.itemDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: LocalDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideInputItemRepository(dao: InputItemDao): InputItemRepository {
        return InputItemRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideUserRepository(dao: UserDao): UserRepository {
        return UserRepositoryImpl(dao)
    }

}