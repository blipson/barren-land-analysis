import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TestRectangle {
    @Test
    fun testTopBelowBottom() {
        assertThrows(Error::class.java) {
            Rectangle(3 to 3, 5 to 1)
        }
    }

    @Test
    fun testRightBehindLeft() {
        assertThrows(Error::class.java) {
            Rectangle(3 to 3, 1 to 5)
        }
    }

    @Test
    fun testNegatives() {
        assertThrows(Error::class.java) {
            Rectangle(-3 to 3, 1 to 5)
        }
        assertThrows(Error::class.java) {
            Rectangle(3 to -3, 1 to 5)
        }
        assertThrows(Error::class.java) {
            Rectangle(-3 to -3, 1 to 5)
        }
    }

    @Test
    fun testGetArea() {
        assertEquals(25, Rectangle(0 to 0, 4 to 4).getArea())
    }
}
