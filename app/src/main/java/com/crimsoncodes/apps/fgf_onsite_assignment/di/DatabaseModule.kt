package com.crimsoncodes.apps.fgf_onsite_assignment.di

import android.content.Context
import androidx.room.Room
import com.crimsoncodes.apps.fgf_onsite_assignment.database.AppDatabase
import com.crimsoncodes.apps.fgf_onsite_assignment.database.dao.AnimalDao
import com.crimsoncodes.apps.fgf_onsite_assignment.database.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app-database",
    ).build()

    @Provides
    @Singleton
    fun providesAnimalDao(
        database: AppDatabase,
    ): AnimalDao = database.animalDao()

    @Provides
    @Singleton
    fun providesRemoteKeysDao(
        database: AppDatabase,
    ): RemoteKeysDao = database.remoteKeysDao()
}
