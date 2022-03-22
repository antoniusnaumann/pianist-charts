package dev.antonius.pianist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

var data = listOf(3.0f to 5.0f, -21.0f to 17.0f, 5.3f to 7.0f, 0.4f to 15.0f).sortedBy { it.first }

@Composable @OptIn(ExperimentalMaterial3Api::class)
fun DemoApp() {

    var selected by remember { mutableStateOf<PointStyle>(PointStyle.None) }

    Column {
        LineChart(
            data,
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f)
                .padding(16.dp),
            strokeWidth = 8f,
            pointStyle = selected
        )

        PointStyleSelector(selected) { selected = it }
    }
}

@Composable @ExperimentalMaterial3Api
fun PointStyleSelector(selected: PointStyle, onChange: (PointStyle) -> Unit) {
    var expand by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expand) 90.0f else 0.0f)

    Column {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.PlayArrow, "Expand", Modifier.clickable { expand = !expand }.rotate(rotation))
            Text("Point Style", Modifier.padding(12.dp), style = MaterialTheme.typography.titleMedium)
        }

        AnimatedVisibility(expand) {
            Column {
                LabeledRadioButton("None", selected == PointStyle.None, onClick = { onChange(PointStyle.None) })
                LabeledRadioButton("Square", selected is PointStyle.Square, onClick = { onChange(PointStyle.Square()) })
                LabeledRadioButton("Circle", selected is PointStyle.Circle, onClick = { onChange(PointStyle.Circle()) })
            }
        }
    }
}

@Composable @ExperimentalMaterial3Api
fun LabeledRadioButton(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onClick)
        Text(label, style = MaterialTheme.typography.labelMedium)
    }
}
