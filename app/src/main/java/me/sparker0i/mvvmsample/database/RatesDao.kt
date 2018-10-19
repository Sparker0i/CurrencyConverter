package me.sparker0i.mvvmsample.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.sparker0i.mvvmsample.model.Rates

@Dao interface RatesDao {
    @Query("SELECT COUNT(*) FROM Rates") fun getRatesSize(): Int
    @Query("SELECT * FROM Rates") fun getAllRates(): LiveData<List<Rates>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertRate(rate: Rates)
}