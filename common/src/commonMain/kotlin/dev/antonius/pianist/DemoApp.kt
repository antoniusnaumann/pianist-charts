package dev.antonius.pianist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp

var data = listOf(-21.0f to 17.0f, -17f to 8f, -15f to 13f, -12f to 7f, -10f to 5f, -5f to 3f, 0.4f to 15.0f, 3.0f to 5.0f,  5.3f to 7.0f, ).sortedBy { it.first }

data class ViewState(
    val pointStyle: PointStyle,
    val strokeWidth: Dp,
)

@Composable @OptIn(ExperimentalMaterial3Api::class)
fun DemoApp() {

    var selected by remember { mutableStateOf(ViewState(PointStyle.None, 2.dp)) }

    Column {
        LineChart(
            data,
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f)
                .padding(16.dp),
            strokeWidth = selected.strokeWidth,
            pointStyle = selected.pointStyle
        )

        StrokeWidthSlider("Line Thickness", selected.strokeWidth.value, { selected = selected.copy(strokeWidth = it.dp) })
        PointStyleSelector(selected.pointStyle) { selected = selected.copy(pointStyle = it) }
    }
}

@Composable
fun StrokeWidthSlider(label: String, value: Float, action: (Float) -> Unit) {
    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, style = MaterialTheme.typography.titleMedium)
        Slider(value, action, valueRange = 0.1f..16f)
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
