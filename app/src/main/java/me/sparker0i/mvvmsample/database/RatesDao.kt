package me.sparker0i.mvvmsample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import me.sparker0i.mvvmsample.model.Rates

@Dao interface RatesDao {
    @Query("SELECT COUNT(*) FROM Rates") fun getRatesSize(): Int
    @Query("SELECT * FROM Rates") fun getAllRates(): Single<List<Rates>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertRate(rate: Rates)
}