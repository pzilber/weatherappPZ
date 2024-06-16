package com.example.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.ui.cities.CityScreen
import com.example.weatherapp.ui.weather.WeatherScreen
import com.example.weatherapp.viewmodel.CityViewModel
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "cities") {
        composable("cities") {
            val cityViewModel = hiltViewModel<CityViewModel>()
            CityScreen(navController = navController, cityViewModel = cityViewModel)
        }
        composable(
            "weather/{cityName}",
            arguments = listOf(navArgument("cityName") { type = NavType.StringType })
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: return@composable
            val weatherViewModel = hiltViewModel<WeatherViewModel>()
            WeatherScreen(navController = navController, weatherViewModel = weatherViewModel, cityName = cityName)
        }
    }
}
