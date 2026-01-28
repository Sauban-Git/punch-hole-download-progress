package eu.hxreborn.phdp.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import eu.hxreborn.phdp.R
import eu.hxreborn.phdp.prefs.PrefsManager
import eu.hxreborn.phdp.ui.component.SectionCard
import eu.hxreborn.phdp.ui.component.SettingsScaffold
import eu.hxreborn.phdp.ui.component.preference.SliderPreferenceWithStepper
import eu.hxreborn.phdp.ui.component.preference.TogglePreferenceWithIcon
import eu.hxreborn.phdp.ui.state.PrefsState
import eu.hxreborn.phdp.ui.theme.AppTheme
import eu.hxreborn.phdp.ui.theme.DarkThemeConfig
import eu.hxreborn.phdp.ui.theme.Tokens
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import me.zhanghai.compose.preference.preferenceCategory

@Composable
fun CalibrationScreen(
    prefsState: PrefsState,
    onSavePrefs: (key: String, value: Any) -> Unit,
    onNavigateBack: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    // Enable preview when screen is visible, disable when backgrounded or navigating away
    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        onSavePrefs(PrefsManager.KEY_PERSISTENT_PREVIEW, true)
    }
    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        onSavePrefs(PrefsManager.KEY_PERSISTENT_PREVIEW, false)
    }
    DisposableEffect(Unit) {
        onDispose { onSavePrefs(PrefsManager.KEY_PERSISTENT_PREVIEW, false) }
    }

    SettingsScaffold(
        title = stringResource(R.string.pref_calibrate_ring_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    ) { innerPadding ->
        ProvidePreferenceLocals {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding =
                    PaddingValues(
                        top = innerPadding.calculateTopPadding() + Tokens.SpacingLg,
                        bottom = contentPadding.calculateBottomPadding() + Tokens.SpacingLg,
                    ),
            ) {
                preferenceCategory(
                    key = "calibration_position_header",
                    title = { Text(stringResource(R.string.group_position)) },
                )

                item(key = "calibration_position_section") {
                    SectionCard(
                        items =
                            listOf(
                                {
                                    SliderPreferenceWithStepper(
                                        value = prefsState.ringOffsetX,
                                        onValueChange = {
                                            onSavePrefs(PrefsManager.KEY_RING_OFFSET_X, it)
                                        },
                                        title = {
                                            Text(stringResource(R.string.pref_ring_offset_x_title))
                                        },
                                        valueRange =
                                            PrefsManager.MIN_RING_OFFSET..PrefsManager.MAX_RING_OFFSET,
                                        defaultValue = PrefsManager.DEFAULT_RING_OFFSET,
                                        onReset = {
                                            onSavePrefs(
                                                PrefsManager.KEY_RING_OFFSET_X,
                                                PrefsManager.DEFAULT_RING_OFFSET,
                                            )
                                        },
                                        stepSize = 1f,
                                        decimalPlaces = 0,
                                        suffix = "px",
                                    )
                                },
                                {
                                    SliderPreferenceWithStepper(
                                        value = prefsState.ringOffsetY,
                                        onValueChange = {
                                            onSavePrefs(PrefsManager.KEY_RING_OFFSET_Y, it)
                                        },
                                        title = {
                                            Text(stringResource(R.string.pref_ring_offset_y_title))
                                        },
                                        valueRange =
                                            PrefsManager.MIN_RING_OFFSET..PrefsManager.MAX_RING_OFFSET,
                                        defaultValue = PrefsManager.DEFAULT_RING_OFFSET,
                                        onReset = {
                                            onSavePrefs(
                                                PrefsManager.KEY_RING_OFFSET_Y,
                                                PrefsManager.DEFAULT_RING_OFFSET,
                                            )
                                        },
                                        stepSize = 1f,
                                        decimalPlaces = 0,
                                        suffix = "px",
                                    )
                                },
                            ),
                    )
                }

                preferenceCategory(
                    key = "calibration_size_header",
                    title = { Text(stringResource(R.string.group_size)) },
                )

                item(key = "calibration_size_section") {
                    SectionCard(
                        items =
                            listOf(
                                {
                                    TogglePreferenceWithIcon(
                                        value = prefsState.ringScaleLinked,
                                        onValueChange = {
                                            onSavePrefs(PrefsManager.KEY_RING_SCALE_LINKED, it)
                                            if (it) {
                                                onSavePrefs(
                                                    PrefsManager.KEY_RING_SCALE_Y,
                                                    prefsState.ringScaleX,
                                                )
                                            }
                                        },
                                        title = {
                                            Text(stringResource(R.string.pref_ring_scale_linked_title))
                                        },
                                        summary = {
                                            Text(stringResource(R.string.pref_ring_scale_linked_summary))
                                        },
                                    )
                                },
                                {
                                    SliderPreferenceWithStepper(
                                        value = prefsState.ringScaleX,
                                        onValueChange = {
                                            onSavePrefs(PrefsManager.KEY_RING_SCALE_X, it)
                                            if (prefsState.ringScaleLinked) {
                                                onSavePrefs(PrefsManager.KEY_RING_SCALE_Y, it)
                                            }
                                        },
                                        title = {
                                            Text(stringResource(R.string.pref_ring_scale_x_title))
                                        },
                                        valueRange =
                                            PrefsManager.MIN_RING_SCALE..PrefsManager.MAX_RING_SCALE,
                                        defaultValue = PrefsManager.DEFAULT_RING_SCALE,
                                        onReset = {
                                            onSavePrefs(
                                                PrefsManager.KEY_RING_SCALE_X,
                                                PrefsManager.DEFAULT_RING_SCALE,
                                            )
                                            if (prefsState.ringScaleLinked) {
                                                onSavePrefs(
                                                    PrefsManager.KEY_RING_SCALE_Y,
                                                    PrefsManager.DEFAULT_RING_SCALE,
                                                )
                                            }
                                        },
                                        stepSize = 0.05f,
                                        decimalPlaces = 2,
                                        suffix = "x",
                                    )
                                },
                                {
                                    SliderPreferenceWithStepper(
                                        value = prefsState.ringScaleY,
                                        onValueChange = {
                                            onSavePrefs(PrefsManager.KEY_RING_SCALE_Y, it)
                                        },
                                        title = {
                                            Text(stringResource(R.string.pref_ring_scale_y_title))
                                        },
                                        valueRange =
                                            PrefsManager.MIN_RING_SCALE..PrefsManager.MAX_RING_SCALE,
                                        defaultValue = PrefsManager.DEFAULT_RING_SCALE,
                                        onReset = {
                                            onSavePrefs(
                                                PrefsManager.KEY_RING_SCALE_Y,
                                                PrefsManager.DEFAULT_RING_SCALE,
                                            )
                                        },
                                        stepSize = 0.05f,
                                        decimalPlaces = 2,
                                        suffix = "x",
                                        enabled = !prefsState.ringScaleLinked,
                                    )
                                },
                            ),
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CalibrationScreenPreview() {
    AppTheme(darkThemeConfig = DarkThemeConfig.DARK) {
        CalibrationScreen(
            prefsState = PrefsState(),
            onSavePrefs = { _, _ -> },
            onNavigateBack = {},
            contentPadding = PaddingValues(),
        )
    }
}
