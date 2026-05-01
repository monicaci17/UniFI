package com.example.unifi.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColors = darkColorScheme(
    primary = Color(0xFF1E3A5F),
    onPrimary = Color.White,

    secondary = Color(0xFF4A6FA5),
    onSecondary = Color.White,

    tertiary = Color(0xFF00A8A8),
    onTertiary = Color(0xFFA7C7E7),

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),

    surface = Color(0xFF1C2C48),
    onSurface = Color.White,

    error = Color(0xFFCF6679),
    onError = Color.Black,

    primaryContainer = Color(0xFF2C4F7C),
    onPrimaryContainer = Color(0xFFD6E4FF),
    secondaryContainer = Color(0xFF3B5C8A),
    onSecondaryContainer = Color(0xFFDCE3F0)
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF3A7BD5),
    onPrimary = Color.White,

    secondary = Color(0xFFA7C7E7),
    onSecondary = Color.Black,

    tertiary = Color(0xFF00A2BF),
    onTertiary = Color(0xFF3A7BD5),

    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1C1C1C),

    surface = Color(0xFFB8E3FF),
    onSurface = Color(0xFF1C1C1C),

    error = Color(0xFFB00020),
    onError = Color.White,

    primaryContainer = Color(0xFFD6E4FF),
    onPrimaryContainer = Color(0xFF0B2545),

    secondaryContainer = Color(0xFFE3F0FF),
    onSecondaryContainer = Color(0xFF1A3A5F)
)

@Composable
fun UnifiTheme(
    // Dynamic color is available on Android 12+
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        typography = Typography,
        colorScheme = colors,
        content = content
    )
}