package com.techfinder.localserviceprovider.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LocalServiceProviderDarkColorScheme = darkColorScheme(

    // Primary
    primary = PrimaryBlue,
    onPrimary = TextOnPrimary,
    primaryContainer = PrimaryBlueLight,
    onPrimaryContainer = TextPrimary,

    // Secondary
    secondary = AccentTeal,
    onSecondary = TextOnAccent,
    secondaryContainer = AccentTealLight,
    onSecondaryContainer = TextPrimary,

    // Tertiary
    tertiary = AccentAmber,
    onTertiary = Color(0xFF1A1200),
    tertiaryContainer = AccentAmberLight,
    onTertiaryContainer = TextPrimary,

    // Error
    error = StatusError,
    onError = TextOnPrimary,
    errorContainer = StatusErrorLight,
    onErrorContainer = TextPrimary,

    // Background
    background = AppBackground,
    onBackground = TextPrimary,

    // Surface
    surface = SurfaceCard,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceRaised,
    onSurfaceVariant = TextSecondary,

    // Borders
    outline = BorderDefault,
    outlineVariant = BorderSubtle,

    // Inverse
    inverseSurface = TextPrimary,
    inverseOnSurface = AppBackground,
    inversePrimary = PrimaryBlueDark
)

@Composable
fun LocalServiceProviderTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LocalServiceProviderDarkColorScheme,
        typography = Typography,
        content = content
    )
}