package com.example.weatherapp.ui.weather

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    cityName: String
) {
    val weather by weatherViewModel.weather.collectAsState()
    val forecast by weatherViewModel.forecast.collectAsState()

    println("Valor de cityName en WeatherScreen: $cityName")
    println("Valor de weather en WeatherScreen: $weather")
    println("Valor de forecast en WeatherScreen: $forecast")

    // Efecto lanzado cuando cambia el nombre de la ciudad
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

            // Verificar la URL del icono
            val iconUrl = "https:${weatherData.icon}"
            println("Icon URL: $iconUrl")

            // Recuadro para mostrar el clima
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFADD8E6)) // Color celeste
                    .padding(16.dp)
            ) {
                Row {
                    // Carga del icono del tiempo
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = iconUrl)
                                .apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                    placeholder(R.drawable.ic_menu_report_image)
                                    error(R.drawable.ic_menu_report_image)
                                }).build()
                        ),
                        contentDescription = "Weather Icon",
                        modifier = Modifier
                            .padding(end = 1.dp)
                            .height(140.dp)
                            .width(85.dp)
                    )

                    Column {
                        println("Construyendo la columna con datos del clima en WeatherScreen")
                        Text("Ciudad: $cityName")
                        Text("País: ${weatherData.country}")
                        Text("Temperatura: ${weatherData.temp_c} °C")
                        Text("Humedad: ${weatherData.humidity}%")
                        Text("Condición: ${weatherData.condition}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forecast Box
            if (forecast != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFADD8E6)) // Color celeste
                        .padding(16.dp)
                ) {
                    Column {
                        forecast!!.forEach { forecastDay ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                val forecastIconUrl = "https:${forecastDay.day.condition.icon}"
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current).data(data = forecastIconUrl)
                                            .apply {
                                                crossfade(true)
                                                placeholder(R.drawable.ic_menu_report_image)
                                                error(R.drawable.ic_menu_report_image)
                                            }.build()
                                    ),
                                    contentDescription = "Forecast Icon",
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .height(64.dp)
                                        .width(64.dp)
                                )

                                Column {

                                    Text("Fecha: ${forecastDay.date}")
                                    Text("Temperatura Mínima: ${forecastDay.day.mintemp_c} °C")
                                    Text("Temperatura Máxima: ${forecastDay.day.maxtemp_c} °C")
                                    Text("Condición: ${forecastDay.day.condition.text}")
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Muestra un mensaje de carga o error si los datos no están disponibles
            println("Weather data is null en WeatherScreen")
            Text("Loading weather data or unable to fetch data.")
        }

        Spacer(modifier = Modifier.height(16.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row {

                Button(onClick = { navController.navigate("cities") }) {
                    Text("Change City")
                }
                Spacer(modifier = Modifier.width(63.dp))

                Column {
                    //Usare este boton para compartir el pronóstico (aun no implementado)
                    Button(onClick = { navController.navigate("cities") }) {
                        Text("Share Forecast")
                    }
                }
            }

        }

    }
}
