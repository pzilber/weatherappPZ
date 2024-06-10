package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): WeatherResponse
}

/*


package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {
    @GET("search.json?")
    /*
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("&") ampersand: String,
        @Query("q") query: String
    ): WeatherResponse
    */

    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): WeatherResponse
}

*/
/*
interface WeatherApiService {
    @GET("/search.json?")
    suspend fun getWeather(
        @Query("city") city: String,
        @Query("days") days: Int = 7 // Supone que queremos un pronóstico de 7 días
    ): WeatherResponse
}

*/
/*

package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/*
interface WeatherApiService {
    @GET("forecast.json?")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): WeatherResponse
}

*/
/*
interface WeatherApiService {
    @GET("current.json?")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("aqi") days: String
    ): WeatherResponse
}

*/