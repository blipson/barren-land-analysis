data class Rectangle(val bottomLeft: Node, val topRight: Node) {
    init {
        if (bottomLeft.x > topRight.x ||
                bottomLeft.y > topRight.y) {
            throw Error("Top right corner must be above and to the right of bottom left corner.")
        }
        if (bottomLeft.x < 0 || bottomLeft.y < 0) {
            throw Error("Barren land must be within bounds of farm.")
        }
    }

    private fun getHeight(): Int {
        return (topRight.y + 1) - bottomLeft.y
    }

    private fun getWidth(): Int {
        return (topRight.x + 1) - bottomLeft.x
    }

    fun getArea(): Int {
        return getHeight() * getWidth()
    }
}
