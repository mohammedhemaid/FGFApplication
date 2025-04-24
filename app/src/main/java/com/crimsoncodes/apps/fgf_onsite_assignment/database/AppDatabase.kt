package com.crimsoncodes.apps.fgf_onsite_assignment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crimsoncodes.apps.fgf_onsite_assignment.database.dao.AnimalDao
import com.crimsoncodes.apps.fgf_onsite_assignment.database.dao.RemoteKeysDao
import com.crimsoncodes.apps.fgf_onsite_assignment.database.model.AnimalEntity
import com.crimsoncodes.apps.fgf_onsite_assignment.database.model.RemoteKeys

@Database(entities = [AnimalEntity::class, RemoteKeys::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun animalDao(): AnimalDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}