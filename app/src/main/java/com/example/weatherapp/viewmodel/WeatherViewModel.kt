package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.network.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> get() = _weather

    fun loadWeather(cityName: String) {
        println("Llegue al WeatherViewModel 1")
        println("Llegue al WeatherViewModel con la ciudad: $cityName")
        viewModelScope.launch {
            try {
                val weatherData = repository.getWeather(cityName)
                println("Datos del clima recibidos: $weatherData")
                _weather.value = weatherData
                println("Datos del clima cargados en WeatherViewModel: $weatherData")
                println("Datos del clima asignados a _weather: ${_weather.value}")
                println("Datos del clima asignados a weather: $weather")
                println("Llegue al WeatherViewModel")
                if (weatherData != null) {
                    println("Weather data loaded in WeatherViewModel: $weatherData")
                }
                else {
                    println("Weather data is null after loading in WeatherViewModel")
                }
            } catch (e: Exception) {
                _weather.value = null
                println(e.message)
                println("Error en el WeatherViewModel")
                println("Error al cargar el clima en WeatherViewModel: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}
