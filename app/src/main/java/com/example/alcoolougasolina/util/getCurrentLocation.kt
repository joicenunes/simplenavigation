package com.example.alcoolougasolina.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.location.FusedLocationProviderClient
import android.location.Location

fun getCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationFetched: (Location?) -> Unit
) {
    if (ContextCompat
            .checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat
            .checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
    ) {
        val cancellationTokenSource = CancellationTokenSource()
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location: Location? ->
            onLocationFetched(location)
        }.addOnFailureListener { _ ->
            fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation: Location? ->
                onLocationFetched(lastLocation)
            }.addOnFailureListener { _ ->
                onLocationFetched(null)
            }
        }
    }
}