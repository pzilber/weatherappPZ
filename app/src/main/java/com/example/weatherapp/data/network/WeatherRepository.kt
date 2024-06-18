package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.ForecastDay
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApiService: WeatherApiService) {

    suspend fun getWeather(query: String): Weather? {
        return try {
            val response: WeatherResponse = weatherApiService.getWeather("690f96c414f84eb19ad04207242805", query = query)
            println("API response: $response")
            run {
                println("Pais en WeatherRepository: ${response.location.name}")
                println("country en WeatherRepository: ${response.location.country}")
                println("Temperature en WeatherRepository: ${response.current.temp_c}")
                println("Humidity en WeatherRepository: ${response.current.humidity}")
                println("Condition en WeatherRepository: ${response.current.condition.text}")
                println("Condition en WeatherRepository: ${response.current.condition.icon}")
                response.toWeatherResponse()
            }
        } catch (e: Exception) {
            println("Error al obtener el clima: ${e.message}")
            null
        }
    }

    suspend fun getForecast(query: String, days: Int): List<ForecastDay>? {
        return try {
            val response: WeatherResponse = weatherApiService.getForecast("690f96c414f84eb19ad04207242805", query, days)
            println("Forecast API response: $response")
            response.forecast.forecastday
        } catch (e: Exception) {
            println("Error al obtener el pronóstico: ${e.message}")
            null
        }
    }
}


fun WeatherResponse.toWeatherResponse(): Weather {
    return Weather(
        name = this.location.name,
        country = this.location.country,
        temp_c = this.current.temp_c,
        humidity = this.current.humidity,
        condition = this.current.condition.text,
        icon = this.current.condition.icon
    )
}
