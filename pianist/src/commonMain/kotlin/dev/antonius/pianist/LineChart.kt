package dev.antonius.pianist

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.antonius.pianist.styling.BackgroundStyle
import dev.antonius.pianist.styling.LineStyle
import dev.antonius.pianist.styling.PointStyle
import java.lang.Float.min

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

    var tapPosition by remember { mutableStateOf(-1f) }

    // TODO: Do not ignore absolute tap position
    Canvas(modifier.pointerInput(Unit) {
        detectTapGestures { tapPosition = it.x }
    }) {
        val path = Path()
        val positions =  data.map { it.asChartPoint().calculateCanvasPosition(rectangle, size) }
        val first = positions.first()

        drawBackground(backgroundStyle, lineColor, density)

        path.moveTo(first.x, first.y)

        positions.forEachIndexed { index, position ->

            if (index < data.size - 1) {
                path.lineTo(positions[index + 1].x, positions[index + 1].y)
            }

            // TODO: This is a temporary solution. Please replace by a more customizable one
            val tapTolerance = min(
                if (index > 0) position.x - positions[index - 1].x else Float.POSITIVE_INFINITY,
                if (index < positions.lastIndex) positions[index + 1].x - position.x else Float.POSITIVE_INFINITY
            ) / 2.5f

            if (tapPosition >= 0f && tapPosition <= size.width && tapPosition in position.x.withTolerance(tapTolerance)) {
                drawLine(
                    lineColor,
                    start = Offset(position.x, 0f),
                    end = Offset(position.x, size.height),
                    strokeWidth = 4.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(12.dp.toPx(), 4.dp.toPx()))
                )

                drawCircle(color, 8.dp.toPx(), position)
            }

            when (pointStyle) {
                is PointStyle.Circle -> drawCircle(color, center = Offset(position.x, position.y), radius = pointStyle.radius.toPx())
                is PointStyle.Square -> drawRect(color, Offset(
                    position.x - pointStyle.length.toPx() / 2f,
                    position.y - pointStyle.length.toPx() / 2f), Size(pointStyle.length.toPx(), pointStyle.length.toPx()))
                is PointStyle.None -> Unit
            }
        }
        drawPath(path, color, style = stroke)
    }
}

@Composable
internal fun LineStyle.toStroke() = Stroke(width = with(LocalDensity.current) { width.toPx() }, miter, cap, join, pathEffect)

internal fun Float.withTolerance(tolerance: Float) = (this - tolerance)..(this + tolerance)