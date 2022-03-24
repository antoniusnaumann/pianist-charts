package dev.antonius.pianist.styling

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
sealed class BackgroundStyle {
    object None: BackgroundStyle()

    class HorizontalLines(
        val count: Int,
        val width: Dp = 1.dp,
        val cap: StrokeCap = StrokeCap.Round,
        val pathEffect: PathEffect? = null
    ): BackgroundStyle()

    class MultiBackground(vararg val styles: BackgroundStyle): BackgroundStyle() {
        override fun combine(vararg others: BackgroundStyle): BackgroundStyle = MultiBackground(*this.styles, *others)
    }

    open fun combine(vararg others: BackgroundStyle): BackgroundStyle = MultiBackground(this, *others)
}