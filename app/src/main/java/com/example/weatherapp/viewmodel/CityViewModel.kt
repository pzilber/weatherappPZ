package com.example.weatherapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.location.LocationManager
import com.example.weatherapp.data.model.City
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.network.CityRepository
import com.example.weatherapp.data.network.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @OptIn(ExperimentalCoroutinesApi::class)
@Inject constructor(
    private val locationManager: LocationManager,
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    private val _currentLocation = MutableStateFlow<City?>(null)
    val currentLocation: StateFlow<City?> get() = _currentLocation

    private val sharedPreferences =
        application.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

    init {
        loadCities()
        loadSavedCity()
    }

    private fun loadSavedCity() {
        val cityName = sharedPreferences.getString("selected_city_name", null)
        val countryName = sharedPreferences.getString("selected_city_country", null)
        if (cityName != null && countryName != null) {
            _selectedCity.value = City(cityName, countryName)
        }
    }

    private fun saveSelectedCity(city: City) {
        with(sharedPreferences.edit()) {
            putString("selected_city_name", city.name)
            putString("selected_city_country", city.country)
            apply()
        }
    }

    fun loadCities() {
        viewModelScope.launch {
            val citiesList = cityRepository.getCities()
            _cities.value = citiesList
        }
    }

    fun searchCity(query: String) {
        viewModelScope.launch {
            try {
                val weatherData = weatherRepository.getWeather(query)
                val city = City(query, "Country")
                _cities.value = listOf(city)
                saveSelectedCity(city)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    fun selectCity(city: City) {
        viewModelScope.launch {
            _selectedCity.emit(city)
            saveSelectedCity(city)
        }
    }

    fun loadWeather(city: City) {
        viewModelScope.launch {
            val weatherData = weatherRepository.getWeather(city.name)
            _weather.value = weatherData
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun findCurrentLocation() {
        viewModelScope.launch {
            val currentLocation = locationManager.getCurrentLocation()
            _currentLocation.value = currentLocation
            if (currentLocation != null) {
                _cities.value += currentLocation
                saveSelectedCity(currentLocation)
            }
        }
    }
}
