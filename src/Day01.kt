fun main() {
    fun fixNumbers(s: String): String {
        if (s.first().isDigit() && s.last().isDigit()) {
            return s
        }

        return s.replace("one", "o1e")
            .replace("two","t2o")
            .replace("three", "t3e")
            .replace("four", "f4r")
            .replace("five", "f5e")
            .replace("six", "s6x")
            .replace("seven", "s7n")
            .replace("eight", "e8t")
            .replace("nine", "n9e")
    }

    fun part1(input: List<String>): Int {
        return input.map { line ->
            line.filter(Char::isDigit)
        }.map {
            "${it.first()}${it.last()}"
        }.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        return part1(input.map(::fixNumbers))
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
