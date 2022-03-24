package dev.antonius.pianist.styling

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin

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
        val Sharp = CornerStyle(StrokeCap.Square, StrokeJoin.Miter)
        val Flat = CornerStyle(StrokeCap.Butt, StrokeJoin.Bevel)
    }
}