package dev.antonius.pianist.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.antonius.pianist.LineChart
import dev.antonius.pianist.styling.BackgroundStyle
import dev.antonius.pianist.styling.CornerStyle
import dev.antonius.pianist.styling.LineStyle
import dev.antonius.pianist.styling.PointStyle

val data = listOf(-21.0f to 17.0f, -17f to 8f, -15f to 13f, -12f to 7f, -10f to 5f, -5f to 3f, 0.4f to 15.0f, 3.0f to 5.0f,  5.3f to 7.0f, ).sortedBy { it.first }

data class ViewState(
    val pointStyle: PointStyle,
    val lineStyle: LineStyle,
)


sealed class Tabs {
    object ExampleCharts: Tabs()
    object TweakChart: Tabs()
}
@Composable @OptIn(ExperimentalMaterial3Api::class)
fun DemoApp(padding: Dp = 16.dp) {


    var index by remember { mutableStateOf<Tabs>(Tabs.ExampleCharts) }

    Scaffold(
        bottomBar = { NavigationBar {
            NavigationBarItem(index == Tabs.ExampleCharts, { index = Tabs.ExampleCharts }, { Icon(Icons.Filled.Home, "Tweak Chart") })
            NavigationBarItem(index == Tabs.TweakChart, { index = Tabs.TweakChart}, { Icon(Icons.Filled.Settings, "Chart Examples") })
        } }
    ) { innerPadding ->
        val appliedPadding = PaddingValues(
            innerPadding.calculateStartPadding(LayoutDirection.Ltr) + padding,
            innerPadding.calculateTopPadding() + padding,
            innerPadding.calculateEndPadding(LayoutDirection.Ltr) + padding,
            innerPadding.calculateBottomPadding()
        )

        when (index) {
            Tabs.ExampleCharts -> ExampleChartsTab(appliedPadding)
            Tabs.TweakChart -> TweakChartTab (appliedPadding)
        }
    }
}

@Composable @ExperimentalMaterial3Api
fun TweakChartTab(padding: PaddingValues) {
    var selected by remember { mutableStateOf(ViewState(PointStyle.None, LineStyle(width = 8.dp))) }

    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(
            start = padding.calculateStartPadding(LayoutDirection.Ltr),
            end = padding.calculateEndPadding(LayoutDirection.Ltr),
            top = padding.calculateTopPadding(),
            bottom = 0.dp)
        ) {
            LineChart(
                data,
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.4f)
                    .padding(16.dp)
                    .padding(bottom = 32.dp),
                lineStyle = selected.lineStyle,
                pointStyle = selected.pointStyle,
                backgroundStyle = BackgroundStyle.HorizontalLines(5, width = selected.lineStyle.width / 8)
            )

            VerticalScrollView {
                Column(Modifier.padding(bottom = padding.calculateBottomPadding())) {
                    LineStyleSelector(selected.lineStyle) { selected = selected.copy(lineStyle = it) }
                    PointStyleSelector(selected.pointStyle) { selected = selected.copy(pointStyle = it) }
                }
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
        Text(label, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
        Slider(value, { action(it) }, Modifier.padding(start = 8.dp), valueRange = 1f..24f, colors = colors())
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

@Composable
fun colors(
    thumbColor: Color = MaterialTheme.colorScheme.primary,
    disabledThumbColor: Color = MaterialTheme.colorScheme.onSurface
        .copy(alpha = ContentAlpha.disabled)
        .compositeOver(MaterialTheme.colorScheme.surface),
    activeTrackColor: Color = MaterialTheme.colorScheme.primary,
    inactiveTrackColor: Color = activeTrackColor.copy(alpha = SliderDefaults.InactiveTrackAlpha),
    disabledActiveTrackColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = SliderDefaults.DisabledActiveTrackAlpha),
    disabledInactiveTrackColor: Color =
        disabledActiveTrackColor.copy(alpha = SliderDefaults.DisabledInactiveTrackAlpha),
    activeTickColor: Color = contentColorFor(activeTrackColor).copy(alpha = SliderDefaults.TickAlpha),
    inactiveTickColor: Color = activeTrackColor.copy(alpha = SliderDefaults.TickAlpha),
    disabledActiveTickColor: Color = activeTickColor.copy(alpha = SliderDefaults.DisabledTickAlpha),
    disabledInactiveTickColor: Color = disabledInactiveTrackColor
        .copy(alpha = SliderDefaults.DisabledTickAlpha)
): SliderColors = SliderDefaults.colors(
    thumbColor = thumbColor,
    disabledThumbColor = disabledThumbColor,
    activeTrackColor = activeTrackColor,
    inactiveTrackColor = inactiveTrackColor,
    disabledActiveTrackColor = disabledActiveTrackColor,
    disabledInactiveTrackColor = disabledInactiveTrackColor,
    activeTickColor = activeTickColor,
    inactiveTickColor = inactiveTickColor,
    disabledActiveTickColor = disabledActiveTickColor,
    disabledInactiveTickColor = disabledInactiveTickColor
)