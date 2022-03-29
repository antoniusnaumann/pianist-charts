package dev.antonius.pianist.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.antonius.pianist.LineChart
import dev.antonius.pianist.styling.BackgroundStyle
import dev.antonius.pianist.styling.LineStyle
import dev.antonius.pianist.styling.PointStyle

@Composable @ExperimentalMaterial3Api
fun ExampleChartsTab(padding: PaddingValues) {
    Box(Modifier.padding(padding)) {
        ChartCard {
            TemperatureChart()
        }
    }
}

@Composable @ExperimentalMaterial3Api
fun ChartCard( content: @Composable () -> Unit) {
    Card(Modifier.fillMaxWidth().wrapContentHeight()) {
        Column(Modifier.padding(24.dp)) {
            content()
        }
    }
}

@Composable
fun TemperatureChart() {
    val data = listOf(
        1f to 12.3f,
        2f to 15.0f,
        3f to 21f,
        4f to 12f,
        5f to 10f,
        6f to 17f,
        8f to 12f,
        9f to 10f,
        11f to 9f,
    )

    var selected: Int? by remember { mutableStateOf(null) }

    Column(Modifier.padding(bottom = 8.dp), horizontalAlignment = Alignment.End) {
        Row(Modifier.padding(bottom = 24.dp)) {
            Text("Temperature", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.weight(1f))
            Text(selected?.let { "${ data[it].second }°C" } ?: "", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W800, color = MaterialTheme.colorScheme.primary))
        }
        Row(Modifier.fillMaxWidth().aspectRatio(16f / 9f)) {
            Column(Modifier.padding(end = 16.dp), horizontalAlignment = Alignment.End) {
                Text("21°C", style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.weight(1f))
                Text("15°C", style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.weight(1f))
                Text("9°C", style = MaterialTheme.typography.labelLarge)
            }

            LineChart(
                data, Modifier.fillMaxSize().padding(vertical = 8.dp),
                lineStyle = LineStyle(4.dp),
                pointStyle = PointStyle.Circle(5.dp),
                backgroundStyle = BackgroundStyle.HorizontalLines(4, width = 0.35.dp)
            ) { selected = it }
        }

    }
}