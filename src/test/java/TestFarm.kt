import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TestFarm {
    private val outContent = ByteArrayOutputStream()
    private val originalOut = System.out
    private val testFarm = Farm(5, 5)

    @BeforeEach
    fun setUpStreams() {
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun restoreStreams() {
        System.setOut(originalOut)
    }

    @Test
    fun testInitialization() {
        assertEquals(5, testFarm.land.size)
        testFarm.land.map { row ->
            assertEquals(5, row.size)
            row.map { plot -> assertEquals(0, plot) }
        }
    }

    @Test
    fun testPrint() {
        testFarm.print()
        assertEquals("00000\n00000\n00000\n00000\n00000\n", outContent.toString())
    }

    @Test
    fun testMarkBarrenHappyPath() {
        testFarm.markBarren(listOf(Rectangle(0 to 0, 1 to 1), Rectangle(2 to 2, 4 to 4)))
        assertEquals(listOf(
                listOf(1, 1, 0, 0, 0),
                listOf(1, 1, 0, 0, 0),
                listOf(0, 0, 1, 1, 1),
                listOf(0, 0, 1, 1, 1),
                listOf(0, 0, 1, 1, 1)
        ), testFarm.land.map { it.toList() }.toList())
    }

    @Test
    fun testMarkBarrenJustOne() {
        testFarm.markBarren(listOf(Rectangle(0 to 0, 1 to 0)))
        assertEquals(listOf(
                listOf(1, 0, 0, 0, 0),
                listOf(1, 0, 0, 0, 0),
                listOf(0, 0, 0, 0, 0),
                listOf(0, 0, 0, 0, 0),
                listOf(0, 0, 0, 0, 0)
        ), testFarm.land.map { it.toList() }.toList())
    }

    @Test
    fun testMarkBarrenOverlap() {
        testFarm.markBarren(listOf(Rectangle(0 to 0, 1 to 1), Rectangle(2 to 2, 4 to 4), Rectangle(1 to 3, 3 to 4)))
        assertEquals(listOf(
                listOf(1, 1, 0, 0, 0),
                listOf(1, 1, 0, 1, 1),
                listOf(0, 0, 1, 1, 1),
                listOf(0, 0, 1, 1, 1),
                listOf(0, 0, 1, 1, 1)
        ), testFarm.land.map { it.toList() }.toList())
    }

    @Test
    fun testGetFertileLandsSimple() {
        testFarm.markBarren(listOf(Rectangle(0 to 0, 1 to 1), Rectangle(2 to 2, 4 to 4), Rectangle(1 to 3, 3 to 4)))
        assertEquals(listOf(4, 6), testFarm.getFertileLands())
    }

    @Test
    fun testGetFertileLandsReadme() {
        val realFarm = Farm(20, 30)
        realFarm.markBarren(listOf(Rectangle(2 to 9, 18 to 10), Rectangle(2 to 20, 18 to 21), Rectangle(6 to 3, 7 to 27), Rectangle(13 to 3, 14 to 27)))
        assertEquals(listOf(45, 403), realFarm.getFertileLands())
    }

    @Test
    fun testGetFertileLandsFirstSample() {
        val realFarm = Farm(400, 600)
        realFarm.markBarren(listOf(Rectangle(0 to 292, 399 to 307)))
        assertEquals(listOf(116800, 116800), realFarm.getFertileLands())
    }

    @Test
    fun testGetFertileLandsSecondSample() {
        val realFarm = Farm(400, 600)
        realFarm.markBarren(listOf(Rectangle(48 to 192, 351 to 207), Rectangle(48 to 392, 351 to 407), Rectangle(120 to 52, 135 to 547), Rectangle(260 to 52, 275 to 547)))
        assertEquals(listOf(22816, 192608), realFarm.getFertileLands())
    }
}
