package com.coderpakistan.design.presentation.camera

import com.coderpakistan.core.presentation.ui.UiText

sealed interface CameraEvent {
    data class Error(val error: UiText) : CameraEvent
    data object CapturedSaved : CameraEvent
}