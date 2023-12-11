data class Pipe(
    val left: Boolean = false,
    val right: Boolean = false,
    val top: Boolean = false,
    val bottom: Boolean = false,
    var steps: Int = -1,
    val start: Boolean = false,
    val i: Int = 0,
    val j: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Pipe) return false
        return i == other.i && j == other.j
    }
}

fun main() {
    fun processLoop(input: List<String>): List<List<Pipe>> {
        var startI = 0
        var startJ = 0
        val map = input.mapIndexed { i, line ->
            line.mapIndexed { j, char ->
                when (char) {
                    '|' -> Pipe(
                        left = false,
                        right = false,
                        top = i > 0,
                        bottom = i < input.lastIndex,
                        i = i, j = j,
                    )

                    '-' -> Pipe(
                        left = j > 0,
                        right = j < line.lastIndex,
                        top = false,
                        bottom = false,
                        i = i, j = j,
                    )

                    'L' -> Pipe(
                        left = false,
                        right = j < line.lastIndex,
                        top = i > 0,
                        bottom = false,
                        i = i, j = j,
                    )

                    'J' -> Pipe(
                        left = j > 0,
                        right = false,
                        top = i > 0,
                        bottom = false,
                        i = i, j = j,
                    )

                    '7' -> Pipe(
                        left = j > 0,
                        right = false,
                        top = false,
                        bottom = i < input.lastIndex,
                        i = i, j = j,
                    )

                    'F' -> Pipe(
                        left = false,
                        right = j < line.lastIndex,
                        top = false,
                        bottom = i < input.lastIndex,
                        i = i, j = j,
                    )

                    '.' -> Pipe(i = i, j = j)
                    else -> {
                        val left = j > 0 && line[j - 1] in listOf('L', '-', 'F')
                        val right = j < line.lastIndex && line[j + 1] in listOf('7', '-', 'J')
                        val top = i > 0 && input[i - 1][j] in listOf('|', '7', 'F')
                        val bottom = i < input.lastIndex && input[i + 1][j] in listOf('|', 'J', 'L')

                        startI = i
                        startJ = j

                        Pipe(left, right, top, bottom, start = true, steps = 0, i = i, j = j)
                    }
                }
            }
        }

        val pipesToVisit = mutableListOf(map[startI][startJ])
        val visited = mutableListOf<Pipe>()
        while (pipesToVisit.isNotEmpty()) {
            val currentPipe = pipesToVisit.removeAt(0)

            if (currentPipe.left) {
                val newPipeToVisit = map[currentPipe.i][currentPipe.j - 1]
                if (!visited.contains(newPipeToVisit)) {
                    pipesToVisit.add(map[currentPipe.i][currentPipe.j - 1].apply {
                        steps = currentPipe.steps + 1
                    })
                }
            }
            if (currentPipe.right) {
                val newPipeToVisit = map[currentPipe.i][currentPipe.j + 1]
                if (!visited.contains(newPipeToVisit)) {
                    pipesToVisit.add(map[currentPipe.i][currentPipe.j + 1].apply {
                        steps = currentPipe.steps + 1
                    })
                }
            }
            if (currentPipe.top) {
                val newPipeToVisit = map[currentPipe.i - 1][currentPipe.j]
                if (!visited.contains(newPipeToVisit)) {
                    pipesToVisit.add(map[currentPipe.i - 1][currentPipe.j].apply {
                        steps = currentPipe.steps + 1
                    })
                }
            }
            if (currentPipe.bottom) {
                val newPipeToVisit = map[currentPipe.i + 1][currentPipe.j]
                if (!visited.contains(newPipeToVisit)) {
                    pipesToVisit.add(map[currentPipe.i + 1][currentPipe.j].apply {
                        steps = currentPipe.steps + 1
                    })
                }
            }

            visited.add(currentPipe)
        }
        return map
    }

    fun part1(input: List<String>): Int {
        val map = processLoop(input)
        return map.flatten().maxOf { it.steps }
    }

    fun part2(input: List<String>): Int {
        val map = processLoop(input)

        var count = 0
        var inside: Boolean
        map.forEach { line ->
            inside = false
            line.forEach {
                if (it.bottom && it.steps != -1) {
                    inside = !inside
                }
                if (it.steps == -1 && inside) {
                    count += 1
                }
            }
        }
        return count
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}