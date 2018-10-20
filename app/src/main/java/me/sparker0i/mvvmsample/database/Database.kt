package me.sparker0i.mvvmsample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.sparker0i.mvvmsample.model.Rates
import me.sparker0i.mvvmsample.utils.Constants

@Database(entities = [Rates::class], version = Constants.DATABASE_VERSION)
abstract class Database: RoomDatabase() {
    abstract fun daoAccess(): RatesDao
}