package com.coderpakistan.core.presentation.designsystem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StampifyScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topAppBar,
        bottomBar = bottomBar,
        modifier = modifier
    ) { padding ->
        content(padding)
    }
}