package dev.antonius.pianist

import dev.antonius.common.ChartPoint
import dev.antonius.common.ChartRectangle
import kotlin.test.Test
import kotlin.test.assertEquals

class ChartDimensionTest {
    @Test
    fun testChartPoint() {
        val x = 5.0
        val y = 3.0

        val point = ChartPoint(x, y)

        assertEquals(x, point.x)
        assertEquals(x, point.horizontal)

        assertEquals(y, point.y)
        assertEquals(y, point.vertical)
    }

    @Test
    fun testChartRectangleFromDoubles() {
        val bottom = 20.0
        val top = 42.0
        val right = 24.0
        val left = 10.0

        testRectangleWithValues(left, top, right, bottom)
    }

    @Test
    fun testChartRectangleFromPoints() {
        val bottomLeft = ChartPoint(-10.0, 5.0)
        val topRight = ChartPoint(21.0, 42.0)

        val rectangle = ChartRectangle(bottomLeft, topRight)

        assertEquals(bottomLeft.vertical, rectangle.bottom)
        assertEquals(bottomLeft.horizontal, rectangle.left)

        assertEquals(topRight.vertical, rectangle.top)
        assertEquals(topRight.horizontal, rectangle.right)

        assertEquals(bottomLeft, rectangle.bottomLeft)
        assertEquals(topRight, rectangle.topRight)

        assertEquals(ChartPoint(bottomLeft.horizontal, topRight.vertical), rectangle.topLeft)
        assertEquals(ChartPoint(topRight.horizontal, bottomLeft.vertical), rectangle.bottomRight)
    }

    @Test
    fun testChartRectangleWithNegativeValues() {
        val bottom = -20.0
        val top = 52.0
        val right = 34.0
        val left = -10.0

        testRectangleWithValues(left, top, right, bottom)
    }

    @Test
    fun testChartRectangleFromData() {
        val top = 42.0
        val bottom = -21.0
        val right = 55.0
        val left = 1.0

        val data = listOf(
            22.0 to 5.0,
            11.0 to 27.0,
            12.0 to 32.0,
            right to 13.3,
            1.3 to -12.0,
            left to top,
            25.7 to bottom,
        )

        val rectangle = ChartRectangle.from(data)

        assertEquals(top, rectangle.top)
        assertEquals(bottom, rectangle.bottom)
        assertEquals(left, rectangle.left)
        assertEquals(right, rectangle.right)
    }

    private fun <N: Number> testRectangleWithValues(left: N, top: N, right: N, bottom: N) {
        val rectangle = ChartRectangle(left, top, right, bottom)

        assertEquals(bottom, rectangle.bottom)
        assertEquals(bottom, rectangle.minY)

        assertEquals(top, rectangle.top)
        assertEquals(top, rectangle.maxY)

        assertEquals(left, rectangle.left)
        assertEquals(left, rectangle.minX)

        assertEquals(right, rectangle.right)
        assertEquals(right, rectangle.maxX)
    }
}