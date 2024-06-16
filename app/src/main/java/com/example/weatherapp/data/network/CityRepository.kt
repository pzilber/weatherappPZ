package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityRepository @Inject constructor() {
    private val cityList = listOf(
        City("Paris", "France"),
        City("New York", "USA"),
        City("Tokyo", "Japan")
    )

    suspend fun getCities(): List<City> {
        return withContext(Dispatchers.IO) {
            cityList
        }
    }
}
