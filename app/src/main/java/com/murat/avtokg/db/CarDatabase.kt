package com.murat.avtokg.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CarEntity::class], version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
}