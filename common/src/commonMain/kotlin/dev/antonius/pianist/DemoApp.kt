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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity

var data = listOf(-21.0f to 17.0f, -17f to 8f, -15f to 13f, -12f to 7f, -10f to 5f, -5f to 3f, 0.4f to 15.0f, 3.0f to 5.0f,  5.3f to 7.0f, ).sortedBy { it.first }

data class ViewState(
    val pointStyle: PointStyle,
    val lineStyle: Stroke,
)

@Composable @OptIn(ExperimentalMaterial3Api::class)
fun DemoApp(modifier: Modifier = Modifier.padding(16.dp)) {

    val density = LocalDensity.current

    var selected by remember { mutableStateOf(ViewState(PointStyle.None, Stroke(width = with(density) { 1.dp.toPx() }))) }

    Column(modifier = modifier) {
        LineChart(
            data,
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f)
                .padding(16.dp)
                .padding(bottom = 32.dp),
            lineStyle = selected.lineStyle,
            pointStyle = selected.pointStyle
        )

        LineStyleSelector(selected.lineStyle) { selected = selected.copy(lineStyle = it) }
        PointStyleSelector(selected.pointStyle) { selected = selected.copy(pointStyle = it) }
    }
}

fun Stroke.copy(
    width: Float = this.width,
    miter: Float = this.miter,
    cap: StrokeCap = this.cap,
    join: StrokeJoin = this.join,
    pathEffect: PathEffect? = this.pathEffect
) = Stroke(width, miter, cap, join, pathEffect)

@Composable
fun Expandable(label: String, content: @Composable () -> Unit) {
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

@Composable
fun LineStyleSelector(selected: Stroke, onChange: (Stroke) -> Unit) {
    val localDensity = LocalDensity.current

    Expandable("Line Style") {
        StrokeWidthSlider("Line Thickness", with(localDensity) { selected.width.toDp().value }) { dp ->
            onChange(selected.copy(width = with(localDensity) { dp.toPx() }))
        }
    }
}

@Composable
fun StrokeWidthSlider(label: String, value: Float, action: (Dp) -> Unit) {
    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, style = MaterialTheme.typography.titleMedium)
        Slider(value, { action(it.dp) }, Modifier.padding(start = 8.dp), valueRange = 1f..24f)
    }
}

@Composable @ExperimentalMaterial3Api
fun PointStyleSelector(selected: PointStyle, onChange: (PointStyle) -> Unit) {
    Expandable("Point Style") {
        Column {
            LabeledRadioButton("None", selected == PointStyle.None, onClick = { onChange(PointStyle.None) })
            LabeledRadioButton("Square", selected is PointStyle.Square, onClick = { onChange(PointStyle.Square()) })
            LabeledRadioButton("Circle", selected is PointStyle.Circle, onClick = { onChange(PointStyle.Circle()) })
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
