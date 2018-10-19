package me.sparker0i.mvvmsample.model

import androidx.room.Entity
import java.io.Serializable

@Entity(
        tableName = "Rates"
)
data class Rates(
        var symbol: String,
        var rate: Double
): Serializable