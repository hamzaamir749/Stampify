package com.coderpakistan.design.presentation.camera

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.coderpakistan.design.presentation.util.checkGPSEnable
import com.coderpakistan.design.presentation.util.createFile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.concurrent.Executors

class CameraViewModel : ViewModel() {

    var state by mutableStateOf(
        CameraState(
        )
    )
        private set
    private val eventChannel = Channel<CameraEvent>()
    val events = eventChannel.receiveAsFlow()

    private val hasCameraPermission = MutableStateFlow(false)
    private val hasLocationPermission = MutableStateFlow(false)


    fun onAction(action: CameraAction) {
        when (action) {
            is CameraAction.CaptureCameraPhoto -> {
                capture(controller = action.controller, context = action.context)
            }

            CameraAction.InitializeCamera -> {}
            CameraAction.SelectCapturedCameraPhoto -> {}
            is CameraAction.SetZoomLevel -> {
                setZoom(controller = action.controller, zoomLevel = action.zoomlevel)
            }

            is CameraAction.SubmitCameraPermissionInfo -> {
                hasCameraPermission.value = action.acceptedCameraPermission
                state = state.copy(
                    showCameraRationale = action.showCameraRationale,
                    hasCamera = action.acceptedCameraPermission
                )
            }

            is CameraAction.SubmitLocationPermissionInfo -> {
                hasLocationPermission.value = action.acceptedLocationPermission
                state = state.copy(
                    showLocationRationale = action.showLocationRationale,
                    hasLocation = action.acceptedLocationPermission
                )
            }

            CameraAction.DismissRationaleDialog -> {
                state = state.copy(
                    showCameraRationale = false, showLocationRationale = false
                )
            }

            is CameraAction.SwitchCamera -> {
                switchCamera(action.controller)
            }

            is CameraAction.SwitchFlash -> {
                toggleFlash(action.controller)
            }

            is CameraAction.CheckGpsEnableInfo -> {
                checkGpsEnable(action.context)
            }
        }
    }

    fun checkGpsEnable(context: Context) {
        context.checkGPSEnable { isEnable ->
            state = state.copy(
                isGpsEnabled = isEnable
            )
        }
    }

    private fun toggleFlash(controller: LifecycleCameraController?) {
        controller ?: return
        val newMode = when (controller.imageCaptureFlashMode) {
            ImageCapture.FLASH_MODE_OFF -> ImageCapture.FLASH_MODE_ON
            else -> ImageCapture.FLASH_MODE_OFF
        }
        controller.imageCaptureFlashMode = newMode
        state = state.copy(flash = newMode)
    }

    private fun switchCamera(controller: LifecycleCameraController?) {
        controller ?: return
        val now = state.lensFacing
        val newFacing = if (now == CameraSelector.LENS_FACING_BACK) {
            CameraSelector.LENS_FACING_FRONT
        } else CameraSelector.LENS_FACING_BACK
        controller.cameraSelector = CameraSelector.Builder().requireLensFacing(newFacing).build()
        state = state.copy(lensFacing = newFacing)
    }

    private fun setZoom(controller: LifecycleCameraController?, zoomLevel: Float) {
        controller ?: return
        val clamped = zoomLevel.coerceIn(0f, 1f)
        controller.setLinearZoom(clamped)
        state = state.copy(zoomLevel = clamped)
    }

    private fun capture(controller: LifecycleCameraController?, context: Context) {
        controller ?: return
        if (state.isCapturing) return
        state = state.copy(isCapturing = true)

        val file = context.createFile()

        val executor = Executors.newSingleThreadExecutor()
        controller.takePicture(
            file,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = outputFileResults.savedUri
                    Log.d("TAG Save Image success", "onImageSaved: ${uri.toString()}")
                    state = state.copy(isCapturing = false)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d("TAG Save Image error", "onImageSaved: ${exception.toString()}")
                    state = state.copy(isCapturing = false)
                }
            }
        )
    }
}