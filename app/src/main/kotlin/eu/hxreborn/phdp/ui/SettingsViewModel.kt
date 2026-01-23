package eu.hxreborn.phdp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import eu.hxreborn.phdp.prefs.PrefsRepository
import eu.hxreborn.phdp.ui.state.PrefsState
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

class SettingsViewModel(
    private val repository: PrefsRepository,
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> =
        repository.state
            .map { SettingsUiState.Success(it) }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = SettingsUiState.Loading,
            )

    fun save(
        key: String,
        value: Any,
    ) {
        repository.save(key, value)
    }

    fun resetDefaults() {
        repository.resetDefaults()
    }
}

sealed interface SettingsUiState {
    data object Loading : SettingsUiState

    data class Success(
        val prefs: PrefsState,
    ) : SettingsUiState
}

class SettingsViewModelFactory(
    private val repository: PrefsRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingsViewModel(repository) as T
}
