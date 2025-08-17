package com.coderpakistan.design.presentation.camera

import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Cameraswitch
import androidx.compose.material.icons.rounded.FlashOff
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.coderpakistan.core.presentation.designsystem.StampifyTheme
import com.coderpakistan.core.presentation.designsystem.components.StampifyDialog
import com.coderpakistan.core.presentation.designsystem.components.StampifyOutlinedActionButton
import com.coderpakistan.design.presentation.R
import com.coderpakistan.design.presentation.util.hasCameraPermission
import com.coderpakistan.design.presentation.util.hasLocationPermission
import com.coderpakistan.design.presentation.util.requestStampifyPermissions
import com.coderpakistan.design.presentation.util.shouldCameraPermissionRationale
import com.coderpakistan.design.presentation.util.shouldShowLocationPermissionRationale
import org.koin.androidx.compose.koinViewModel


@Composable
fun CameraScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = koinViewModel()
) {
    CameraScreen(
        modifier = modifier,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CameraScreen(
    modifier: Modifier = Modifier,
    state: CameraState,
    onAction: (CameraAction) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasCourseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasCameraPermission = perms[Manifest.permission.CAMERA] == true
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showCameraRationale = activity.shouldCameraPermissionRationale()
        onAction(
            CameraAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = hasCourseLocationPermission && hasFineLocationPermission,
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            CameraAction.SubmitCameraPermissionInfo(
                acceptedCameraPermission = hasCameraPermission,
                showCameraRationale = showCameraRationale
            )
        )

    }
    // GPS settings launcher
    val gpsEnableLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        /* onAction(
             CameraAction.CheckGpsEnableInfo(context)
         )*/
    }



    if (!state.isGpsEnabled && state.enableAutoLocation)
        StampifyDialog(
            title = stringResource(id = R.string.gps_required),
            onDismiss = { /* Normal dismissing not allowed for permissions */ },
            description = stringResource(id = R.string.enable_gps_rationale),
            primaryButton = {
                StampifyOutlinedActionButton(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(CameraAction.DismissRationaleDialog)
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        gpsEnableLauncher.launch(intent)
                    }
                )
            }
        )

    LaunchedEffect(key1 = true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showCameraRationale = activity.shouldCameraPermissionRationale()

        onAction(
            CameraAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            CameraAction.SubmitCameraPermissionInfo(
                acceptedCameraPermission = context.hasCameraPermission(),
                showCameraRationale = showCameraRationale
            )
        )

        if (!showLocationRationale && !showCameraRationale) {
            permissionLauncher.requestStampifyPermissions(context)
        }


    }

    val lifecycleOwner = LocalLifecycleOwner.current

    // Controller lives in the Composition and survives recomposition
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                LifecycleCameraController.IMAGE_CAPTURE or LifecycleCameraController.VIDEO_CAPTURE
            )
        }
    }
    val flip = remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (flip.value) 180f else 0f,
        animationSpec = tween(500),
        label = "flip"
    )
    // Attach lifecycle
    LaunchedEffect(lifecycleOwner) {
        controller.bindToLifecycle(lifecycleOwner)
    }


    // Side-effect: listen for effects
    LaunchedEffect(Unit) {
        /*vm.effects.collect { eff ->
            when (eff) {
                is CameraEffect.ToastMsg -> Toast.makeText(ctx, eff.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }*/
    }


    Box(modifier = modifier.fillMaxSize()) {
        // Preview
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    this@graphicsLayer.rotationY
                    cameraDistance = 12 * density // prevents flattening
                },
            factory = { context ->
                PreviewView(context).apply {
                    this.controller = controller
                    this.scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            },
            update = { it.controller = controller }
        )

        // Top controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalIconButton(onClick = {
                flip.value = !flip.value
                onAction(
                    CameraAction.SwitchCamera(
                        context = context,
                        controller = controller
                    )
                )

            }) {
                Icon(Icons.Rounded.Cameraswitch, contentDescription = "Switch camera")
            }
            FilledTonalIconButton(onClick = {
                onAction(
                    CameraAction.SwitchFlash(
                        context = context,
                        controller = controller
                    )
                )
            }) {
                Icon(
                    if (state.flash == ImageCapture.FLASH_MODE_ON) Icons.Rounded.FlashOn else Icons.Rounded.FlashOff,
                    contentDescription = "Flash"
                )
            }
        }

        // Shutter button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 28.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.size(84.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.9f)
            ) {
                Box(Modifier.fillMaxSize())
            }
            Button(
                onClick = {

                    onAction(
                        CameraAction.CaptureCameraPhoto(
                            context = context,
                            controller = controller
                        )
                    )
                },
                modifier = Modifier
                    .size(72.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) { Icon(Icons.Rounded.Camera, contentDescription = "Capture") }
        }

        // Vertical zoom slider on the right
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 8.dp)
                .align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ) {
            // Rotate a horizontal Slider by -90 degrees to make it vertical
            Box(
                modifier = Modifier
                    .height(240.dp)
                    .width(48.dp)
                    .graphicsLayer(rotationZ = -90f),
                contentAlignment = Alignment.Center
            ) {
                Slider(
                    value = state.zoomLevel,
                    onValueChange = { value ->
                        onAction(
                            CameraAction.SetZoomLevel(
                                context = context,
                                controller = controller,
                                zoomlevel = value
                            )
                        )
                    },
                    valueRange = 0f..1f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }


    if (state.showLocationRationale || state.showCameraRationale) {
        StampifyDialog(
            title = stringResource(id = R.string.permission_required),
            onDismiss = { /* Normal dismissing not allowed for permissions */ },
            description = when {
                state.showLocationRationale && state.showCameraRationale -> {
                    stringResource(id = R.string.location_camera_rationale)
                }

                state.showLocationRationale -> {
                    stringResource(id = R.string.location_rationale)
                }

                else -> {
                    stringResource(id = R.string.camera_rationale)
                }
            },
            primaryButton = {
                StampifyOutlinedActionButton(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(CameraAction.DismissRationaleDialog)
                        permissionLauncher.requestStampifyPermissions(context)
                    }
                )
            }
        )
    }

}


@Preview
@Composable
private fun CameraScreenRootScreenPreview() {
    StampifyTheme {
        CameraScreen(
            state = CameraState(),
            onAction = {}
        )

    }

}