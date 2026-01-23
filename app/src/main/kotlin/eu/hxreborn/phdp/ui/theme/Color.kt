package eu.hxreborn.phdp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Cyan80 = Color(0xFF4DD0E1)
val Cyan40 = Color(0xFF00ACC1)
val CyanGrey80 = Color(0xFFB2EBF2)
val CyanGrey40 = Color(0xFF4DB6AC)

val LightColorScheme =
    lightColorScheme(
        primary = Cyan40,
        onPrimary = Color.White,
        primaryContainer = Color(0xFFB2EBF2),
        onPrimaryContainer = Color(0xFF00363D),
        secondary = Color(0xFF4A6267),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFCDE7EC),
        onSecondaryContainer = Color(0xFF051F23),
        tertiary = Color(0xFF525E7D),
        onTertiary = Color.White,
        tertiaryContainer = Color(0xFFDAE2FF),
        onTertiaryContainer = Color(0xFF0E1B37),
        error = Color(0xFFBA1A1A),
        onError = Color.White,
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
    )

val DarkColorScheme =
    darkColorScheme(
        primary = Cyan80,
        onPrimary = Color(0xFF003639),
        primaryContainer = Color(0xFF004F54),
        onPrimaryContainer = Color(0xFFB2EBF2),
        secondary = Color(0xFFB1CBD0),
        onSecondary = Color(0xFF1C3438),
        secondaryContainer = Color(0xFF324B4F),
        onSecondaryContainer = Color(0xFFCDE7EC),
        tertiary = Color(0xFFBAC6EA),
        onTertiary = Color(0xFF24304D),
        tertiaryContainer = Color(0xFF3B4664),
        onTertiaryContainer = Color(0xFFDAE2FF),
        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
    )
