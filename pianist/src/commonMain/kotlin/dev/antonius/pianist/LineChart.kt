package dev.antonius.pianist

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
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
) = LineChart(data, { }, modifier, lineStyle, pointStyle, backgroundStyle, color)

@Composable
fun LineChart(
    data: List<Pair<Float, Float>>,
    onHover: (Float) -> Unit,
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

    var dragPosition by remember { mutableStateOf(-1f) }

    val interaction = MutableInteractionSource()

    // TODO: Do not ignore absolute tap position
    Canvas(modifier.hoverable(interaction).draggable(rememberDraggableState { dragPosition += it; onHover(dragPosition) }, Orientation.Horizontal)) {
        val path = Path()
        val positions =  data.map { it.asChartPoint().calculateCanvasPosition(rectangle, size) }

        val first = positions.first()

        drawBackground(backgroundStyle, lineColor, density)

        path.moveTo(first.x, first.y)

        positions.forEachIndexed { index, position ->

            if (index < data.size - 1) {
                path.lineTo(positions[index + 1].x, positions[index + 1].y)
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

        // TODO: Temporary, please remove in favor of a more customizable solution
        if (dragPosition >= 0f && dragPosition <= size.width) {
            drawLine(
                lineColor,
                start = Offset(dragPosition, 0f),
                end = Offset(dragPosition, size.height),
                strokeWidth = 4.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(50f, 20f))
            )

            // Find intersection
            val start = positions.last { it.x <= dragPosition }
            val stop = positions.first { it.x >= dragPosition }
            val proportion = (dragPosition - start.x) / (stop.x - start.x)

            // TODO: Calculate proportions
            val interpolated = Offset(dragPosition, (proportion * stop.y + (1 - proportion) * start.y))
            drawCircle(color, 8.dp.toPx(), interpolated)
        }
    }
}

@Composable
internal fun LineStyle.toStroke() = Stroke(width = with(LocalDensity.current) { width.toPx() }, miter, cap, join, pathEffect)