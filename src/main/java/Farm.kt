import java.util.*

class Farm(private val width: Int, private val height: Int) {
    var land = Array(width) { IntArray(height) }

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

    private val queue: LinkedList<Node> = LinkedList()
    private val areasMap: MutableMap<Int, Int> = mutableMapOf()
    private var landAccum = 1

    private fun addNextNodesToQueue(): MutableMap<Int, Int> {
        val node = queue.pop()
        if (land[node.x][node.y] == 0) {
            if (node.x > 0) {
                if (land[node.x - 1][node.y] == 0) {
                    queue.add(Node(node.x - 1, node.y))
                }
            }
            if (node.x < width - 1) {
                if (land[node.x + 1][node.y] == 0) {
                    queue.add(Node(node.x + 1, node.y))
                }
            }
            if (node.y > 0) {
                if (land[node.x][node.y - 1] == 0) {
                    queue.add(Node(node.x, node.y - 1))
                }
            }
            if (node.y < height - 1) {
                if (land[node.x][node.y + 1] == 0) {
                    queue.add(Node(node.x, node.y + 1))
                }
            }
            land[node.x][node.y] = landAccum
            areasMap[landAccum] = areasMap[landAccum]!! + 1 // Will never be null, but still needs a check in order to be compatible with the Java HashMap .get() function
        }
        return areasMap
    }

    private fun startNewFertileAreaCalculation(current: Node) {
        val node = Node(current.x, current.y)
        if (land[current.x][current.y] == 0) {
            landAccum++
            areasMap[landAccum] = 0
            queue.add(node)
        }
        return
    }

    fun getFertileLands(): List<Int> {
        val current = Node(0, 0)
        while (current.x < width && current.y < height) {
            if (queue.isEmpty()) {
                startNewFertileAreaCalculation(current)
                if (current.x == width - 1) {
                    current.x = 0
                    current.y++
                } else {
                    current.x++
                }
            } else {
                addNextNodesToQueue()
            }
        }
        val result = areasMap.values.toList()
        return result.sorted()
    }
}
