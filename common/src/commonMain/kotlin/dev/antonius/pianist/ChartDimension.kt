package dev.antonius.pianist

internal data class ChartPoint<N: Number>(val x: N, val y: N) {
    inline val horizontal get() = x
    inline val vertical get() = y
}

internal data class ChartRectangle<N: Number>(val left: N, val top: N, val right: N, val bottom: N) {
    constructor(bottomLeft: ChartPoint<N>, topRight: ChartPoint<N>): this(bottomLeft.horizontal, topRight.vertical, topRight.horizontal, bottomLeft.vertical)

    inline val minX get() = left
    inline val minY get() = bottom
    inline val maxX get() = right
    inline val maxY get() = top

    val bottomRight get() = ChartPoint(right, bottom)
    val bottomLeft get() = ChartPoint(left, bottom)
    val topLeft get() = ChartPoint(left, top)
    val topRight get() = ChartPoint(right, top)

    val width get() = right.toDouble() - left.toDouble()
    val height get() = top.toDouble() - bottom.toDouble()

    companion object {
        fun <N: Number> from(data: Collection<Pair<N, N>>) = ChartRectangle(
            left = data.left,
            top = data.top,
            right = data.right,
            bottom = data.bottom,
        )

        private inline val <N: Number> Collection<Pair<N, N>>.left: Double get() = minOf { it.first.toDouble() }
        private inline val <N: Number> Collection<Pair<N, N>>.right: Double get() = maxOf { it.first.toDouble() }
        private inline val <N: Number> Collection<Pair<N, N>>.top: Double get() = maxOf { it.second.toDouble() }
        private inline val <N: Number> Collection<Pair<N, N>>.bottom: Double get() = minOf { it.second.toDouble() }
    }
}