package com.murat.avtokg.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murat.avtokg.db.CarEntity
import com.murat.avtokg.repository.DbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class CarViewModel @Inject constructor(private val repository: DbRepository):ViewModel() {


    val allCarData : MutableLiveData<List<CarEntity>> by lazy {
        MutableLiveData<List<CarEntity>>()
    }


    fun getAllCars() {
        allCarData.value = repository.getAllCar()
    }

    fun getFilterCar(filter: String) {
        allCarData.value = repository.filterCar(filter) }


    fun updateCar(carEntity: CarEntity){
        repository.updateCar(carEntity)
    }





}