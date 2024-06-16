package com.example.weatherapp.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    cityName: String
) {
    val weather by weatherViewModel.weather.collectAsState()

    println("Valor de cityName en WeatherScreen: $cityName")
    println("Valor de weather en WeatherScreen: $weather")

    // Efecto lanzado cuando cambia la ciudad seleccionada
    LaunchedEffect(cityName) {
        println("Ciudad seleccionada en WeatherScreen: $cityName")
        weatherViewModel.loadWeather(cityName)
    }

    // Columna principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        println("Entrando a buscar datos del clima en WeatherScreen")
        if (weather != null) {
            val weatherData = weather!!
            println("Datos del clima en WeatherScreen: $weatherData")

            // Recuadro para mostrar el clima
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFADD8E6)) // Color celeste
                    .padding(16.dp)
            ) {
                Column {
                    println("Construyendo la columna con datos del clima en WeatherScreen")
                    Text("Ciudad: $cityName")
                    Text("País: ${weatherData.country}")
                    Text("Temperatura: ${weatherData.temp_c} °C")
                    Text("Humedad: ${weatherData.humidity}%")
                    Text("Condición: ${weatherData.condition}")
                }
            }
        } else {
            // Muestra un mensaje de carga o error si los datos no están disponibles
            println("Weather data is null en WeatherScreen")
            Text("Loading weather data or unable to fetch data.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("cities") }) {
            Text("Back to Cities")
        }
    }
}
