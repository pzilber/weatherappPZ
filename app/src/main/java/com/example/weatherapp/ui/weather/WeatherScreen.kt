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
import com.example.weatherapp.viewmodel.CityViewModel
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    navController: NavController,
    cityViewModel: CityViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    val selectedCity by cityViewModel.selectedCity.collectAsState()
    val weather by weatherViewModel.weather.collectAsState()

    println("Valor de selectedCity en WeatherScreen: $selectedCity")
    println("Valor de weather en WeatherScreen: $weather")

    // Efecto lanzado cuando cambia la ciudad seleccionada
    LaunchedEffect(selectedCity) {
        selectedCity?.let {
            println("Ciudad seleccionada en WeatherScreen: ${it.name}")
            weatherViewModel.loadWeather(it)
        }
    }

    // Columna principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Si los datos del clima no son nulos, mostrar los datos
        println("Entrando a buscar datos del clima en WeatherScreen")
        weather?.let { weatherData ->
            println("Datos del clima en WeatherScreen: $weatherData")
            println("Weather data en WeatherScreen: $weather")

            // Recuadro para mostrar el clima
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFADD8E6)) // Color celeste
                    .padding(16.dp)
            ) {
                Column {
                    println("Construyendo la columna con datos del clima en WeatherScreen")
                    Text("Ciudad: ${selectedCity?.name}")
                    Text("País: ${weatherData.country}")
                    Text("Temperatura: ${weatherData.temp_c} °C")
                    Text("Humedad: ${weatherData.humidity}%")
                    Text("Condición: ${weatherData.condition}")

                    Spacer(modifier = Modifier.height(16.dp))
                    /*
                    Text("7-Day Forecast:")
                    LazyRow {
                        items(weatherData.forecast) { forecast ->
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Day: ${forecast.day}")
                                Text("Max: ${forecast.maxTemp} °C")
                                Text("Min: ${forecast.minTemp} °C")
                            }
                        }
                    }
                    */
                }
            }
        }

        /*            Spacer(modifier = Modifier.height(16.dp))

                    // Recuadro para el gráfico de barras
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color(0xFFADD8E6)) // Color celeste
                            .padding(16.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val barWidth = (size.width / (weather?.forecast?.size * 2)).toFloat()
                            val maxTemp = weatherData.forecast.maxOf { it.maxTemp }.toFloat()

                            weatherData.forecast.forEachIndexed { index, forecast ->
                                val barHeight = (forecast.maxTemp / maxTemp * size.height).toFloat()
                                drawRect(
                                    color = Color.Blue,
                                    topLeft = androidx.compose.ui.geometry.Offset(
                                        x = index * 2 * barWidth,
                                        y = size.height - barHeight
                                    ),
                                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
                                )
                            }
                        }
                    }
                }
                */

            ?: run {
                // Muestra un mensaje de carga o error si los datos no están disponibles
                Text("Loading weather data or unable to fetch data.")
                println("Weather data is null")
            }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("cities") }) {
            Text("Change City")
        }
    }
}
