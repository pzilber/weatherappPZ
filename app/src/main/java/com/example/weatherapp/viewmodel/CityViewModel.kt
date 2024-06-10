package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.location.LocationManager
import com.example.weatherapp.data.model.City
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.network.WeatherRepository
import com.example.weatherapp.data.network.CityRepository
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
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    private val _currentLocation = MutableStateFlow<City?>(null)
    val currentLocation: StateFlow<City?> get() = _currentLocation

    init {
        loadCities()
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
                println("Inicio")
                val weatherData = weatherRepository.getWeather(query)
                println(weatherData)
                // Actualiza _cities con la ciudad encontrada.
                _cities.value = listOf(City(query, "Country"))
                println("Fin")
            } catch (e: Exception) {
                println("InicioExcepcion")
                println(e.message) // Aquí puedes agregar más detalles del mensaje de excepción
                println(e.printStackTrace()) // Imprime el stack trace para más detalles
                println("FinalizaExcepcion")
            }
        }
    }

    fun selectCity(city: City) {
        _selectedCity.value = city
        println("Ciudad seleccionada en CityViewModel: ${city.name}")
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
            _cities.value += (currentLocation ?: City("", ""))
        }
    }
}



/*
class CityViewModel : ViewModel() {

    private val retrofitTest = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val testApiService = retrofitTest.create(TestApiService::class.java)

    fun testApi() {
        viewModelScope.launch {
            try {
                Log.i("CityViewModel", "Inicio test API")
                val response = testApiService.getTest()
                if (response.isSuccessful) {
                    Log.i("CityViewModel", "Respuesta de prueba: ${response.body()}")
                } else {
                    Log.e("CityViewModel", "Error en la respuesta de prueba: ${response.code()}")
                }
                Log.i("CityViewModel", "Fin test API")
            } catch (e: Exception) {
                Log.e("CityViewModel", "Error en la llamada de prueba: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}
*/