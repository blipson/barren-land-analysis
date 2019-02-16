import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TestRectangle {
    @Test
    fun testTopBelowBottom() {
        assertThrows(Error::class.java) {
            Rectangle(Node(3, 3), Node(5, 1))
        }
    }

    @Test
    fun testRightBehindLeft() {
        assertThrows(Error::class.java) {
            Rectangle(Node(3, 3), Node(1, 5))
        }
    }

    @Test
    fun testNegatives() {
        assertThrows(Error::class.java) {
            Rectangle(Node(-3, 3), Node(1, 5))
        }
        assertThrows(Error::class.java) {
            Rectangle(Node(3, -3), Node(1, 5))
        }
        assertThrows(Error::class.java) {
            Rectangle(Node(-3, -3), Node(1, 5))
        }
    }

    @Test
    fun testGetArea() {
        assertEquals(25, Rectangle(Node(0, 0), Node(4, 4)).getArea())
    }
}
