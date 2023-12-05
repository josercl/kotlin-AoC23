fun main() {
    fun isValidSet(gameSet: String, maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean {
        return gameSet
            .trim()
            .split(",")
            .map(String::trim)
            .all {
                val (number, color) = it.split(" ")
                val n = number.toInt()

                when (color) {
                    "red" -> n <= maxRed
                    "blue" -> n <= maxBlue
                    else -> n <= maxGreen
                }
            }
    }

    fun minimumCubes(sets: List<String>): List<Int> {
        var minRed = 0
        var minGreen = 0
        var minBlue = 0

        sets.forEach { set ->
            set.split(",").map(String::trim)
                .forEach {
                    val (number, color) = it.split(" ")
                    val n = number.toInt()

                    if (color == "red" && n > minRed) {
                        minRed = n
                    } else if (color == "blue" && n > minBlue) {
                        minBlue = n
                    } else if (color == "green" && n > minGreen) {
                        minGreen = n
                    }
                }
        }

        return listOf(
            minRed,
            minBlue,
            minGreen
        )
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (game, sets) = line.split(": ")
            val (_, gameId) = game.split(" ")

            val isPossible = sets.split("; ")
                .all {
                    isValidSet(it, 12, 13, 14)
                }

            if (isPossible) gameId.toInt() else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (_, sets) = line.split(": ")

            minimumCubes(sets.split("; ")).reduce { acc, i -> acc * i }
        }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}