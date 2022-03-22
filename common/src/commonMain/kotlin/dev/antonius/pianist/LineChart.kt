package dev.antonius.pianist

import androidx.compose.runtime.Composable

@Composable
fun LineChart(data: Collection<Pair<Double, Double>>) {
    val rectangle = ChartRectangle.from(data)
}