package dev.antonius.pianist

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.antonius.pianist.styling.BackgroundStyle
import dev.antonius.pianist.styling.LineStyle
import dev.antonius.pianist.styling.PointStyle

@Composable
fun LineChart(
    data: List<Pair<Float, Float>>,
    modifier: Modifier,
    lineStyle: LineStyle = LineStyle(2.dp),
    pointStyle: PointStyle = PointStyle.None,
    backgroundStyle: BackgroundStyle = BackgroundStyle.None,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    val stroke = lineStyle.toStroke()
    val rectangle = ChartRectangle.from(data)
    val lineColor = MaterialTheme.colorScheme.outline
    val density = LocalDensity.current

    Canvas(modifier) {
        val path = Path()
        val first = data.first().asChartPoint().calculateCanvasPosition(rectangle, size)

        drawBackground(backgroundStyle, lineColor, density)

        path.moveTo(first.x, first.y)

        data.forEachIndexed { index, point ->
            val current = point.asChartPoint()
            val from = current.calculateCanvasPosition(rectangle, size)

            if (index < data.size - 1) {
                val next = data[index + 1].asChartPoint()
                val to = next.calculateCanvasPosition(rectangle, size)

                path.lineTo(to.x, to.y)
            }

            when (pointStyle) {
                is PointStyle.Circle -> drawCircle(color, center = Offset(from.x, from.y), radius = pointStyle.radius.toPx())
                is PointStyle.Square -> drawRect(color, Offset(from.x - pointStyle.length.toPx() / 2f, from.y - pointStyle.length.toPx() / 2f), Size(pointStyle.length.toPx(), pointStyle.length.toPx()))
                is PointStyle.None -> Unit
            }
        }
        drawPath(path, color, style = stroke)
    }
}

@Composable
internal fun LineStyle.toStroke() = Stroke(width = with(LocalDensity.current) { width.toPx() }, miter, cap, join, pathEffect)