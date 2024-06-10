package com.example.weatherapp.ui.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.viewmodel.CityViewModel

@Composable
fun CityScreen(navController: NavController, cityViewModel: CityViewModel = hiltViewModel()) {
    var cityName by remember { mutableStateOf(TextFieldValue("")) }
    val cities by cityViewModel.cities.collectAsState()

    LaunchedEffect(Unit) {
        cityViewModel.loadCities()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextField(
                value = cityName,
                onValueChange = { cityName = it },
                label = { Text("Enter city name") },
                modifier = Modifier
                    .weight(1f)
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Enter) {
                            cityViewModel.searchCity(cityName.text)
                            true
                        } else {
                            false
                        }
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { cityViewModel.searchCity(cityName.text) },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { cityViewModel.findCurrentLocation() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Use Current Location")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(cities) { city ->
                Text(
                    text = city.name,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            cityViewModel.selectCity(city)
                            println("Navegando (CitiesScreen antes de navController) a la pantalla del clima para la ciudad: ${city.name}")
                            navController.navigate("weather")
                            println("Navegando (CitiesScreen despues del navController) a la pantalla del clima para la ciudad: ${city.name}")
                        }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
