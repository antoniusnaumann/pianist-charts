package dev.antonius.pianist

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke.Companion.DefaultMiter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
sealed class PointStyle {
    object None: PointStyle()
    class Circle(val radius: Dp = 4.dp): PointStyle()
    class Square(val length: Dp = 8.dp): PointStyle()
}

/**
 * Provides density-independent styling information for a line chart line
 */
@Immutable
class LineStyle(
    val width: Dp,
    val miter: Float = DefaultMiter,
    val cap: StrokeCap = StrokeCap.Round,
    val join: StrokeJoin = StrokeJoin.Round,
    val pathEffect: PathEffect? = null
) {
    /**
     * Convenience initializer to use a corner style instead of cap and join
     */
    constructor(
        width: Dp,
        miter: Float = DefaultMiter,
        cornerStyle: CornerStyle = CornerStyle.Default,
        pathEffect: PathEffect? = null
    ): this(width, miter, cornerStyle.strokeCap, cornerStyle.strokeJoin, pathEffect)
}

/**
 * Convenience class to bundle matching stroke cap and join styles together
 */
@Immutable
class CornerStyle internal constructor(
    val strokeCap: StrokeCap = StrokeCap.Round,
    val strokeJoin: StrokeJoin = StrokeJoin.Round
) {
    companion object {
        val Default = CornerStyle()
        val Round = CornerStyle(StrokeCap.Round, StrokeJoin.Round)
        val Sharp = CornerStyle(StrokeCap.Butt, StrokeJoin.Miter)
        val Flat = CornerStyle(StrokeCap.Square, StrokeJoin.Bevel)
    }
}