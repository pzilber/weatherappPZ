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

    LaunchedEffect(cityName) {
        weatherViewModel.loadWeather(cityName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (weather != null) {
            val weatherData = weather!!

            val iconUrl = "https:${weatherData.icon}"

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFADD8E6)) // Color celeste
                    .padding(16.dp)
            ) {
                Row {
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
                        Text("Ciudad: $cityName")
                        Text("País: ${weatherData.country}")
                        Text("Temperatura: ${weatherData.temp_c} °C")
                        Text("Humedad: ${weatherData.humidity}%")
                        Text("Condición: ${weatherData.condition}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                                        ImageRequest.Builder(LocalContext.current)
                                            .data(data = forecastIconUrl)
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
                    Button(onClick = { /* Acción de compartir pronóstico */ }) {
                        Text("Share Forecast")
                    }
                }
            }
        }
    }
}
