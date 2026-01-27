package eu.hxreborn.phdp.ui.state

import eu.hxreborn.phdp.prefs.PrefsManager
import eu.hxreborn.phdp.ui.theme.DarkThemeConfig

data class PrefsState(
    val enabled: Boolean = PrefsManager.DEFAULT_ENABLED,
    val color: Int = PrefsManager.DEFAULT_COLOR,
    val strokeWidth: Float = PrefsManager.DEFAULT_STROKE_WIDTH,
    val ringGap: Float = PrefsManager.DEFAULT_RING_GAP,
    val opacity: Int = PrefsManager.DEFAULT_OPACITY,
    val hooksFeedback: Boolean = PrefsManager.DEFAULT_HOOKS_FEEDBACK,
    val clockwise: Boolean = true,
    val progressEasing: String = PrefsManager.DEFAULT_PROGRESS_EASING,
    val errorColor: Int = PrefsManager.DEFAULT_ERROR_COLOR,
    val powerSaverMode: String = PrefsManager.DEFAULT_POWER_SAVER_MODE,
    val showDownloadCount: Boolean = PrefsManager.DEFAULT_SHOW_DOWNLOAD_COUNT,
    val finishStyle: String = PrefsManager.DEFAULT_FINISH_STYLE,
    val finishHoldMs: Int = PrefsManager.DEFAULT_FINISH_HOLD_MS,
    val finishExitMs: Int = PrefsManager.DEFAULT_FINISH_EXIT_MS,
    val finishUseFlashColor: Boolean = PrefsManager.DEFAULT_FINISH_USE_FLASH_COLOR,
    val finishFlashColor: Int = PrefsManager.DEFAULT_FINISH_FLASH_COLOR,
    val minVisibilityEnabled: Boolean = PrefsManager.DEFAULT_MIN_VISIBILITY_ENABLED,
    val minVisibilityMs: Int = PrefsManager.DEFAULT_MIN_VISIBILITY_MS,
    val completionPulseEnabled: Boolean = PrefsManager.DEFAULT_COMPLETION_PULSE_ENABLED,
    val percentTextEnabled: Boolean = PrefsManager.DEFAULT_PERCENT_TEXT_ENABLED,
    val percentTextPosition: String = PrefsManager.DEFAULT_PERCENT_TEXT_POSITION,
    val filenameTextEnabled: Boolean = PrefsManager.DEFAULT_FILENAME_TEXT_ENABLED,
    val filenameTextPosition: String = PrefsManager.DEFAULT_FILENAME_TEXT_POSITION,
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    val useDynamicColor: Boolean = PrefsManager.DEFAULT_USE_DYNAMIC_COLOR,
    val ringScaleX: Float = PrefsManager.DEFAULT_RING_SCALE,
    val ringScaleY: Float = PrefsManager.DEFAULT_RING_SCALE,
    val ringScaleLinked: Boolean = PrefsManager.DEFAULT_RING_SCALE_LINKED,
)
