package com.seyone22.cafeteriaRatings.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.seyone22.fingerprinter.ui.theme.Pink40
import com.seyone22.fingerprinter.ui.theme.Pink80
import com.seyone22.fingerprinter.ui.theme.Purple40
import com.seyone22.fingerprinter.ui.theme.Purple80
import com.seyone22.fingerprinter.ui.theme.PurpleGrey40
import com.seyone22.fingerprinter.ui.theme.PurpleGrey80
import com.seyone22.fingerprinter.ui.theme.backgroundDark
import com.seyone22.fingerprinter.ui.theme.backgroundDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.backgroundDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.backgroundLight
import com.seyone22.fingerprinter.ui.theme.backgroundLightHighContrast
import com.seyone22.fingerprinter.ui.theme.backgroundLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.errorContainerDark
import com.seyone22.fingerprinter.ui.theme.errorContainerDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.errorContainerDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.errorContainerLight
import com.seyone22.fingerprinter.ui.theme.errorContainerLightHighContrast
import com.seyone22.fingerprinter.ui.theme.errorContainerLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.errorDark
import com.seyone22.fingerprinter.ui.theme.errorDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.errorDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.errorLight
import com.seyone22.fingerprinter.ui.theme.errorLightHighContrast
import com.seyone22.fingerprinter.ui.theme.errorLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.inverseOnSurfaceDark
import com.seyone22.fingerprinter.ui.theme.inverseOnSurfaceDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.inverseOnSurfaceDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.inverseOnSurfaceLight
import com.seyone22.fingerprinter.ui.theme.inverseOnSurfaceLightHighContrast
import com.seyone22.fingerprinter.ui.theme.inverseOnSurfaceLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.inversePrimaryDark
import com.seyone22.fingerprinter.ui.theme.inversePrimaryDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.inversePrimaryDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.inversePrimaryLight
import com.seyone22.fingerprinter.ui.theme.inversePrimaryLightHighContrast
import com.seyone22.fingerprinter.ui.theme.inversePrimaryLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.inverseSurfaceDark
import com.seyone22.fingerprinter.ui.theme.inverseSurfaceDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.inverseSurfaceDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.inverseSurfaceLight
import com.seyone22.fingerprinter.ui.theme.inverseSurfaceLightHighContrast
import com.seyone22.fingerprinter.ui.theme.inverseSurfaceLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onBackgroundDark
import com.seyone22.fingerprinter.ui.theme.onBackgroundDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onBackgroundDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onBackgroundLight
import com.seyone22.fingerprinter.ui.theme.onBackgroundLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onBackgroundLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onErrorContainerDark
import com.seyone22.fingerprinter.ui.theme.onErrorContainerDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onErrorContainerDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onErrorContainerLight
import com.seyone22.fingerprinter.ui.theme.onErrorContainerLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onErrorContainerLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onErrorDark
import com.seyone22.fingerprinter.ui.theme.onErrorDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onErrorDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onErrorLight
import com.seyone22.fingerprinter.ui.theme.onErrorLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onErrorLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onPrimaryContainerDark
import com.seyone22.fingerprinter.ui.theme.onPrimaryContainerDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onPrimaryContainerDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onPrimaryContainerLight
import com.seyone22.fingerprinter.ui.theme.onPrimaryContainerLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onPrimaryContainerLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onPrimaryDark
import com.seyone22.fingerprinter.ui.theme.onPrimaryDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onPrimaryDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onPrimaryLight
import com.seyone22.fingerprinter.ui.theme.onPrimaryLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onPrimaryLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onSecondaryContainerDark
import com.seyone22.fingerprinter.ui.theme.onSecondaryContainerDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onSecondaryContainerDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onSecondaryContainerLight
import com.seyone22.fingerprinter.ui.theme.onSecondaryContainerLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onSecondaryContainerLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onSecondaryDark
import com.seyone22.fingerprinter.ui.theme.onSecondaryDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onSecondaryDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onSecondaryLight
import com.seyone22.fingerprinter.ui.theme.onSecondaryLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onSecondaryLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onSurfaceDark
import com.seyone22.fingerprinter.ui.theme.onSurfaceDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onSurfaceDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onSurfaceLight
import com.seyone22.fingerprinter.ui.theme.onSurfaceLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onSurfaceLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onSurfaceVariantDark
import com.seyone22.fingerprinter.ui.theme.onSurfaceVariantDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onSurfaceVariantDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onSurfaceVariantLight
import com.seyone22.fingerprinter.ui.theme.onSurfaceVariantLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onSurfaceVariantLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onTertiaryContainerDark
import com.seyone22.fingerprinter.ui.theme.onTertiaryContainerDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onTertiaryContainerDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onTertiaryContainerLight
import com.seyone22.fingerprinter.ui.theme.onTertiaryContainerLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onTertiaryContainerLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.onTertiaryDark
import com.seyone22.fingerprinter.ui.theme.onTertiaryDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.onTertiaryDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.onTertiaryLight
import com.seyone22.fingerprinter.ui.theme.onTertiaryLightHighContrast
import com.seyone22.fingerprinter.ui.theme.onTertiaryLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.outlineDark
import com.seyone22.fingerprinter.ui.theme.outlineDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.outlineDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.outlineLight
import com.seyone22.fingerprinter.ui.theme.outlineLightHighContrast
import com.seyone22.fingerprinter.ui.theme.outlineLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.outlineVariantDark
import com.seyone22.fingerprinter.ui.theme.outlineVariantDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.outlineVariantDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.outlineVariantLight
import com.seyone22.fingerprinter.ui.theme.outlineVariantLightHighContrast
import com.seyone22.fingerprinter.ui.theme.outlineVariantLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.primaryContainerDark
import com.seyone22.fingerprinter.ui.theme.primaryContainerDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.primaryContainerDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.primaryContainerLight
import com.seyone22.fingerprinter.ui.theme.primaryContainerLightHighContrast
import com.seyone22.fingerprinter.ui.theme.primaryContainerLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.primaryDark
import com.seyone22.fingerprinter.ui.theme.primaryDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.primaryDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.primaryLight
import com.seyone22.fingerprinter.ui.theme.primaryLightHighContrast
import com.seyone22.fingerprinter.ui.theme.primaryLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.scrimDark
import com.seyone22.fingerprinter.ui.theme.scrimDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.scrimDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.scrimLight
import com.seyone22.fingerprinter.ui.theme.scrimLightHighContrast
import com.seyone22.fingerprinter.ui.theme.scrimLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.secondaryContainerDark
import com.seyone22.fingerprinter.ui.theme.secondaryContainerDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.secondaryContainerDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.secondaryContainerLight
import com.seyone22.fingerprinter.ui.theme.secondaryContainerLightHighContrast
import com.seyone22.fingerprinter.ui.theme.secondaryContainerLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.secondaryDark
import com.seyone22.fingerprinter.ui.theme.secondaryDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.secondaryDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.secondaryLight
import com.seyone22.fingerprinter.ui.theme.secondaryLightHighContrast
import com.seyone22.fingerprinter.ui.theme.secondaryLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.surfaceDark
import com.seyone22.fingerprinter.ui.theme.surfaceDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.surfaceDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.surfaceLight
import com.seyone22.fingerprinter.ui.theme.surfaceLightHighContrast
import com.seyone22.fingerprinter.ui.theme.surfaceLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.surfaceVariantDark
import com.seyone22.fingerprinter.ui.theme.surfaceVariantDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.surfaceVariantDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.surfaceVariantLight
import com.seyone22.fingerprinter.ui.theme.surfaceVariantLightHighContrast
import com.seyone22.fingerprinter.ui.theme.surfaceVariantLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.tertiaryContainerDark
import com.seyone22.fingerprinter.ui.theme.tertiaryContainerDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.tertiaryContainerDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.tertiaryContainerLight
import com.seyone22.fingerprinter.ui.theme.tertiaryContainerLightHighContrast
import com.seyone22.fingerprinter.ui.theme.tertiaryContainerLightMediumContrast
import com.seyone22.fingerprinter.ui.theme.tertiaryDark
import com.seyone22.fingerprinter.ui.theme.tertiaryDarkHighContrast
import com.seyone22.fingerprinter.ui.theme.tertiaryDarkMediumContrast
import com.seyone22.fingerprinter.ui.theme.tertiaryLight
import com.seyone22.fingerprinter.ui.theme.tertiaryLightHighContrast
import com.seyone22.fingerprinter.ui.theme.tertiaryLightMediumContrast

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)



private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,

    )

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,

    )

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,

    )

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,

    )

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,

    )

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,

    )

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun CafeteriaRatingsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}