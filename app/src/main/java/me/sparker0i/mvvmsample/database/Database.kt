package me.sparker0i.mvvmsample.database

import androidx.room.RoomDatabase

abstract class Database: RoomDatabase() {
    abstract fun daoAccess(): RatesDao
}