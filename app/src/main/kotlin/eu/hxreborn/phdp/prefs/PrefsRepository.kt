package eu.hxreborn.phdp.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import eu.hxreborn.phdp.ui.state.PrefsState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface PrefsRepository {
    val state: Flow<PrefsState>

    fun save(
        key: String,
        value: Any,
    )

    fun resetDefaults()
}

class PrefsRepositoryImpl(
    private val localPrefs: SharedPreferences,
    private val remotePrefsProvider: () -> SharedPreferences?,
) : PrefsRepository {
    override val state: Flow<PrefsState> =
        callbackFlow {
            val listener =
                SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
                    trySend(localPrefs.toPrefsState())
                }
            trySend(localPrefs.toPrefsState())
            localPrefs.registerOnSharedPreferenceChangeListener(listener)
            awaitClose { localPrefs.unregisterOnSharedPreferenceChangeListener(listener) }
        }

    override fun save(
        key: String,
        value: Any,
    ) {
        localPrefs.edit { putAny(key, value) }
        remotePrefsProvider()?.edit(commit = true) { putAny(key, value) }
    }

    override fun resetDefaults() {
        localPrefs.edit { PrefsManager.DEFAULTS.forEach { (k, v) -> putAny(k, v) } }
        remotePrefsProvider()?.edit(commit = true) {
            PrefsManager.DEFAULTS.forEach { (k, v) ->
                putAny(k, v)
            }
        }
    }

    private fun SharedPreferences.toPrefsState(): PrefsState =
        PrefsState(
            enabled = read(PrefsManager.KEY_ENABLED, PrefsManager.DEFAULT_ENABLED),
            color = read(PrefsManager.KEY_COLOR, PrefsManager.DEFAULT_COLOR),
            strokeWidth =
                readFloat(
                    PrefsManager.KEY_STROKE_WIDTH,
                    PrefsManager.DEFAULT_STROKE_WIDTH,
                    PrefsManager.MIN_STROKE_WIDTH..PrefsManager.MAX_STROKE_WIDTH,
                ),
            ringGap =
                readFloat(
                    PrefsManager.KEY_RING_GAP,
                    PrefsManager.DEFAULT_RING_GAP,
                    PrefsManager.MIN_RING_GAP..PrefsManager.MAX_RING_GAP,
                ),
            opacity =
                readInt(
                    PrefsManager.KEY_OPACITY,
                    PrefsManager.DEFAULT_OPACITY,
                    PrefsManager.MIN_OPACITY..PrefsManager.MAX_OPACITY,
                ),
            hooksFeedback =
                read(
                    PrefsManager.KEY_HOOKS_FEEDBACK,
                    PrefsManager.DEFAULT_HOOKS_FEEDBACK,
                ),
            clockwise = read(PrefsManager.KEY_CLOCKWISE, true),
            progressEasing =
                readString(
                    PrefsManager.KEY_PROGRESS_EASING,
                    PrefsManager.DEFAULT_PROGRESS_EASING,
                ),
            errorColor = read(PrefsManager.KEY_ERROR_COLOR, PrefsManager.DEFAULT_ERROR_COLOR),
            powerSaverMode =
                readString(
                    PrefsManager.KEY_POWER_SAVER_MODE,
                    PrefsManager.DEFAULT_POWER_SAVER_MODE,
                ),
            showDownloadCount =
                read(
                    PrefsManager.KEY_SHOW_DOWNLOAD_COUNT,
                    PrefsManager.DEFAULT_SHOW_DOWNLOAD_COUNT,
                ),
            finishStyle =
                readString(
                    PrefsManager.KEY_FINISH_STYLE,
                    PrefsManager.DEFAULT_FINISH_STYLE,
                ),
            finishHoldMs =
                readInt(
                    PrefsManager.KEY_FINISH_HOLD_MS,
                    PrefsManager.DEFAULT_FINISH_HOLD_MS,
                    PrefsManager.MIN_FINISH_HOLD_MS..PrefsManager.MAX_FINISH_HOLD_MS,
                ),
            finishExitMs =
                readInt(
                    PrefsManager.KEY_FINISH_EXIT_MS,
                    PrefsManager.DEFAULT_FINISH_EXIT_MS,
                    PrefsManager.MIN_FINISH_EXIT_MS..PrefsManager.MAX_FINISH_EXIT_MS,
                ),
            finishUseFlashColor =
                read(
                    PrefsManager.KEY_FINISH_USE_FLASH_COLOR,
                    PrefsManager.DEFAULT_FINISH_USE_FLASH_COLOR,
                ),
            finishFlashColor =
                read(
                    PrefsManager.KEY_FINISH_FLASH_COLOR,
                    PrefsManager.DEFAULT_FINISH_FLASH_COLOR,
                ),
            minVisibilityEnabled =
                read(
                    PrefsManager.KEY_MIN_VISIBILITY_ENABLED,
                    PrefsManager.DEFAULT_MIN_VISIBILITY_ENABLED,
                ),
            minVisibilityMs =
                readInt(
                    PrefsManager.KEY_MIN_VISIBILITY_MS,
                    PrefsManager.DEFAULT_MIN_VISIBILITY_MS,
                    PrefsManager.MIN_MIN_VISIBILITY_MS..PrefsManager.MAX_MIN_VISIBILITY_MS,
                ),
            completionPulseEnabled =
                read(
                    PrefsManager.KEY_COMPLETION_PULSE_ENABLED,
                    PrefsManager.DEFAULT_COMPLETION_PULSE_ENABLED,
                ),
            percentTextEnabled =
                read(
                    PrefsManager.KEY_PERCENT_TEXT_ENABLED,
                    PrefsManager.DEFAULT_PERCENT_TEXT_ENABLED,
                ),
            percentTextPosition =
                readString(
                    PrefsManager.KEY_PERCENT_TEXT_POSITION,
                    PrefsManager.DEFAULT_PERCENT_TEXT_POSITION,
                ),
            filenameTextEnabled =
                read(
                    PrefsManager.KEY_FILENAME_TEXT_ENABLED,
                    PrefsManager.DEFAULT_FILENAME_TEXT_ENABLED,
                ),
            filenameTextPosition =
                readString(
                    PrefsManager.KEY_FILENAME_TEXT_POSITION,
                    PrefsManager.DEFAULT_FILENAME_TEXT_POSITION,
                ),
            darkThemeConfig = readDarkThemeConfig(),
            useDynamicColor =
                read(
                    PrefsManager.KEY_USE_DYNAMIC_COLOR,
                    PrefsManager.DEFAULT_USE_DYNAMIC_COLOR,
                ),
            ringScaleX =
                readFloat(
                    PrefsManager.KEY_RING_SCALE_X,
                    PrefsManager.DEFAULT_RING_SCALE,
                    PrefsManager.MIN_RING_SCALE..PrefsManager.MAX_RING_SCALE,
                ),
            ringScaleY =
                readFloat(
                    PrefsManager.KEY_RING_SCALE_Y,
                    PrefsManager.DEFAULT_RING_SCALE,
                    PrefsManager.MIN_RING_SCALE..PrefsManager.MAX_RING_SCALE,
                ),
            ringScaleLinked =
                read(
                    PrefsManager.KEY_RING_SCALE_LINKED,
                    PrefsManager.DEFAULT_RING_SCALE_LINKED,
                ),
        )
}
