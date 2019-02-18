package com.blipson

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader

class InputHandler {
    companion object {
        fun loadRectangles(): List<Rectangle> {
            println("Enter rectangles in the format\n{\"x1 y1 x2 y2\", \"x1 y1 x2 y2\", ...}")
            return BufferedReader(InputStreamReader(System.`in`) as Reader).readLine()
                    .replace("{", "")
                    .replace("}", "")
                    .split(",")
                    .map {
                        if (it == "") {
                            throw Error("Input is required.")
                        }
                        val coords = it.replace("\"", "").replace("“", "").replace("”", "").replace(Regex("\\s+"), " ")
                                .trim()
                                .split(" ")
                        // I chose to have the check here rather than in
                        // a separate loop because the extra work done to
                        // potentially create a few unneeded rectangle objects
                        // doesn't outweigh the work that'd be done by
                        // iterating over the collection a second time.
                        if (coords.size != 4 || coords.any { coord -> coord.trim().toInt() < 0 }) {
                            throw Error("All rectangles inputted must have 4 positive integers representing the two corner points.")
                        }
                        Rectangle(
                                Node(coords[0].trim().toInt(), coords[1].trim().toInt()),
                                Node(coords[2].trim().toInt(), coords[3].trim().toInt())
                        )
                    }
        }
    }
}