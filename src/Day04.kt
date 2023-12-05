import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    fun countWinningNumbers(line: String): Int {
        val (_, wNumbersStr, myNumbersStr) = line.split(": ", " | ")
        val winningNumbers = wNumbersStr.split(' ').filter(String::isNotBlank)
        val myNumbers = myNumbersStr.split(' ').filter(String::isNotBlank)
        return winningNumbers.count { it in myNumbers }
    }

    fun score(count: Int) = if (count == 0) 0 else 2.0.pow(count - 1).roundToInt()

    fun processLine(line: String): Int = score(countWinningNumbers(line))

    fun part1(input: List<String>): Int {
        return input.map(::processLine).reduce { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {
        val map = input.mapIndexed { index, line ->
            index + 1 to mutableListOf(countWinningNumbers(line))
        }.toList()

        map.forEach {
            val idx = it.first - 1
            it.second.forEach { n ->
                for (i in 1..n) {
                    if (idx + i <= input.size) {
                        map[idx + i].second += map[idx + i].second.first()
                    }
                }
            }
        }

        return map.map(Pair<Int, MutableList<Int>>::second).sumOf { it.size }
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}