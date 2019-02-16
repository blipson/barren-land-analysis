# Barren Land Analysis

## Dependencies and Libraries

- Java 11
- Kotlin 1.3.21
- Gradle 5.2.1
- JUnit 5.4.0

## Running Locally
`./gradlew clean run`

Alternatively you can import the project into IntelliJ and make a "Kotlin Application" run configuration with the main class set to `Main.kt`.

## Testing Locally
`./gradlew clean test`

Alternatively you can import the project into IntelliJ and make a "JUnit" run configuration with the test dir set to `barren-land-analysis/src/test/java`.

## Interaction
When the application gets run you'll see this prompt in standard out:

    Enter rectangles in the format
    {"x1 y1 x2 y2", "x1 y1 x2 y2", ...}

Enter the rectangles into standard in with that format to get results in the format of:

    Fertile land areas are: [22816, 192608]

The program will handle a few formatting issues, like if you accidentally type in extra spaces or tabs, but you should generally try to keep your input clean.

## Other Notes

- Sets up a 2D array of integers to represent the farm. Because of the natural ordering of this, the output from the `print()` method in farm goes from top left to bottom right. X denotes the row that the number's in, and Y denotes the column. This might seem counter-intuitive, but if you simply imagine the graph to be flipped 90 degrees clockwise it makes sense. A future feature might be a `prettyPrint()` method that goes from bottom left to top right.
- Fertile lands are denoted with a 0, barren lands are denoted with a 1. As the program executes, spaces that it's already checked will be denoted with the next highest number, incrementing each time a new fertile area has begun calculation. So it'll go 2, 3, 4, ... depending on the number of fertile areas. Barren lands will remain 1 after processing.
- Uses a simple breadth-first search approach to find and process fertile areas. Traversal is done using a queue (`LinkedList`). It'll process the current node and then go to all that node's neighbors if they're within bounds and haven't been processed yet.

## General Algorithm Example
Imagine our farm is a 20x30 grid. It'll be initialized to look like this:

    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000
    000000000000000000000000000000

The user then inputs the following rectangle string:

    {“2 9 18 10”, “2 20 18 21”, “6 3 7 27”, “13 3 14 27”}

The barren lands will get filled into the farm like this:

    000000000000000000000000000000
    000000000000000000000000000000
    000000000110000000001100000000
    000000000110000000001100000000
    000000000110000000001100000000
    000000000110000000001100000000
    000111111111111111111111111100
    000111111111111111111111111100
    000000000110000000001100000000
    000000000110000000001100000000
    000000000110000000001100000000
    000000000110000000001100000000
    000000000110000000001100000000
    000111111111111111111111111100
    000111111111111111111111111100
    000000000110000000001100000000
    000000000110000000001100000000
    000000000110000000001100000000
    000000000110000000001100000000
    000000000000000000000000000000

We can see visually that there are two areas of fertile land in this case, one inner square, and one outer polygon. The fertile land area search will start at `0, 0` (the top left), and go until it finds fertile area. Since the top left is a 0, it starts calculating the area of the outer land right away. Going top left to bottom right it'll check all fertile land with a breadth-first search going neighbor by neighbor, all the while storing the number of plots of land it hits in a hashmap, where the key denotes which fertile area it's processing.

    200000000000000000000000000000        220000000000000000000000000000        222000000000000000000000000000        222222222222222222222222222222
    000000000000000000000000000000        200000000000000000000000000000        220000000000000000000000000000        222222222222222222222222222222
    000000000110000000001100000000        000000000110000000001100000000        200000000110000000001100000000        222222222112222222221122222222
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222112222222221122222222
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222112222222221122222222
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222112222222221122222222
    000111111111111111111111111100        000111111111111111111111111100        000111111111111111111111111100        222111111111111111111111111122
    000111111111111111111111111100        000111111111111111111111111100        000111111111111111111111111100        222111111111111111111111111122
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222110000000001122222222
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222110000000001122222222
    000000000110000000001100000000   ->   000000000110000000001100000000   ->   000000000110000000001100000000  ....  222222222110000000001122222222
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222110000000001122222222
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222110000000001122222222
    000111111111111111111111111100        000111111111111111111111111100        000111111111111111111111111100        222111111111111111111111111122
    000111111111111111111111111100        000111111111111111111111111100        000111111111111111111111111100        222111111111111111111111111122
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222112222222221122222222
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222112222222221122222222
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222112222222221122222222
    000000000110000000001100000000        000000000110000000001100000000        000000000110000000001100000000        222222222112222222221122222222
    000000000000000000000000000000        000000000000000000000000000000        000000000000000000000000000000        222222222222222222222222222222

`...` denotes continued iteration until all values are calculated. The hashmap values at each stage would be:

    [{2: 1}] -> [{2: 3}] -> [{2: 6}] -> [{2: 10}] ... [{2: 403}]

Our initial iterator, which is still at the last position that we started the previous fertile land area calculation at (`0, 0`), will now move up the farm, row value by row value until it reaches the end of the column and will then iterate to the next column. It'll continue until it finds a new piece of fertile land to process (in this case at `8, 11`). Then it'll do the same process:

    222222222222222222222222222222        222222222222222222222222222222        222222222222222222222222222222        222222222222222222222222222222
    222222222222222222222222222222        222222222222222222222222222222        222222222222222222222222222222        222222222222222222222222222222
    222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222
    222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222
    222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222
    222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222
    222111111111111111111111111122        222111111111111111111111111122        222111111111111111111111111122        222111111111111111111111111122
    222111111111111111111111111122        222111111111111111111111111122        222111111111111111111111111122        222111111111111111111111111122
    222222222113000000001122222222        222222222113300000001122222222        222222222113330000001122222222        222222222113333333331122222222
    222222222110000000001122222222        222222222113000000001122222222        222222222113300000001122222222        222222222113333333331122222222
    222222222110000000001122222222   ->   222222222110000000001122222222   ->   222222222113000000001122222222  ....  222222222113333333331122222222
    222222222110000000001122222222        222222222110000000001122222222        222222222110000000001122222222        222222222113333333331122222222
    222222222110000000001122222222        222222222110000000001122222222        222222222110000000001122222222        222222222113333333331122222222
    222111111111111111111111111122        222111111111111111111111111122        222111111111111111111111111122        222111111111111111111111111122
    222111111111111111111111111122        222111111111111111111111111122        222111111111111111111111111122        222111111111111111111111111122
    222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222
    222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222
    222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222
    222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222        222222222112222222221122222222
    222222222222222222222222222222        222222222222222222222222222222        222222222222222222222222222222        222222222222222222222222222222

The hashmap values at each stage would be:

    [{2: 403}, {3: 1}] -> [{2: 403}, {3: 3}] -> [{2: 403}, {3: 6}] -> [{2: 403}, {3, 10}] ... [{2: 403}, {3: 45}]

The iterator, which is still at `8, 11` would continue on it's merry way until reaching the end, since there are no more uncalculated fertile lands. The application will then take it's final values in the hashmap, sort them in ascending order, and return them as:

    [45, 403]
