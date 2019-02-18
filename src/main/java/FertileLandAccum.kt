import java.util.*

data class FertileLandAccum(val current: Node, val areasMap: MutableMap<Int, Int>, val queue: LinkedList<Node>, var landAccum: Int)
