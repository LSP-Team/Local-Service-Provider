package com.techfinder.localserviceprovider.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale

@SuppressLint("MissingPermission")
fun fetchCurrentLocation(
    context: Context,
    onLocationFetched: (latitude: Double, longitude: Double, address: String) -> Unit,
    onError: (String) -> Unit
){

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        .addOnSuccessListener { location ->
            if (location != null){
                val lat = location.latitude
                val lng = location.longitude


                val geocoder = Geocoder(context, Locale.getDefault())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(lat, lng, 1) { addresses ->
                        val addressText = addresses?.firstOrNull()?.getAddressLine(0) ?: ""
                        onLocationFetched(lat, lng, addressText)
                    }
                } else{
                    onError("Failed to get location. Please turn on GPS")
                }
            }
        }
        .addOnFailureListener {
            onError(it.localizedMessage ?: "Error fetching location")
        }
}