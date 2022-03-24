package dev.antonius.pianist

import kotlin.math.absoluteValue
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChartDimensionTest {
    @Test
    fun testChartPoint() {
        val x = 5.0f
        val y = 3.0f

        val point = ChartPoint(x, y)

        assertEquals(x, point.x)
        assertEquals(x, point.horizontal)

        assertEquals(y, point.y)
        assertEquals(y, point.vertical)
    }

    @Test
    fun testChartRectangleFromDoubles() {
        val bottom = 20.0f
        val top = 42.0f
        val right = 24.0f
        val left = 10.0f

        testRectangleWithValues(left, top, right, bottom)
    }

    @Test
    fun testChartRectangleFromPoints() {
        val bottomLeft = ChartPoint(-10.0f, 5.0f)
        val topRight = ChartPoint(21.0f, 42.0f)

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
        val bottom = -20f
        val top = 52f
        val right = 34f
        val left = -10f

        testRectangleWithValues(left, top, right, bottom)
    }

    @Test
    fun testChartRectangleFromData() {
        val top = 42f
        val bottom = -21.0f
        val right = 55.0f
        val left = 1.0f

        val data = listOf(
            22.0f to 5.0f,
            11.0f to 27.0f,
            12.0f to 32.0f,
            right to 13.3f,
            1.3f to -12.0f,
            left to top,
            25.7f to bottom,
        )

        val rectangle = ChartRectangle.from(data)

        assertEquals(top, rectangle.top)
        assertEquals(bottom, rectangle.bottom)
        assertEquals(left, rectangle.left)
        assertEquals(right, rectangle.right)
    }

    @Test
    fun testChartRectangleWidthAndHeight() {
        val top = 55.0f
        val bottom = 10.0f
        val right = 43.2f
        val left = -10.7f

        val rectangle = ChartRectangle(left, top, right, bottom)

        // Quick fix for slight offset in float subtraction
        assertTrue((rectangle.width - 53.9f).absoluteValue < 0.001f)
        assertEquals(45f, rectangle.height)
    }

    @Test
    fun testZeroChartPoint() {
        val minBounds = 17.3f to -21.0f
        val maxBounds = 50f to 150f
        val data = (0..20).map { Random.nextFloat() * maxBounds.x + minBounds.x to Random.nextFloat() * maxBounds.y + minBounds.y } + minBounds
        val points = data.map { ChartPoint(it.x, it.y) }
        val rectangle = ChartRectangle.from(data)

        val zeroed = points.map { it.zeroed(rectangle) }

        assertContains(zeroed, ZeroedPoint(0f, 0f), "No element with 0, 0 found")

        zeroed.forEachIndexed { index, point ->
            assertTrue(point.x >= 0, "point.x was negative: ${point.x} (Original: ${data[index].x})")
            assertTrue(point.y >= 0, "point.x was negative: ${point.y} (Original: ${data[index].y})")
        }
    }

    @Test
    fun testNormalizeChartPoint() {
        val maxBounds = 50f to 150f
        val data = (0..20).map { Random.nextFloat() * maxBounds.x to Random.nextFloat() * maxBounds.y } + (0f to 0f)
        val points = data.map { ZeroedPoint(it.x, it.y) }
        val rectangle = ChartRectangle.from(data)

        val normalized = points.map { it.normalized(rectangle) }

        normalized.forEachIndexed { index, point ->
            assertTrue(point.x >= 0f, "Normalized point.x was negative: ${point.x} (Original: ${data[index].x})")
            assertTrue(point.y >= 0f, "Normalized point.y was negative: ${point.y} (Original: ${data[index].y})")
            assertTrue(point.x <= 1f, "Normalized point.x exceeded 1: ${point.x} (Original: ${data[index].x}, Bound: ${rectangle.maxX})")
            assertTrue(point.y <= 1f, "Normalized point.y exceeded 1: ${point.y} (Original: ${data[index].y}, Bound: ${rectangle.maxY})")
        }
    }

    private fun testRectangleWithValues(left: Float, top: Float, right: Float, bottom: Float) {
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