data class Rectangle(val bottomLeft: Pair<Int, Int>, val topRight: Pair<Int, Int>) {
    init {
        if (bottomLeft.first > topRight.first ||
                bottomLeft.second > topRight.second) {
            throw Error("Top right corner must be above and to the right of bottom left corner.")
        }
        if (bottomLeft.first < 0 || bottomLeft.second < 0) {
            throw Error("Barren land must be within bounds of farm.")
        }
    }

    private fun getHeight(): Int {
        return (topRight.second + 1) - bottomLeft.second
    }

    private fun getWidth(): Int {
        return (topRight.first + 1) - bottomLeft.first
    }

    fun getArea(): Int {
        return getHeight() * getWidth()
    }
}
