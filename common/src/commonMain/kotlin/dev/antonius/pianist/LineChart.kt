package dev.antonius.pianist

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

sealed class PointStyle {
    object None: PointStyle()
    class Circle(val radius: Float = 15f): PointStyle()
    class Square(val length: Float = 20f): PointStyle()
}

@Composable
fun LineChart(
    data: List<Pair<Float, Float>>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 2.0f,
    strokeCap: StrokeCap = Stroke.DefaultCap,
    pointStyle: PointStyle = PointStyle.None
) {
    val rectangle = ChartRectangle.from(data)
    val color = MaterialTheme.colorScheme.primary

    Canvas(modifier) {

        // TODO: Probably need to do some more transformation because canvas coordinate system could begin top left instead of bottom left

        data.forEachIndexed { index, point ->
            val current = point.asChartPoint()
            val from = current.calculateCanvasPosition(rectangle, size)

            if (index < data.size - 1) {
                val next = data[index + 1].asChartPoint()
                val to = next.calculateCanvasPosition(rectangle, size)

                drawLine(color,
                    start = Offset(from.x, from.y),
                    end = Offset(to.x, to.y),
                    strokeWidth = strokeWidth,
                    cap = strokeCap)
            }

            when (pointStyle) {
                is PointStyle.Circle -> drawCircle(color, center = Offset(from.x, from.y), radius = pointStyle.radius)
                is PointStyle.Square -> drawRect(color, Offset(from.x - pointStyle.length / 2f, from.y - pointStyle.length / 2f), Size(pointStyle.length, pointStyle.length))
                is PointStyle.None -> Unit
            }
        }
    }
}