package eu.hxreborn.phdp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.navigation3.runtime.NavKey

@Composable
fun <T : NavKey> rememberTypedBackStack(initialKey: T): SnapshotStateList<T> = remember { listOf(initialKey).toMutableStateList() }
