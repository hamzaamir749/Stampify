package com.coderpakistan.design.presentation.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture

data class CameraState(
    var lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    var zoomLevel: Float = 0.0f,
    var flash: Int = ImageCapture.FLASH_MODE_OFF,
    var isCapturing: Boolean = false,
    var isCameraInitialized: Boolean = false,
    val errorMessage: String? = null,
    val showLocationRationale: Boolean = false,
    val showCameraRationale: Boolean = false,
    val hasCamera: Boolean = false,
    val hasLocation: Boolean = false,
    val isGpsEnabled: Boolean = false,

    //Settings
    val enableWaterMark: Boolean = false,
    val enableAutoLocation: Boolean = false,
    val timeStamp: String = "MM/dd/yyyy hh:mmm:ss a",
    val color: Int = android.graphics.Color.WHITE
)