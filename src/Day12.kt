fun main() {
    fun generate(input: String): List<String> {
        if (input.indexOf('?') == -1) return listOf(input)

        return generate(input.replaceFirst('?', '#')) +
                generate(input.replaceFirst('?', '.'))
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (springs, formation) = line.split(" ")
            val lengths = formation.split(",").map(String::toInt)

            generate(springs.dropWhile { it == '.' }.dropLastWhile { it == '.' })
                .map { it.split(".").filter(String::isNotBlank) }
                .count {
                    val sameSize = it.size == lengths.size
                    val sameLengths = it.zip(lengths).all { p -> p.first.length == p.second }
                    sameSize && sameLengths
                }
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day12")

    part1(input).println()
}