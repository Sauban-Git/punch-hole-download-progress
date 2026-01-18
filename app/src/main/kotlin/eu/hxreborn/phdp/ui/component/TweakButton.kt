package eu.hxreborn.phdp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.hxreborn.phdp.ui.theme.Tokens

@Composable
fun TweakButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String = "",
    enabled: Boolean = true,
) {
    if (description.isEmpty()) {
        FilledTonalButton(
            onClick = onClick,
            enabled = enabled,
            modifier =
                modifier
                    .padding(
                        horizontal = Tokens.SettingsRowHorizontalPadding,
                        vertical = Tokens.SettingsRowVerticalPadding,
                    ).fillMaxWidth(),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    } else {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Tokens.SettingsRowHorizontalPadding,
                        vertical = Tokens.SettingsRowVerticalPadding,
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color =
                        if (enabled) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = Tokens.DISABLED_ALPHA)
                        },
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color =
                        if (enabled) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = Tokens.DISABLED_ALPHA)
                        },
                    modifier = Modifier.padding(top = Tokens.SpacingXs),
                )
            }
        }
    }
}
