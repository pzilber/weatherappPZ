package com.example.weatherapp.data.model

data class Weather(
    val name: String,
    val country: String,
    val temp_c: Float,
    val humidity: Int,
    val condition: String,
    val icon: String
)
