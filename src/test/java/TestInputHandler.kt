import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class TestInputHandler {
    @AfterEach
    fun resetStdIn() {
        System.setIn(System.`in`)
    }

    @Test
    fun testLoadRectanglesHappyPath() {
        val newIn = ByteArrayInputStream("{\"1 2 3 4\", \"2 3 4 5\", \"3 4 5 6\"}".toByteArray())
        System.setIn(newIn)
        assertEquals(listOf(Rectangle(1 to 2, 3 to 4), Rectangle(2 to 3, 4 to 5), Rectangle(3 to 4, 5 to 6)), InputHandler.loadRectangles())
    }

    @Test
    fun testLoadRectanglesStripSpaces() {
        val newIn = ByteArrayInputStream("{\"   1 2 3 4 \", \" 2 3 4 5   \", \" 3 4 5 6 \"}".toByteArray())
        System.setIn(newIn)
        assertEquals(listOf(Rectangle(1 to 2, 3 to 4), Rectangle(2 to 3, 4 to 5), Rectangle(3 to 4, 5 to 6)), InputHandler.loadRectangles())
    }

    @Test
    fun testLoadRectanglesMultipleSpaces() {
        val newIn = ByteArrayInputStream("{\"1  2   3    4\", \"2   3  4   5\", \"3    4  5  6\"}".toByteArray())
        System.setIn(newIn)
        assertEquals(listOf(Rectangle(1 to 2, 3 to 4), Rectangle(2 to 3, 4 to 5), Rectangle(3 to 4, 5 to 6)), InputHandler.loadRectangles())
    }

    @Test
    fun testLoadRectanglesTabs() {
        val newIn = ByteArrayInputStream("{\"\t1\t2\t3\t4\t\", \"\t2\t3\t4\t5\t\", \"\t3\t4\t5\t6\t\"}".toByteArray())
        System.setIn(newIn)
        assertEquals(listOf(Rectangle(1 to 2, 3 to 4), Rectangle(2 to 3, 4 to 5), Rectangle(3 to 4, 5 to 6)), InputHandler.loadRectangles())
    }

    @Test
    fun testLoadRectanglesTabsAndSpacesCombined() {
        val newIn = ByteArrayInputStream("{\"\t1 \t 2\t3   \t4\t    \", \"\t  2  \t3  \t4  \t   5 \t\", \"\t 3   \t4\t    5 \t  6\t \"}".toByteArray())
        System.setIn(newIn)
        assertEquals(listOf(Rectangle(1 to 2, 3 to 4), Rectangle(2 to 3, 4 to 5), Rectangle(3 to 4, 5 to 6)), InputHandler.loadRectangles())
    }

    @Test
    fun testLoadRectanglesTooFew() {
        val newIn = ByteArrayInputStream("{\"1 2 3\", \"2 3 4\", \"3 4 5\"}".toByteArray())
        System.setIn(newIn)
        assertThrows(Error::class.java) {
            InputHandler.loadRectangles()
        }
    }

    @Test
    fun testLoadRectanglesTooMany() {
        val newIn = ByteArrayInputStream("{\"1 2 3 4 5\", \"2 3 4 5 6\", \"3 4 5 6 7\"}".toByteArray())
        System.setIn(newIn)
        assertThrows(Error::class.java) {
            InputHandler.loadRectangles()
        }
    }

    @Test
    fun testLoadRectanglesNegative() {
        val newIn = ByteArrayInputStream("{\"1 2 -3 4\", \"2 -3 4 -5\", \"-3 4 5 -6\"}".toByteArray())
        System.setIn(newIn)
        assertThrows(Error::class.java) {
            InputHandler.loadRectangles()
        }
    }

    @Test
    fun testLoadRectanglesFloats() {
        val newIn = ByteArrayInputStream("{\"1 2.3 3 4\", \"2.1 3 4.5 5\", \"3.7 4 5 6.8\"}".toByteArray())
        System.setIn(newIn)
        assertThrows(NumberFormatException::class.java) {
            InputHandler.loadRectangles()
        }
    }

    @Test
    fun testLoadRectanglesStrings() {
        val newIn = ByteArrayInputStream("{\"1 two 3 4\", \"two 3 four 5\", \"three 4 5 six\"}".toByteArray())
        System.setIn(newIn)
        assertThrows(NumberFormatException::class.java) {
            InputHandler.loadRectangles()
        }
    }

    @Test
    fun testLoadRectanglesNoInput() {
        val newIn = ByteArrayInputStream("\n".toByteArray())
        System.setIn(newIn)
        assertThrows(Error::class.java) {
            InputHandler.loadRectangles()
        }
    }

    @Test
    fun testLoadRectanglesSpecialQuoteCharacter() {
        val newIn = ByteArrayInputStream("{“48 192 351 207”, “48 392 351 407”, “120 52 135 547”, “260 52 275 547”}".toByteArray())
        System.setIn(newIn)
        assertEquals(listOf(Rectangle(48 to 192, 351 to 207), Rectangle(48 to 392, 351 to 407), Rectangle(120 to 52, 135 to 547), Rectangle(260 to 52, 275 to 547)), InputHandler.loadRectangles())
    }
}
