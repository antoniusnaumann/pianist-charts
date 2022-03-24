package dev.antonius.pianist.styling

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

/**
 * Provides density-independent styling information for a line chart line
 */
@Immutable
class LineStyle(
    val width: Dp,
    val miter: Float = Stroke.DefaultMiter,
    val cap: StrokeCap = StrokeCap.Round,
    val join: StrokeJoin = StrokeJoin.Round,
    val pathEffect: PathEffect? = null
) {
    /**
     * Convenience initializer to use a corner style instead of cap and join
     */
    constructor(
        width: Dp,
        miter: Float = Stroke.DefaultMiter,
        cornerStyle: CornerStyle = CornerStyle.Default,
        pathEffect: PathEffect? = null
    ): this(width, miter, cornerStyle.strokeCap, cornerStyle.strokeJoin, pathEffect)
}