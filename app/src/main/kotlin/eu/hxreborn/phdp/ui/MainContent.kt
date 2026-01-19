package eu.hxreborn.phdp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import eu.hxreborn.phdp.R
import eu.hxreborn.phdp.ui.navigation.BottomNav
import eu.hxreborn.phdp.ui.navigation.MainNavHost
import eu.hxreborn.phdp.ui.state.PrefsState
import eu.hxreborn.phdp.ui.theme.Tokens

sealed class MenuAction {
    data object RestartSystemUI : MenuAction()

    data object Reset : MenuAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PunchHoleProgressContent(
    prefsState: PrefsState,
    onSavePrefs: (key: String, value: Any) -> Unit,
    onMenuAction: (MenuAction) -> Unit,
    onTestSuccess: () -> Unit,
    onTestFailure: () -> Unit,
    onPreviewAnimation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var menuExpanded by remember { mutableStateOf(false) }
    val isCollapsed = scrollBehavior.state.collapsedFraction >= 1f

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    if (isCollapsed) {
                        Text(
                            text = stringResource(R.string.app_name),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    } else {
                        Column {
                            Text(
                                text = "Punch-hole",
                                style = MaterialTheme.typography.headlineMedium,
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = stringResource(R.string.app_tagline),
                                style = MaterialTheme.typography.titleSmall,
                                color = LocalContentColor.current.copy(alpha = Tokens.MEDIUM_EMPHASIS_ALPHA),
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    if (isCollapsed) {
                        Box {
                            IconButton(onClick = { menuExpanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = stringResource(R.string.more_options),
                                )
                            }
                            DropdownMenu(
                                expanded = menuExpanded && isCollapsed,
                                onDismissRequest = { menuExpanded = false },
                            ) {
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.restart_systemui)) },
                                    onClick = {
                                        menuExpanded = false
                                        onMenuAction(MenuAction.RestartSystemUI)
                                    },
                                    leadingIcon = {
                                        Icon(Icons.Default.Refresh, contentDescription = null)
                                    },
                                )
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.reset_defaults)) },
                                    onClick = {
                                        menuExpanded = false
                                        onMenuAction(MenuAction.Reset)
                                    },
                                    leadingIcon = {
                                        Icon(Icons.Default.RestartAlt, contentDescription = null)
                                    },
                                )
                            }
                        }
                    }
                },
            )
        },
        bottomBar = {
            BottomNav(
                navController = navController,
                currentRoute = currentRoute,
            )
        },
    ) { paddingValues ->
        MainNavHost(
            navController = navController,
            prefsState = prefsState,
            onSavePrefs = onSavePrefs,
            onTestSuccess = onTestSuccess,
            onTestFailure = onTestFailure,
            onPreviewAnimation = onPreviewAnimation,
            contentPadding = paddingValues,
        )
    }
}
