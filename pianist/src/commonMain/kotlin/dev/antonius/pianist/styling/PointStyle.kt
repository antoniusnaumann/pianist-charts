package dev.antonius.pianist.styling

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
sealed class PointStyle {
    object None: PointStyle()
    class Circle(val radius: Dp = 4.dp): PointStyle()
    class Square(val length: Dp = 8.dp): PointStyle()
}