package com.murat.avtokg.db

import androidx.room.*
import com.murat.avtokg.utils.Constants.CAR_TABLE


@Dao
interface CarDao {
    @Query(value = "SELECT * FROM $CAR_TABLE")
    fun getAllCar() : List<CarEntity>

    @Query("SELECT * FROM $CAR_TABLE WHERE brand == :br")
    fun filterBrand(br: String):List<CarEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCar(entity: CarEntity)

    @Update
    fun editCar(entity: CarEntity)


}