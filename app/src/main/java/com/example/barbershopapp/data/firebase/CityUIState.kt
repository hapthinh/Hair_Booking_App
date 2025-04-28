package com.example.barbershopapp.data.firebase

import com.example.barbershopapp.data.city.City
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CityUIState {
    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities : StateFlow<List<City>> = _cities

    init {
        getCities()
    }

    private fun getCities() {
        firestore.collection("cities").addSnapshotListener {snapshot,error ->
            if(error != null){
                return@addSnapshotListener
            }
            val cityList = snapshot?.documents?.mapNotNull { doc -> doc.toObject(City::class.java) } ?: emptyList()
            _cities.value = cityList
        }
    }
}