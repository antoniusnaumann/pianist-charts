package dev.antonius.pianist

import androidx.compose.ui.geometry.Size

/**
 * The coordinate of a point on a chart
 */
internal sealed class ChartPoint {
    abstract val x: Float
    abstract val y: Float

    inline val horizontal get() = x
    inline val vertical get() = y

    companion object {
        operator fun  invoke(x: Float, y: Float): ChartPoint = CommonPoint(x, y)
    }
}

private data class CommonPoint(override val x: Float, override val y: Float): ChartPoint()

/**
 * A point on a chart which was transformed so that both bottom and left of the chart where set to 0
 */
internal data class ZeroedPoint(override val x: Float, override val y: Float): ChartPoint()

/**
 * A point on a chart which was transformed so that both chart axis go from 0 to 1
 */
internal data class NormalizedPoint(override val x: Float, override val y: Float): ChartPoint()

internal fun  Pair<Float, Float>.asChartPoint(): ChartPoint = ChartPoint(first, second)

internal val Pair<Float, Float>.x get() = first
internal val Pair<Float, Float>.y get() = second

/**
 * Boundaries of a plotted chart
 */
internal data class ChartRectangle(val left: Float, val top: Float, val right: Float, val bottom: Float) {
    constructor(bottomLeft: ChartPoint, topRight: ChartPoint): this(bottomLeft.horizontal, topRight.vertical, topRight.horizontal, bottomLeft.vertical)

    inline val minX get() = left
    inline val minY get() = bottom
    inline val maxX get() = right
    inline val maxY get() = top

    val bottomRight get() = ChartPoint(right, bottom)
    val bottomLeft get() = ChartPoint(left, bottom)
    val topLeft get() = ChartPoint(left, top)
    val topRight get() = ChartPoint(right, top)

    val width get() = right - left
    val height get() = top - bottom

    companion object {
        fun from(data: Collection<Pair<Float, Float>>) = ChartRectangle(
            left = data.left,
            top = data.top,
            right = data.right,
            bottom = data.bottom,
        )

        private inline val Collection<Pair<Float, Float>>.left: Float get() = minOf { it.first }
        private inline val Collection<Pair<Float, Float>>.right: Float get() = maxOf { it.first }
        private inline val Collection<Pair<Float, Float>>.top: Float get() = maxOf { it.second }
        private inline val Collection<Pair<Float, Float>>.bottom: Float get() = minOf { it.second }
    }
}

internal fun ChartPoint.zeroed(chart: ChartRectangle) = ZeroedPoint(x - chart.left, y - chart.bottom)

/**
 * Normalize a point on a chart to [0, 1)
 */
internal fun ZeroedPoint.normalized(chart: ChartRectangle) = NormalizedPoint(x / chart.width, y / chart.height)

internal fun NormalizedPoint.fitOn(canvas: Size) = x * canvas.width to y * canvas.height

internal fun ChartPoint.calculateCanvasPosition(chart: ChartRectangle, canvas: Size) = this
    .zeroed(chart)
    .normalized(chart)
    .fitOn(canvas)