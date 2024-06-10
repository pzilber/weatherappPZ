package com.example.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.cities.CityScreen
import com.example.weatherapp.ui.weather.WeatherScreen
import com.example.weatherapp.viewmodel.CityViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "cities") {
        composable("cities") {
            val cityViewModel = hiltViewModel<CityViewModel>()
            CityScreen(navController = navController, cityViewModel = cityViewModel)
        }
        composable("weather") {
            val cityViewModel = hiltViewModel<CityViewModel>()
            WeatherScreen(navController = navController, cityViewModel = cityViewModel)
        }
    }
}
