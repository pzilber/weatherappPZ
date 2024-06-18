package com.example.weatherapp.data.model

data class WeatherResponse(
    val current: CurrentWeather,
    val location: Location,
     val forecast: Forecast
)

data class Location(
    val name: String,
    val country: String
)

data class CurrentWeather(
    val temp_c: Float,
    val humidity: Int,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day
)

data class Day(
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val condition: Condition
)
