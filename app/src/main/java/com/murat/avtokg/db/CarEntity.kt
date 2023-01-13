package com.murat.avtokg.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.murat.avtokg.utils.Constants.CAR_TABLE

@Entity(tableName = CAR_TABLE)
data class CarEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var image: String? = null,
    var brand: String? = null,
    var price : String? = null,
    var model : String? = null,
    var seriesYear : String? = null,
    var body : String? = null,
    ) : java.io.Serializable

