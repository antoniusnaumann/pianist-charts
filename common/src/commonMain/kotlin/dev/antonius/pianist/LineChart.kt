package dev.antonius.common

import androidx.compose.runtime.*

@Composable
fun LineChart(data: Collection<Pair<Double, Double>>) {
    val rectangle = ChartRectangle.from(data)
}