package com.coderpakistan.design.presentation.camera

import android.content.Context
import androidx.camera.view.LifecycleCameraController

sealed interface CameraAction {
    object InitializeCamera : CameraAction
    data class SwitchCamera(
        val context: Context,
        val controller: LifecycleCameraController?
    ) : CameraAction

    object SelectCapturedCameraPhoto : CameraAction
    data class SwitchFlash(
        val context: Context,
        val controller: LifecycleCameraController?
    ) : CameraAction

    data class CaptureCameraPhoto(
        val context: Context,
        val controller: LifecycleCameraController?
    ) : CameraAction

    data class SetZoomLevel(
        val context: Context,
        val controller: LifecycleCameraController?,
        val zoomlevel: Float
    ) : CameraAction

    data class SubmitLocationPermissionInfo(
        val acceptedLocationPermission: Boolean,
        val showLocationRationale: Boolean
    ) : CameraAction

    data class SubmitCameraPermissionInfo(
        val acceptedCameraPermission: Boolean,
        val showCameraRationale: Boolean
    ) : CameraAction

    data class CheckGpsEnableInfo(
        val context: Context
    ) : CameraAction

    data object DismissRationaleDialog : CameraAction

}