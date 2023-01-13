package com.murat.avtokg.repository

import com.murat.avtokg.db.CarDao
import com.murat.avtokg.db.CarEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbRepository @Inject constructor(private var dao : CarDao) {

    fun saveCar(carEntity: CarEntity) = dao.insertCar(carEntity)
    fun updateCar(carEntity: CarEntity) = dao.editCar(carEntity)
    fun getAllCar() = dao.getAllCar()
    fun filterCar(brand: String) = dao.filterTask(brand)

}