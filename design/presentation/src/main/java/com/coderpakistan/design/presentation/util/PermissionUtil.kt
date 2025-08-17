package com.coderpakistan.design.presentation.util

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.camera.core.ImageCapture
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun ComponentActivity.shouldShowLocationPermissionRationale(): Boolean {
    return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
}

fun ComponentActivity.shouldCameraPermissionRationale(): Boolean {
    return shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
}

private fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasLocationPermission(): Boolean {
    return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
}

fun Context.hasCameraPermission(): Boolean {
    return hasPermission(Manifest.permission.CAMERA)

}

fun ActivityResultLauncher<Array<String>>.requestStampifyPermissions(
    context: Context
) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasCameraPermission = context.hasCameraPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val cameraPermission =
        arrayOf(Manifest.permission.CAMERA)


    when {
        !hasLocationPermission && !hasCameraPermission -> {
            launch(locationPermissions + cameraPermission)
        }

        !hasLocationPermission -> launch(locationPermissions)
        !hasCameraPermission -> launch(cameraPermission)
    }
}

fun Context.checkGPSEnable(
    launcherCheck: ((Boolean) -> Unit)? = null
) {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    val isEnabled = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        // API 28+
        locationManager?.isLocationEnabled == true
    } else {
        // Below API 28
        val gpsEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
        val networkEnabled =
            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
        gpsEnabled || networkEnabled
    }
    launcherCheck?.invoke(isEnabled)

}

fun Context.createFile(): ImageCapture.OutputFileOptions {
    val name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_${'$'}name")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-MVI")
        }
    }
    return ImageCapture.OutputFileOptions.Builder(
        this.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ).build()
}