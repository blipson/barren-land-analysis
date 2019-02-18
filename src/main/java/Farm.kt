import java.util.*

class Farm(private val width: Int, private val height: Int) {
    val land = Array(width) { IntArray(height) }

    init {
        for (x in 0 until width) {
            for (y in 0 until height) {
                // Fertile will be denoted with 0
                land[x][y] = 0
            }
        }
    }

    fun print() {
        land.map { row ->
            row.map { plot -> print("$plot") }
            print("\n")
        }
    }

    fun markBarrenLands(rectangles: List<Rectangle>) {
        rectangles.map { rectangle ->
            for (x in 0 until width) {
                for (y in 0 until height) {
                    if (x >= rectangle.bottomLeft.x &&
                            x <= rectangle.topRight.x &&
                            y >= rectangle.bottomLeft.y &&
                            y <= rectangle.topRight.y) {
                        // barren will be denoted with 1
                        land[x][y] = 1
                    }
                }
            }
        }
    }

    private fun addNextNodesToQueue(fertileLandAccum: FertileLandAccum, landValue: Int): Pair<MutableMap<Int, Int>, Int> {
        val node = fertileLandAccum.queue.pop()
        if (land[node.x][node.y] == 0) {
            if (node.x > 0) {
                if (land[node.x - 1][node.y] == 0) {
                    fertileLandAccum.queue.add(Node(node.x - 1, node.y))
                }
            }
            if (node.x < width - 1) {
                if (land[node.x + 1][node.y] == 0) {
                    fertileLandAccum.queue.add(Node(node.x + 1, node.y))
                }
            }
            if (node.y > 0) {
                if (land[node.x][node.y - 1] == 0) {
                    fertileLandAccum.queue.add(Node(node.x, node.y - 1))
                }
            }
            if (node.y < height - 1) {
                if (land[node.x][node.y + 1] == 0) {
                    fertileLandAccum.queue.add(Node(node.x, node.y + 1))
                }
            }
            land[node.x][node.y] = landValue
            fertileLandAccum.areasMap[landValue] = fertileLandAccum.areasMap[landValue]!! + 1 // Will never be null, but still needs a check in order to be compatible with the Java HashMap .get() function
        }
        return fertileLandAccum.areasMap to landValue
    }

    private fun startNewFertileAreaCalculation(fertileLandAccum: FertileLandAccum, landValue: Int): Pair<MutableMap<Int, Int>, Int> {
        val node = Node(fertileLandAccum.current.x, fertileLandAccum.current.y)
        if (land[fertileLandAccum.current.x][fertileLandAccum.current.y] == 0) {
            fertileLandAccum.areasMap[landValue + 1] = 0
            fertileLandAccum.queue.add(node)
        }
        return fertileLandAccum.areasMap to landValue + 1
    }

    private tailrec fun getFertileLands(fertileLandAccum: FertileLandAccum, landValue: Int): MutableMap<Int, Int> {
        return if (fertileLandAccum.current.x >= width || fertileLandAccum.current.y >= height) {
            fertileLandAccum.areasMap
        }
        else {
            val (nextCurrent, nextResult) = if (fertileLandAccum.queue.isEmpty()) {
                if (fertileLandAccum.current.x == width - 1) {
                    Node(0, fertileLandAccum.current.y + 1) to startNewFertileAreaCalculation(fertileLandAccum, landValue)
                } else {
                    Node(fertileLandAccum.current.x + 1, fertileLandAccum.current.y) to startNewFertileAreaCalculation(fertileLandAccum, landValue)
                }
            } else {
                fertileLandAccum.current to addNextNodesToQueue(fertileLandAccum, landValue)
            }
            getFertileLands(FertileLandAccum(nextCurrent, nextResult.first, fertileLandAccum.queue), nextResult.second)
        }
    }

    fun getFertileLands(): List<Int> {
        return getFertileLands(FertileLandAccum(Node(0, 0), mutableMapOf(), LinkedList()), 1).values.toList().sorted()
    }
}
