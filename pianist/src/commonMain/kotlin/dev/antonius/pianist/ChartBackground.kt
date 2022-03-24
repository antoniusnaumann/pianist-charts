package dev.antonius.pianist

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import dev.antonius.pianist.styling.BackgroundStyle
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.Density

internal fun DrawScope.drawBackground(style: BackgroundStyle, color: Color, density: Density) {
    when (style) {
        BackgroundStyle.None -> {}
        is BackgroundStyle.HorizontalLines -> drawHorizontalLines(style, color, density)
        is BackgroundStyle.MultiBackground -> style.styles.forEach { drawBackground(it, color, density) }
    }
}

internal fun DrawScope.drawHorizontalLines(style: BackgroundStyle.HorizontalLines, color: Color, density: Density) {
    (0..style.count).forEach {
        val y = it.toFloat() / style.count.toFloat() * size.height

        drawLine(color,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = with(density) { style.width.toPx() },
            cap = style.cap,
            pathEffect = style.pathEffect,
        )
    }
}