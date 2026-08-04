package com.santhosh.dailyactivitytracker.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6EC9FF),
    onPrimary = Color(0xFF003D5C),
    primaryContainer = Color(0xFF005499),
    onPrimaryContainer = Color(0xFFB3E5FF),
    secondary = Color(0xFFB0CAE2),
    onSecondary = Color(0xFF1E3446),
    secondaryContainer = Color(0xFF354D5D),
    onSecondaryContainer = Color(0xFFCCD7E8),
    tertiary = Color(0xFFC2BEDC),
    onTertiary = Color(0xFF352E5C),
    tertiaryContainer = Color(0xFF4D4574),
    onTertiaryContainer = Color(0xFFDDD9F8),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF0F1419),
    onBackground = Color(0xFFE8EAF1),
    surface = Color(0xFF0F1419),
    onSurface = Color(0xFFE8EAF1),
    surfaceVariant = Color(0xFF48525F),
    onSurfaceVariant = Color(0xFFD0D9E8),
    outline = Color(0xFF9AA3B1),
    outlineVariant = Color(0xFF48525F),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFE8EAF1),
    inverseOnSurface = Color(0xFF363B42),
    inversePrimary = Color(0xFF0E68B4)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0E68B4),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD4E7FF),
    onPrimaryContainer = Color(0xFF001D3A),
    secondary = Color(0xFF52658F),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD9E7FF),
    onSecondaryContainer = Color(0xFF0D1E33),
    tertiary = Color(0xFF65597E),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFEBDEFF),
    onTertiaryContainer = Color(0xFF1F1636),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFFFBFCFE),
    onBackground = Color(0xFF1A1C20),
    surface = Color(0xFFFBFCFE),
    onSurface = Color(0xFF1A1C20),
    surfaceVariant = Color(0xFFDDE1EB),
    onSurfaceVariant = Color(0xFF48525F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFC4C7D0),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF2F3135),
    inverseOnSurface = Color(0xFFF1F2F6),
    inversePrimary = Color(0xFFB3E5FF)
)

@Composable
fun DailyActivityTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = androidx.compose.ui.platform.LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)?.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

fun getPriorityColor(priority: Int): Color {
    return when (priority) {
        1 -> Color(0xFF90EE90) // Green for Low
        2 -> Color(0xFFFFD700) // Gold for Medium
        3 -> Color(0xFFFF6B6B) // Red for High
        else -> Color(0xFF90EE90)
    }
}
