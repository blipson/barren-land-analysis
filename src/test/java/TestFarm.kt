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
        testFarm.markBarrenLands(listOf(Rectangle(Node(0, 0), Node(1, 1)), Rectangle(Node(2, 2), Node(4, 4))))
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
        testFarm.markBarrenLands(listOf(Rectangle(Node(0, 0), Node(1, 0))))
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
        testFarm.markBarrenLands(listOf(Rectangle(Node(0, 0), Node(1, 1)), Rectangle(Node(2, 2), Node(4, 4)), Rectangle(Node(1, 3), Node(3, 4))))
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
        testFarm.markBarrenLands(listOf(Rectangle(Node(0, 0), Node(1, 1)), Rectangle(Node(2, 2), Node(4, 4)), Rectangle(Node(1, 3), Node(3, 4))))
        assertEquals(listOf(4, 6), testFarm.getFertileLands())
    }

    @Test
    fun testGetFertileLandsReadme() {
        val realFarm = Farm(20, 30)
        realFarm.markBarrenLands(listOf(Rectangle(Node(2, 9), Node(18, 10)), Rectangle(Node(2, 20), Node(18, 21)), Rectangle(Node(6, 3), Node(7, 27)), Rectangle(Node(13, 3), Node(14, 27))))
        assertEquals(listOf(45, 403), realFarm.getFertileLands())
    }

    @Test
    fun testGetFertileLandsFirstSample() {
        val realFarm = Farm(400, 600)
        realFarm.markBarrenLands(listOf(Rectangle(Node(0, 292), Node(399, 307))))
        assertEquals(listOf(116800, 116800), realFarm.getFertileLands())
    }

    @Test
    fun testGetFertileLandsSecondSample() {
        val realFarm = Farm(400, 600)
        realFarm.markBarrenLands(listOf(Rectangle(Node(48, 192), Node(351, 207)), Rectangle(Node(48, 392), Node(351, 407)), Rectangle(Node(120, 52), Node(135, 547)), Rectangle(Node(260, 52), Node(275, 547))))
        assertEquals(listOf(22816, 192608), realFarm.getFertileLands())
    }
}
