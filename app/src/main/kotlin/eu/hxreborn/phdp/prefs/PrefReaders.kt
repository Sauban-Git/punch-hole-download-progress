package eu.hxreborn.phdp.prefs

import android.content.SharedPreferences
import eu.hxreborn.phdp.ui.theme.DarkThemeConfig

// Generic readers
inline fun <reified T> SharedPreferences.read(
    key: String,
    default: T,
): T =
    when (T::class) {
        Boolean::class -> getBoolean(key, default as Boolean) as T
        Int::class -> getInt(key, default as Int) as T
        Float::class -> getFloat(key, default as Float) as T
        String::class -> (getString(key, default as String) ?: default) as T
        else -> error("Unsupported prefs type: ${T::class}")
    }

fun SharedPreferences.readInt(
    key: String,
    default: Int,
    range: IntRange,
): Int = getInt(key, default).coerceIn(range)

fun SharedPreferences.readFloat(
    key: String,
    default: Float,
    range: ClosedFloatingPointRange<Float>,
): Float = getFloat(key, default).coerceIn(range)

fun SharedPreferences.readString(
    key: String,
    default: String,
): String = getString(key, default) ?: default

fun SharedPreferences.readDarkThemeConfig(): DarkThemeConfig {
    val value =
        getString(PrefsManager.KEY_DARK_THEME_CONFIG, PrefsManager.DEFAULT_DARK_THEME_CONFIG)
            ?: PrefsManager.DEFAULT_DARK_THEME_CONFIG
    return when (value) {
        "light" -> DarkThemeConfig.LIGHT
        "dark" -> DarkThemeConfig.DARK
        else -> DarkThemeConfig.FOLLOW_SYSTEM
    }
}

// Writer helper
fun SharedPreferences.Editor.putAny(
    key: String,
    value: Any,
): SharedPreferences.Editor =
    apply {
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            is String -> putString(key, value)
        }
    }
