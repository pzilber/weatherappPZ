package com.example.weatherapp.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.weatherapp.data.model.City
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class LocationManager @Inject constructor(
    private val context: Context
) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    suspend fun getCurrentLocation(): City? {
        val hasLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasLocationPermission) {
            return null
        }

        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val city = getCityFromLocation(location)
                    continuation.resume(city)
                } else {
                    continuation.resume(null)
                }
            }.addOnFailureListener {
                continuation.resume(null)
            }
        }
    }

    private fun getCityFromLocation(location: Location): City? {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)!!

        if (addresses.isNotEmpty()) {
            val address = addresses[0]
            val cityName = address.locality ?: ""
            val countryCode = address.countryCode ?: ""
            return City(cityName, countryCode)
        }

        return null
    }
}