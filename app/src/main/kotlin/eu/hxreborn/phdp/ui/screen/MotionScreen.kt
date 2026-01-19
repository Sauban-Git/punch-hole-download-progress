package eu.hxreborn.phdp.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.hxreborn.phdp.R
import eu.hxreborn.phdp.prefs.PrefsManager
import eu.hxreborn.phdp.ui.component.SectionCard
import eu.hxreborn.phdp.ui.component.preference.SelectPreference
import eu.hxreborn.phdp.ui.component.preference.SliderPreferenceWithReset
import eu.hxreborn.phdp.ui.component.preference.TogglePreferenceWithIcon
import eu.hxreborn.phdp.ui.state.PrefsState
import eu.hxreborn.phdp.ui.theme.AppTheme
import eu.hxreborn.phdp.ui.theme.Tokens
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import me.zhanghai.compose.preference.preferenceCategory

@Composable
fun MotionScreen(
    prefsState: PrefsState,
    onSavePrefs: (key: String, value: Any) -> Unit,
    onPreviewAnimation: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val finishStyleEntries = context.resources.getStringArray(R.array.finish_style_entries).toList()
    val finishStyleValues = context.resources.getStringArray(R.array.finish_style_values).toList()

    ProvidePreferenceLocals {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding() + Tokens.SpacingLg,
                bottom = contentPadding.calculateBottomPadding() + Tokens.SpacingLg,
            ),
        ) {
            preferenceCategory(
                key = "motion_animation_header",
                title = { Text(stringResource(R.string.group_animation)) },
            )

            item(key = "motion_animation_section") {
                SectionCard(
                    items = listOf(
                        {
                            SelectPreference(
                                value = prefsState.finishStyle,
                                onValueChange = { onSavePrefs(PrefsManager.KEY_FINISH_STYLE, it) },
                                values = finishStyleValues,
                                title = { Text(stringResource(R.string.completion_style)) },
                                valueToText = { finishStyleLabel(it, finishStyleEntries, finishStyleValues) ?: it },
                            )
                        },
                    ),
                )
            }

            preferenceCategory(
                key = "motion_timing_header",
                title = { Text(stringResource(R.string.group_timing)) },
            )

            item(key = "motion_timing_section") {
                SectionCard(
                    items = listOf(
                        {
                            SliderPreferenceWithReset(
                                value = prefsState.finishHoldMs.toFloat(),
                                onValueChange = { onSavePrefs(PrefsManager.KEY_FINISH_HOLD_MS, it.toInt()) },
                                title = { Text(stringResource(R.string.completion_hold)) },
                                summary = { Text(stringResource(R.string.completion_hold_desc)) },
                                valueRange = PrefsManager.MIN_FINISH_HOLD_MS.toFloat()..PrefsManager.MAX_FINISH_HOLD_MS.toFloat(),
                                defaultValue = PrefsManager.DEFAULT_FINISH_HOLD_MS.toFloat(),
                                onReset = { onSavePrefs(PrefsManager.KEY_FINISH_HOLD_MS, PrefsManager.DEFAULT_FINISH_HOLD_MS) },
                                valueText = { Text("${it.toInt()}ms") },
                            )
                        },
                        {
                            SliderPreferenceWithReset(
                                value = prefsState.finishExitMs.toFloat(),
                                onValueChange = { onSavePrefs(PrefsManager.KEY_FINISH_EXIT_MS, it.toInt()) },
                                title = { Text(stringResource(R.string.exit_duration)) },
                                summary = { Text(stringResource(R.string.exit_duration_desc)) },
                                valueRange = PrefsManager.MIN_FINISH_EXIT_MS.toFloat()..PrefsManager.MAX_FINISH_EXIT_MS.toFloat(),
                                defaultValue = PrefsManager.DEFAULT_FINISH_EXIT_MS.toFloat(),
                                onReset = { onSavePrefs(PrefsManager.KEY_FINISH_EXIT_MS, PrefsManager.DEFAULT_FINISH_EXIT_MS) },
                                valueText = { Text("${it.toInt()}ms") },
                            )
                        },
                    ),
                )
            }

            preferenceCategory(
                key = "motion_feedback_header",
                title = { Text(stringResource(R.string.group_feedback)) },
            )

            item(key = "motion_feedback_section") {
                SectionCard(
                    items = listOf(
                        {
                            TogglePreferenceWithIcon(
                                value = prefsState.hooksFeedback,
                                onValueChange = { onSavePrefs(PrefsManager.KEY_HOOKS_FEEDBACK, it) },
                                title = { Text(stringResource(R.string.finish_vibration)) },
                                summary = { Text(stringResource(R.string.finish_vibration_desc)) },
                            )
                        },
                        {
                            TogglePreferenceWithIcon(
                                value = prefsState.completionPulseEnabled,
                                onValueChange = { onSavePrefs(PrefsManager.KEY_COMPLETION_PULSE_ENABLED, it) },
                                title = { Text(stringResource(R.string.completion_pulse)) },
                                summary = { Text(stringResource(R.string.completion_pulse_desc)) },
                            )
                        },
                    ),
                )
            }

            preferenceCategory(
                key = "motion_fast_header",
                title = { Text(stringResource(R.string.group_fast_downloads)) },
            )

            item(key = "motion_fast_section") {
                SectionCard(
                    items = listOf(
                        {
                            TogglePreferenceWithIcon(
                                value = prefsState.minVisibilityEnabled,
                                onValueChange = { onSavePrefs(PrefsManager.KEY_MIN_VISIBILITY_ENABLED, it) },
                                title = { Text(stringResource(R.string.min_visibility)) },
                                summary = { Text(stringResource(R.string.min_visibility_desc)) },
                            )
                        },
                        {
                            SliderPreferenceWithReset(
                                value = prefsState.minVisibilityMs.toFloat(),
                                onValueChange = { onSavePrefs(PrefsManager.KEY_MIN_VISIBILITY_MS, it.toInt()) },
                                title = { Text(stringResource(R.string.min_visibility_duration)) },
                                summary = { Text(stringResource(R.string.min_visibility_duration_desc)) },
                                enabled = prefsState.minVisibilityEnabled,
                                valueRange = PrefsManager.MIN_MIN_VISIBILITY_MS.toFloat()..PrefsManager.MAX_MIN_VISIBILITY_MS.toFloat(),
                                defaultValue = PrefsManager.DEFAULT_MIN_VISIBILITY_MS.toFloat(),
                                onReset = { onSavePrefs(PrefsManager.KEY_MIN_VISIBILITY_MS, PrefsManager.DEFAULT_MIN_VISIBILITY_MS) },
                                valueText = { Text("${it.toInt()}ms") },
                            )
                        },
                    ),
                )
            }
        }
    }
}

private fun finishStyleLabel(
    value: String,
    entries: List<String>,
    values: List<String>,
): String? {
    val index = values.indexOf(value)
    return if (index >= 0) entries.getOrNull(index) else null
}

@Preview
@Composable
private fun MotionScreenPreview() {
    AppTheme {
        MotionScreen(
            prefsState = PrefsState(),
            onSavePrefs = { _, _ -> },
            onPreviewAnimation = {},
            contentPadding = PaddingValues(),
        )
    }
}
