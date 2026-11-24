package com.example.alcometer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AlcoPurple,
    onPrimary = Color.White,
    secondary = AlcoTeal,
    onSecondary = Color.White,
    tertiary = AlcoTealDark,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE)
)

private val DarkColorScheme = darkColorScheme(
    primary = AlcoPurpleLight,
    onPrimary = Color.White,
    secondary = AlcoTeal,
    onSecondary = Color.Black,
    tertiary = AlcoTeal
)

@Composable
fun AlcometerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
