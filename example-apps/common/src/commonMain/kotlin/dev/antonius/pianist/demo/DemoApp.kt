package dev.antonius.pianist.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.antonius.pianist.CornerStyle
import dev.antonius.pianist.LineChart
import dev.antonius.pianist.LineStyle
import dev.antonius.pianist.PointStyle

val data = listOf(-21.0f to 17.0f, -17f to 8f, -15f to 13f, -12f to 7f, -10f to 5f, -5f to 3f, 0.4f to 15.0f, 3.0f to 5.0f,  5.3f to 7.0f, ).sortedBy { it.first }

data class ViewState(
    val pointStyle: PointStyle,
    val lineStyle: LineStyle,
)

@Composable @OptIn(ExperimentalMaterial3Api::class)
fun DemoApp(padding: Dp = 16.dp) {

    var selected by remember { mutableStateOf(ViewState(PointStyle.None, LineStyle(width = 8.dp))) }

    Column(modifier = Modifier.padding(start = padding, end = padding, top = padding)) {
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

        VerticalScrollView {
            Column(Modifier.padding(bottom = padding)) {
                LineStyleSelector(selected.lineStyle) { selected = selected.copy(lineStyle = it) }
                PointStyleSelector(selected.pointStyle) { selected = selected.copy(pointStyle = it) }
            }
        }
    }
}

fun LineStyle.copy(
    width: Dp = this.width,
    miter: Float = this.miter,
    cap: StrokeCap = this.cap,
    join: StrokeJoin = this.join,
    pathEffect: PathEffect? = this.pathEffect
) = LineStyle(width, miter, cap, join, pathEffect)

@Composable @ExperimentalMaterial3Api
private fun LineStyleSelector(selected: LineStyle, onChange: (LineStyle) -> Unit) {

    Expandable("Line Style") {
        Column {
            StrokeWidthSlider("Line Thickness", selected.width.value) {
                onChange(selected.copy(width = it.dp))
            }

            val select: (Pair<StrokeCap, StrokeJoin>) -> Unit = { (cap, join) ->
                onChange(selected.copy(cap = cap, join = join))
            }

            LabeledRadioGroup(selected.cap to selected.join, listOf(
                RadioItem("Round", CornerStyle.Round.asPair(), select),
                RadioItem("Sharp", CornerStyle.Sharp.asPair(), select),
                RadioItem("Flat", CornerStyle.Flat.asPair(), select),
            ))
        }
    }
}

private fun CornerStyle.asPair() = strokeCap to strokeJoin

@Composable
private fun StrokeWidthSlider(label: String, value: Float, action: (Float) -> Unit) {
    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, style = MaterialTheme.typography.titleMedium)
        Slider(value, { action(it) }, Modifier.padding(start = 8.dp), valueRange = 1f..24f)
    }
}

@Composable @ExperimentalMaterial3Api
internal fun PointStyleSelector(selected: PointStyle, onChange: (PointStyle) -> Unit) {
    Expandable("Point Style") {
        LabeledRadioGroup(selected, listOf(
            RadioItem("None", PointStyle.None, onChange),
            RadioItem("Square", PointStyle.Square(), onChange) { it is PointStyle.Square },
            RadioItem("Circle", PointStyle.Circle(), onChange) { it is PointStyle.Circle},
        ))
    }
}
