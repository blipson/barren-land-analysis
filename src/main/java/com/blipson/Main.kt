package com.blipson

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val rectangles = InputHandler.loadRectangles()
            val farm = Farm(400, 600)
            farm.markBarrenLands(rectangles)
            println("Fertile land areas are: ${farm.getFertileLands()}")
        }
    }
}
