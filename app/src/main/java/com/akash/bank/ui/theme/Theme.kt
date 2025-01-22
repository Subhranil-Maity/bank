package com.akash.bank.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF007BFF),
    onPrimary = Color.White,
    secondary = Color(0xFF6C757D),
    onSecondary = Color.White,
    background = Color.Transparent,
    onBackground = Color.Black,
    surface = Color.Transparent,
    onSurface = Color.Black
)


@Composable
fun BankTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}