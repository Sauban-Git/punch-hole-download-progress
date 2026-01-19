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
import eu.hxreborn.phdp.BuildConfig
import eu.hxreborn.phdp.R
import eu.hxreborn.phdp.prefs.PrefsManager
import eu.hxreborn.phdp.ui.component.SectionCard
import eu.hxreborn.phdp.ui.component.preference.ActionPreference
import eu.hxreborn.phdp.ui.component.preference.SelectPreference
import eu.hxreborn.phdp.ui.component.preference.TogglePreferenceWithIcon
import eu.hxreborn.phdp.ui.state.PrefsState
import eu.hxreborn.phdp.ui.theme.AppTheme
import eu.hxreborn.phdp.ui.theme.Tokens
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import me.zhanghai.compose.preference.preferenceCategory

@Composable
fun SystemScreen(
    prefsState: PrefsState,
    onSavePrefs: (key: String, value: Any) -> Unit,
    onTestSuccess: () -> Unit,
    onTestFailure: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val powerSaverEntries = context.resources.getStringArray(R.array.power_saver_entries).toList()
    val powerSaverValues = context.resources.getStringArray(R.array.power_saver_values).toList()

    ProvidePreferenceLocals {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding() + Tokens.SpacingLg,
                bottom = contentPadding.calculateBottomPadding() + Tokens.SpacingLg,
            ),
        ) {
            preferenceCategory(
                key = "system_master_header",
                title = { Text(stringResource(R.string.master_enabled)) },
            )

            item(key = "system_master_section") {
                SectionCard(
                    items = listOf(
                        {
                            TogglePreferenceWithIcon(
                                value = prefsState.enabled,
                                onValueChange = { onSavePrefs(PrefsManager.KEY_ENABLED, it) },
                                title = { Text(stringResource(R.string.master_enabled)) },
                                summary = {
                                    val text = if (prefsState.enabled) R.string.master_enabled_on else R.string.master_enabled_off
                                    Text(stringResource(text))
                                },
                            )
                        },
                    ),
                )
            }

            preferenceCategory(
                key = "system_visibility_header",
                title = { Text(stringResource(R.string.group_visibility)) },
            )

            item(key = "system_visibility_section") {
                SectionCard(
                    enabled = prefsState.enabled,
                    items = listOf(
                        {
                            TogglePreferenceWithIcon(
                                value = prefsState.showDownloadCount,
                                onValueChange = { onSavePrefs(PrefsManager.KEY_SHOW_DOWNLOAD_COUNT, it) },
                                title = { Text(stringResource(R.string.display_item_count)) },
                                summary = { Text(stringResource(R.string.display_item_count_desc)) },
                                enabled = prefsState.enabled,
                            )
                        },
                        {
                            TogglePreferenceWithIcon(
                                value = prefsState.clockwise,
                                onValueChange = { onSavePrefs(PrefsManager.KEY_CLOCKWISE, it) },
                                title = { Text(stringResource(R.string.fill_direction)) },
                                summary = {
                                    val text = if (prefsState.clockwise) R.string.clockwise else R.string.counter_clockwise
                                    Text(stringResource(text))
                                },
                                enabled = prefsState.enabled,
                            )
                        },
                    ),
                )
            }

            preferenceCategory(
                key = "system_power_header",
                title = { Text(stringResource(R.string.group_power)) },
            )

            item(key = "system_power_section") {
                SectionCard(
                    enabled = prefsState.enabled,
                    items = listOf(
                        {
                            SelectPreference(
                                value = prefsState.powerSaverMode,
                                onValueChange = { onSavePrefs(PrefsManager.KEY_POWER_SAVER_MODE, it) },
                                values = powerSaverValues,
                                title = { Text(stringResource(R.string.battery_saver_mode)) },
                                enabled = prefsState.enabled,
                                valueToText = { powerSaverLabel(it, powerSaverEntries, powerSaverValues) ?: it },
                            )
                        },
                    ),
                )
            }

            preferenceCategory(
                key = "system_diagnostics_header",
                title = { Text(stringResource(R.string.group_diagnostics)) },
            )

            item(key = "system_diagnostics_section") {
                SectionCard(
                    enabled = prefsState.enabled,
                    items = listOf(
                        {
                            ActionPreference(
                                onClick = onTestSuccess,
                                title = { Text(stringResource(R.string.test_success)) },
                                enabled = prefsState.enabled,
                            )
                        },
                        {
                            ActionPreference(
                                onClick = onTestFailure,
                                title = { Text(stringResource(R.string.test_failure)) },
                                enabled = prefsState.enabled,
                            )
                        },
                    ),
                )
            }

            preferenceCategory(
                key = "system_about_header",
                title = { Text(stringResource(R.string.group_about)) },
            )

            item(key = "system_about_section") {
                SectionCard(
                    items = listOf(
                        {
                            ActionPreference(
                                onClick = {},
                                title = { Text(stringResource(R.string.version_label)) },
                                summary = { Text("${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})") },
                                enabled = false,
                            )
                        },
                    ),
                )
            }
        }
    }
}

private fun powerSaverLabel(
    value: String,
    entries: List<String>,
    values: List<String>,
): String? {
    val index = values.indexOf(value)
    return if (index >= 0) entries.getOrNull(index) else null
}

@Preview
@Composable
private fun SystemScreenPreview() {
    AppTheme {
        SystemScreen(
            prefsState = PrefsState(),
            onSavePrefs = { _, _ -> },
            onTestSuccess = {},
            onTestFailure = {},
            contentPadding = PaddingValues(),
        )
    }
}
