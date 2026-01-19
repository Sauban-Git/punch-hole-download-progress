package eu.hxreborn.phdp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import eu.hxreborn.phdp.PunchHoleProgressApp
import eu.hxreborn.phdp.R
import eu.hxreborn.phdp.prefs.PrefsManager
import eu.hxreborn.phdp.prefs.putAny
import eu.hxreborn.phdp.ui.state.rememberPrefsState
import eu.hxreborn.phdp.ui.theme.AppTheme
import eu.hxreborn.phdp.util.RootUtils
import io.github.libxposed.service.XposedService
import io.github.libxposed.service.XposedServiceHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity :
    ComponentActivity(),
    XposedServiceHelper.OnServiceListener {
    // Properties
    private lateinit var prefs: SharedPreferences

    private var xposedService: XposedService? = null
    private var remotePrefs: SharedPreferences? = null

    val serviceState = MutableLiveData<XposedService?>()

    private var showRestartDialog by mutableStateOf(false)
    private var showResetDialog by mutableStateOf(false)

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences(PrefsManager.PREFS_GROUP, Context.MODE_PRIVATE)

        PunchHoleProgressApp.addServiceListener(this)

        setContent {
            AppTheme {
                val prefsState by rememberPrefsState(prefs)

                PunchHoleProgressContent(
                    prefsState = prefsState,
                    onSavePrefs = ::saveToPrefs,
                    onMenuAction = { action ->
                        when (action) {
                            MenuAction.RestartSystemUI -> showRestartDialog = true
                            MenuAction.Reset -> showResetDialog = true
                        }
                    },
                    onTestSuccess = ::simulateSuccess,
                    onTestFailure = ::simulateFailure,
                    onPreviewAnimation = ::previewCompletion,
                )

                if (showRestartDialog) {
                    AlertDialog(
                        onDismissRequest = { showRestartDialog = false },
                        title = { Text(stringResource(R.string.restart_systemui)) },
                        text = { Text(stringResource(R.string.restart_systemui_confirm)) },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showRestartDialog = false
                                    performRestart()
                                },
                            ) {
                                Text(stringResource(R.string.restart))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showRestartDialog = false }) {
                                Text(stringResource(android.R.string.cancel))
                            }
                        },
                    )
                }

                if (showResetDialog) {
                    AlertDialog(
                        onDismissRequest = { showResetDialog = false },
                        title = { Text(stringResource(R.string.reset_defaults)) },
                        text = { Text(stringResource(R.string.reset_confirm)) },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showResetDialog = false
                                    resetToDefaults()
                                },
                            ) {
                                Text(stringResource(R.string.reset))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showResetDialog = false }) {
                                Text(stringResource(android.R.string.cancel))
                            }
                        },
                    )
                }
            }
        }
    }

    // Preferences
    private fun saveToPrefs(
        key: String,
        value: Any,
    ) {
        prefs.edit { putAny(key, value) }
        remotePrefs?.edit(commit = true) { putAny(key, value) }
    }

    // Test simulation
    private fun simulateSuccess() {
        lifecycleScope.launch {
            for (progress in 0..100 step 5) {
                saveToPrefs(PrefsManager.KEY_TEST_PROGRESS, progress)
                delay(100)
            }
            saveToPrefs(PrefsManager.KEY_TEST_PROGRESS, -1)
        }
    }

    private fun previewCompletion() {
        saveToPrefs(PrefsManager.KEY_PREVIEW_TRIGGER, System.currentTimeMillis())
    }

    private fun simulateFailure() {
        lifecycleScope.launch {
            for (progress in 0..60 step 10) {
                saveToPrefs(PrefsManager.KEY_TEST_PROGRESS, progress)
                delay(100)
            }
            saveToPrefs(PrefsManager.KEY_TEST_ERROR, true)
            delay(100)
            saveToPrefs(PrefsManager.KEY_TEST_PROGRESS, -1)
            saveToPrefs(PrefsManager.KEY_TEST_ERROR, false)
        }
    }

    private fun resetToDefaults() {
        val defaults =
            mapOf(
                PrefsManager.KEY_COLOR to PrefsManager.DEFAULT_COLOR,
                PrefsManager.KEY_STROKE_WIDTH to PrefsManager.DEFAULT_STROKE_WIDTH,
                PrefsManager.KEY_RING_GAP to PrefsManager.DEFAULT_RING_GAP,
                PrefsManager.KEY_OPACITY to PrefsManager.DEFAULT_OPACITY,
                PrefsManager.KEY_HOOKS_FEEDBACK to PrefsManager.DEFAULT_HOOKS_FEEDBACK,
                PrefsManager.KEY_CLOCKWISE to true,
                PrefsManager.KEY_FINISH_STYLE to PrefsManager.DEFAULT_FINISH_STYLE,
                PrefsManager.KEY_FINISH_HOLD_MS to PrefsManager.DEFAULT_FINISH_HOLD_MS,
                PrefsManager.KEY_FINISH_EXIT_MS to PrefsManager.DEFAULT_FINISH_EXIT_MS,
                PrefsManager.KEY_FINISH_FLASH_COLOR to PrefsManager.DEFAULT_FINISH_FLASH_COLOR,
                PrefsManager.KEY_SHOW_DOWNLOAD_COUNT to PrefsManager.DEFAULT_SHOW_DOWNLOAD_COUNT,
                PrefsManager.KEY_POWER_SAVER_MODE to PrefsManager.DEFAULT_POWER_SAVER_MODE,
            )
        prefs.edit { defaults.forEach { (k, v) -> putAny(k, v) } }
        remotePrefs?.edit(commit = true) { defaults.forEach { (k, v) -> putAny(k, v) } }
        Toast.makeText(this, R.string.reset_done, Toast.LENGTH_SHORT).show()
    }

    // Lifecycle cleanup
    override fun onDestroy() {
        super.onDestroy()
        PunchHoleProgressApp.removeServiceListener(this)
    }

    // Xposed service callbacks
    override fun onServiceBind(service: XposedService) {
        xposedService = service
        remotePrefs = service.getRemotePreferences(PrefsManager.PREFS_GROUP)
        runOnUiThread {
            serviceState.value = service
        }
    }

    override fun onServiceDied(service: XposedService) {
        xposedService = null
        remotePrefs = null
        runOnUiThread {
            serviceState.value = null
        }
    }

    override fun onResume() {
        super.onResume()
        serviceState.value = xposedService
        remotePrefs?.edit(commit = true) { putBoolean(PrefsManager.KEY_APP_VISIBLE, true) }
    }

    override fun onPause() {
        super.onPause()
        remotePrefs?.edit(commit = true) { putBoolean(PrefsManager.KEY_APP_VISIBLE, false) }
    }

    // System actions
    private fun performRestart() {
        lifecycleScope.launch {
            RootUtils
                .restartSystemUI()
                .onSuccess {
                    Toast.makeText(this@MainActivity, R.string.restart_success, Toast.LENGTH_SHORT).show()
                }.onFailure { e ->
                    Toast
                        .makeText(
                            this@MainActivity,
                            getString(R.string.restart_failed_detail, e.message),
                            Toast.LENGTH_LONG,
                        ).show()
                }
        }
    }

    companion object {
        @JvmStatic
        fun isXposedEnabled(): Boolean = false
    }
}
