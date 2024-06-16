package com.example.weatherapp.data.network

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
                response.toWeatherResponse()
            }
        } catch (e: Exception) {
            println("Error al obtener el clima: ${e.message}")
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
        condition = this.current.condition.text
    )
}

/*
    suspend fun searchCities(cities: List<City>): List<WeatherResponse> {
        val weatherResponses = mutableListOf<WeatherResponse>()
        for (city in cities) {
            val response: WeatherResponse = weatherApiService.getWeather("690f96c414f84eb19ad04207242805", city.name)
            weatherResponses.add(response)
        }
        return weatherResponses
    }

*/

/*
fun WeatherResponse.toWeatherResponse(): Weather {
    return Weather(
        temp_c = this.current.temp_c,
        humidity = this.current.humidity,
        condition = this.current.condition.text,
        forecast = this.forecast.forecastday.map { day ->
            Weather.Forecast(
                day = day.date,
                maxTemp = day.day.maxTemp,
                minTemp = day.day.minTemp
            )
        }
    )
}
*/