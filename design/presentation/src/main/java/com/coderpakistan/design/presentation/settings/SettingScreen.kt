package com.coderpakistan.design.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderpakistan.core.presentation.designsystem.StampifyTheme

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    var watermarkEnabled by remember { mutableStateOf(false) }
    var locationEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Timestamp Format
        SettingHeader("Timestamp Format")
        SettingRow(
            title = "MM/dd/yyyy hh:mm:ss a",
            subtitle = "Default",
            onClick = { /* Open timestamp format picker */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save Directory
        SettingHeader("Save Directory")
        SettingRow(
            title = "Pictures/Timestamp Photo App",
            subtitle = "Default",
            onClick = { /* Open folder picker */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Watermark toggle
        SettingHeader("Watermark")
        SwitchRow(
            title = "Enable Watermark",
            checked = watermarkEnabled,
            onCheckedChange = { watermarkEnabled = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Location toggle
        SettingHeader("Location")
        SwitchRow(
            title = "Enable Auto-Location Tag",
            checked = locationEnabled,
            onCheckedChange = { locationEnabled = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Privacy Section
        SettingHeader("Privacy")
        Text(
            text = "Your location data is stored locally on your device and is not shared with any third parties. " +
                    "We respect your privacy and ensure your data remains secure.",
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun SettingHeader(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun SettingRow(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = title, fontSize = 14.sp)
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null
        )
    }
}

@Composable
fun SwitchRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 14.sp)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    StampifyTheme {
        SettingsScreen()
    }
}