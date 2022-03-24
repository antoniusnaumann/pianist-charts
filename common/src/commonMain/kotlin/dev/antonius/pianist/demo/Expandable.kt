package dev.antonius.pianist.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
internal fun Expandable(label: String, content: @Composable () -> Unit) {
    var expand by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expand) 90.0f else 0.0f)

    Column {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.PlayArrow, "Expand", Modifier.clickable { expand = !expand }.rotate(rotation))
            Text(label, Modifier.padding(12.dp), style = MaterialTheme.typography.titleMedium)
        }

        AnimatedVisibility(expand) {
            content()
        }
    }
}
