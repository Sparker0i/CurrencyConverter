package me.sparker0i.mvvmsample.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
        tableName = "Rates"
)
data class Rates(
        @PrimaryKey var symbol: String,
        var rate: Double?
): Serializable